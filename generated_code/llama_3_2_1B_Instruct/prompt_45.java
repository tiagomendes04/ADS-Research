```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankATM {
    public static void main(String[] args) {
        Map<String, Double> accounts = new HashMap<>();
        Map<String, String> users = new HashMap<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Pin Validation");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter PIN: ");
                    String pin = scanner.nextLine();
                    if (pin.equals("1234")) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        amount = Math.max(0, amount);
                        accounts.put("1234", amount);
                        System.out.println("Deposit successful!");
                    } else {
                        System.out.println("Invalid PIN!");
                    }
                    break;

                case 2:
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextLine();
                    if (pin.equals("1234")) {
                        System.out.print("Enter account number: ");
                        String accountNumber = scanner.nextLine();
                        if (accounts.containsKey(accountNumber)) {
                            System.out.print("Enter amount to withdraw: ");
                            double amount = scanner.nextDouble();
                            amount = Math.max(0, amount);
                            if (accounts.get(accountNumber) >= amount) {
                                accounts.put(accountNumber, accounts.get(accountNumber) - amount);
                                System.out.println("Withdrawal successful!");
                            } else {
                                System.out.println("Insufficient funds!");
                            }
                        } else {
                            System.out.println("Account not found!");
                        }
                    } else {
                        System.out.println("Invalid PIN!");
                    }
                    break;

                case 3:
                    System.out.print("Enter PIN: ");
                    pin = scanner.nextLine();
                    if (pin.equals("1234")) {
                        System.out.print("Enter account number: ");
                        String accountNumber = scanner.nextLine();
                        if (accounts.containsKey(accountNumber)) {
                            System.out.print("Enter PIN to validate: ");
                            String pinToValidate = scanner.nextLine();
                            if (pinToValidate.equals("1234")) {
                                System.out.println("Authentication successful!");
                            } else {
                                System.out.println("Authentication failed!");
                            }
                        } else {
                            System.out.println("Account not found!");
                        }
                    } else {
                        System.out.println("Invalid PIN!");
                    }
                    break;

                case 4:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    if (accounts.containsKey(accountNumber)) {
                        System.out.println("Current balance: $" + accounts.get(accountNumber));
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}
```