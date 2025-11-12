```java
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class Account {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    private final long id;
    private final String owner;
    private double balance;

    public Account(String owner, double initialBalance) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.owner = owner;
        this.balance = initialBalance;
    }

    public long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive");
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive");
        if (balance < amount) throw new InsufficientFundsException("Insufficient funds");
        balance -= amount;
    }

    public void transferTo(Account target, double amount) {
        if (target == null) throw new IllegalArgumentException("Target account cannot be null");
        if (amount <= 0) throw new IllegalArgumentException("Transfer amount must be positive");

        // To avoid deadlock, always lock the lower id first
        Account firstLock = this.id < target.id ? this : target;
        Account secondLock = this.id < target.id ? target : this;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (this.balance < amount) throw new InsufficientFundsException("Insufficient funds");
                this.balance -= amount;
                target.balance += amount;
            }
        }
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", owner='" + owner + "', balance=" + balance + '}';
    }
}

class Bank {
    private final Map<Long, Account> accounts = new HashMap<>();

    public synchronized Account createAccount(String owner, double initialBalance) {
        Account acc = new Account(owner, initialBalance);
        accounts.put(acc.getId(), acc);
        return acc;
    }

    public synchronized Account getAccount(long accountId) {
        Account acc = accounts.get(accountId);
        if (acc == null) throw new NoSuchElementException("Account not found: " + accountId);
        return acc;
    }

    public void deposit(long accountId, double amount) {
        getAccount(accountId).deposit(amount);
    }

    public void withdraw(long accountId, double amount) {
        getAccount(accountId).withdraw(amount);
    }

    public void transfer(long fromAccountId, long toAccountId, double amount) {
        Account from = getAccount(fromAccountId);
        Account to = getAccount(toAccountId);
        from.transferTo(to, amount);
    }

    public List<Account> listAccounts() {
        synchronized (this) {
            return new ArrayList<>(accounts.values());
        }
    }
}

/* Example usage
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Account a1 = bank.createAccount("Alice", 1000);
        Account a2 = bank.createAccount("Bob", 500);

        bank.deposit(a1.getId(), 200);
        bank.withdraw(a2.getId(), 100);
        bank.transfer(a1.getId(), a2.getId(), 300);

        bank.listAccounts().forEach(System.out::println);
    }
}
*/
```