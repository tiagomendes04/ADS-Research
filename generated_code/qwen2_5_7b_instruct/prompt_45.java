```java
import java.util.Scanner;

public class ATM {
    private static final int PIN = 1234;
    private static final double INITIAL_BALANCE = 1000.00;

    private double balance;
    private int pin;

    public ATM() {
        this.balance = INITIAL_BALANCE;
        this.pin = PIN;
    }

    public boolean validatePin(int enteredPin) {
        return enteredPin == this.pin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();
        boolean isValid = false;

        System.out.println("Welcome to the ATM!");

        while (!isValid) {
            System.out.print("Enter your PIN: ");
            int enteredPin = scanner.nextInt();
            isValid = atm.validatePin(enteredPin);
            if (!isValid) {
                System.out.println("Invalid PIN. Please try again.");
            }
        }

        while (true) {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    atm.withdraw(withdrawalAmount);
                    break;
                case 3:
                    System.out.println("Current Balance: " + atm.balance);
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
```