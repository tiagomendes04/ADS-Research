```java
import java.util.Scanner;

class ATMSimulation {
    private static final int CORRECT_PIN = 1234;
    private double balance = 1000.0;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        if (validatePIN(pin)) {
            while (true) {
                System.out.println("\n1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check Balance");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        deposit(scanner);
                        break;
                    case 2:
                        withdraw(scanner);
                        break;
                    case 3:
                        checkBalance();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Incorrect PIN. Access denied.");
        }

        scanner.close();
    }

    private boolean validatePIN(int pin) {
        return pin == CORRECT_PIN;
    }

    private void deposit(Scanner scanner) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. New balance: $" + balance);
        } else {
            System.out.println("Invalid amount. Deposit failed.");
        }
    }

    private void withdraw(Scanner scanner) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: $" + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal failed.");
        } else {
            System.out.println("Invalid amount. Withdrawal failed.");
        }
    }

    private void checkBalance() {
        System.out.println("Your current balance is: $" + balance);
    }

    public static void main(String[] args) {
        ATMSimulation atm = new ATMSimulation();
        atm.run();
    }
}
```