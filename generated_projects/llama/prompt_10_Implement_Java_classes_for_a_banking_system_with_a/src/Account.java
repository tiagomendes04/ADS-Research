// Account.java
public class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds");
        } else {
            balance -= amount;
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds");
        } else {
            balance -= amount;
            recipient.deposit(amount);
        }
    }
}

// Bank.java
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void createAccount(String accountNumber, double balance) {
        accounts.put(accountNumber, new Account(accountNumber, balance));
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void deposit(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = getAccount(fromAccountNumber);
        Account toAccount = getAccount(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.createAccount("12345", 1000);
        bank.createAccount("67890", 500);

        bank.deposit("12345", 500);
        bank.withdraw("67890", 200);

        bank.transfer("12345", "67890", 300);

        System.out.println("Account 12345 balance: " + bank.getAccount("12345").getBalance());
        System.out.println("Account 67890 balance: " + bank.getAccount("67890").getBalance());
    }
}