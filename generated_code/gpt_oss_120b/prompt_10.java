```java
// Exception thrown when an operation would result in a negative balance
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Simple immutable identifier for an account
public final class AccountId {
    private final String id;

    public AccountId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Account id cannot be null or empty");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId that = (AccountId) o;
        return id.equals(that.id);
    }

    @Override public int hashCode() {
        return id.hashCode();
    }

    @Override public String toString() {
        return id;
    }
}

// Core account class supporting deposit, withdraw and transfer
public class BankAccount {
    private final AccountId accountId;
    private long balanceCents; // store balance in smallest currency unit to avoid floating point errors

    public BankAccount(AccountId accountId) {
        this.accountId = accountId;
        this.balanceCents = 0L;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public synchronized long getBalanceCents() {
        return balanceCents;
    }

    public synchronized void deposit(long amountCents) {
        if (amountCents <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balanceCents += amountCents;
    }

    public synchronized void withdraw(long amountCents) {
        if (amountCents <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balanceCents < amountCents) {
            throw new InsufficientFundsException(
                "Account " + accountId + " has insufficient funds");
        }
        balanceCents -= amountCents;
    }

    // Transfer is implemented as atomic operation using a lock ordering to avoid deadlock
    public void transferTo(BankAccount target, long amountCents) {
        if (target == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }
        if (target == this) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        if (amountCents <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        // Determine lock order based on account IDs to prevent deadlock
        BankAccount firstLock = this.accountId.getId().compareTo(target.accountId.getId()) < 0 ? this : target;
        BankAccount secondLock = firstLock == this ? target : this;

        synchronized (firstLock) {
            synchronized (secondLock) {
                this.withdraw(amountCents);
                target.deposit(amountCents);
            }
        }
    }

    @Override public String toString() {
        return "BankAccount{id=" + accountId + ", balance=" + (balanceCents / 100.0) + "}";
    }
}

// Simple repository to store and retrieve accounts
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<AccountId, BankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccount createAccount(String id) {
        AccountId accountId = new AccountId(id);
        BankAccount account = new BankAccount(accountId);
        if (accounts.putIfAbsent(accountId, account) != null) {
            throw new IllegalArgumentException("Account with id " + id + " already exists");
        }
        return account;
    }

    public BankAccount getAccount(String id) {
        BankAccount account = accounts.get(new AccountId(id));
        if (account == null) {
            throw new IllegalArgumentException("Account with id " + id + " not found");
        }
        return account;
    }
}

// Example usage
public class BankingDemo {
    public static void main(String[] args) {
        AccountRepository repo = new AccountRepository();

        BankAccount accA = repo.createAccount("A001");
        BankAccount accB = repo.createAccount("B001");

        accA.deposit(500_00); // $500.00
        accB.deposit(200_00); // $200.00

        System.out.println(accA); // BankAccount{id=A001, balance=500.0}
        System.out.println(accB); // BankAccount{id=B001, balance=200.0}

        accA.withdraw(150_00); // $150.00
        System.out.println("After withdrawal from A: " + accA);

        accA.transferTo(accB, 100_00); // $100.00 transfer
        System.out.println("After transfer A -> B:");
        System.out.println(accA);
        System.out.println(accB);
    }
}
```