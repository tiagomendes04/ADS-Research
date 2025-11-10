```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BankAccount {
    private int accountNumber;
    private String pin;
    private double balance;

    public BankAccount(int accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
        }
    }
}

public class ATM {
    private Map<Integer, BankAccount> accounts;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void login() {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            BankAccount account = accounts.get(accountNumber);
            if (account.getPin().equals(pin)) {
                menu(account);
            } else {
                System.out.println("Incorrect PIN.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private void register() {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Enter your initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        BankAccount account = new BankAccount(accountNumber, pin, balance);
        accounts.put(accountNumber, account);
        System.out.println("Account created successfully.");
    }

    private void menu(BankAccount account) {
        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    deposit(account);
                    break;
                case 2:
                    withdraw(account);
                    break;
                case 3:
                    checkBalance(account);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void deposit(BankAccount account) {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        account.deposit(amount);
        System.out.println("Deposit successful. New balance: " + account.getBalance());
    }

    private void withdraw(BankAccount account) {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        account.withdraw(amount);
        System.out.println("Withdrawal successful. New balance: " + account.getBalance());
    }

    private void checkBalance(BankAccount account) {
        System.out.println("Current balance: " + account.getBalance());
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}
```