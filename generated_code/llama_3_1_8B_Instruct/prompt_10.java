```java
// Account.java
public class Account {
    private int accountNumber;
    private double balance;

    public Account(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
    }

    public void transfer(Account toAccount, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        withdraw(amount);
        toAccount.deposit(amount);
    }
}

// InsufficientFundsException.java
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Bank.java
public class Bank {
    public static Account createAccount(int accountNumber, double initialBalance) {
        return new Account(accountNumber, initialBalance);
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Account account1 = Bank.createAccount(12345, 1000.0);
        Account account2 = Bank.createAccount(67890, 500.0);

        System.out.println("Account 1 balance: " + account1.getBalance());
        System.out.println("Account 2 balance: " + account2.getBalance());

        account1.deposit(500.0);
        System.out.println("Account 1 balance after deposit: " + account1.getBalance());

        try {
            account1.transfer(account2, 200.0);
            System.out.println("Account 1 balance after transfer: " + account1.getBalance());
            System.out.println("Account 2 balance after transfer: " + account2.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
```