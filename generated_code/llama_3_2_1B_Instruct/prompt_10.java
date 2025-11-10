```java
public class Account {
    private String accountNumber;
    private double balance;
    private AccountType type;

    public Account(String accountNumber, double balance, AccountType type) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient funds.");
        } else {
            this.balance -= amount;
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient funds.");
        } else {
            this.balance -= amount;
            recipient.setBalance(recipient.getBalance() + amount);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                '}';
    }
}

enum AccountType {
    SAVING,
    CREDIT,
    DEPOSIT
}

class Transaction {
    private Account from;
    private Account to;
    private double amount;
    private TransactionType type;

    public Transaction(Account from, Account to, double amount, TransactionType type) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.type = type;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
}

class TransactionType {
    public static enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }
}
```