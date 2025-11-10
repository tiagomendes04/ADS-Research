```java
import java.util.Scanner;

public class ATM {
    private static final int CORRECT_PIN = 1234;
    private static double balance = 0.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!validatePin(scanner)) {
            System.out.println("Too many failed attempts. Exiting.");
            scanner.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    deposit(scanner);
                    break;
                case "2":
                    withdraw(scanner);
                    break;
                case "3":
                    System.out.printf("Current balance: $%.2f%n", balance);
                    break;
                case "4":
                    running = false;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        scanner.close();
    }

    private static boolean validatePin(Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter your 4-digit PIN: ");
            String input = scanner.nextLine().trim();
            try {
                int enteredPin = Integer.parseInt(input);
                if (enteredPin == CORRECT_PIN) {
                    System.out.println("PIN accepted.");
                    return true;
                } else {
                    System.out.println("Incorrect PIN.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid PIN format.");
            }
            attempts++;
        }
        return false;
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter amount to deposit: $");
        String input = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive.");
                return;
            }
            balance += amount;
            System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter amount to withdraw: $");
        String input = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be positive.");
                return;
            }
            if (amount > balance) {
                System.out.println("Insufficient funds.");
                return;
            }
            balance -= amount;
            System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }
    }
}
```