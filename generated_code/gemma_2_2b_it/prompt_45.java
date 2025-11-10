```java
import java.util.HashMap;
import java.util.Scanner;

public class ATM {

    private static HashMap<Integer, Integer> accountBalance = new HashMap<>();
    private static int accountNumber;
    private static String pin;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM!");
        System.out.print("Enter your account number: ");
        accountNumber = scanner.nextInt();
        System.out.print("Enter your PIN: ");
        pin = scanner.next();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    deposit(scanner);
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    checkBalance(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();

        if (validatePin(pin)) {
            accountBalance.put(accountNumber, accountBalance.getOrDefault(accountNumber, 0) + amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter withdrawal amount: ");
        int amount = scanner.nextInt();

        if (validatePin(pin)) {
            if (accountBalance.get(accountNumber) >= amount) {
                accountBalance.put(accountNumber, accountBalance.getOrDefault(accountNumber, 0) - amount);
                System.out.println("Withdrawal successful.");
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    private static void checkBalance(Scanner scanner) {
        if (validatePin(pin)) {
            System.out.println("Your balance is: " + accountBalance.get(accountNumber));
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    private static boolean validatePin(String pin) {
        return pin.equals("1234");
    }
}
```