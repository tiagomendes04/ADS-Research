```java
import java.util.HashMap;
import java.util.Scanner;

public class BudgetingApp {
    static HashMap<String, Double> spending = new HashMap<>();
    static HashMap<String, Double> savings = new HashMap<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1. Add transaction");
            System.out.println("2. View spending");
            System.out.println("3. View savings");
            System.out.println("4. Set limit");
            System.out.println("5. Exit");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    addTransaction(input);
                    break;
                case 2:
                    viewSpending();
                    break;
                case 3:
                    viewSavings();
                    break;
                case 4:
                    setLimit(input);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addTransaction(Scanner input) {
        System.out.println("Enter transaction details:");
        System.out.println("Transaction category:");
        String category = input.next();

        System.out.println("Transaction amount:");
        double amount = input.nextDouble();

        spending.put(category, spending.getOrDefault(category, 0.0) + amount);
        System.out.println("Transaction added!");
    }

    static void viewSpending() {
        System.out.println("Current Spending:");
        for (String category : spending.keySet()) {
            System.out.println(category + ": $" + spending.get(category));
        }
    }

    static void viewSavings() {
        System.out.println("Current Savings:");
        for (String category : savings.keySet()) {
            System.out.println(category + ": $" + savings.get(category));
        }
    }

    static void setLimit(Scanner input) {
        System.out.println("Enter category to set limit for:");
        String category = input.next();

        System.out.println("Enter limit amount:");
        double limit = input.nextDouble();

        savings.put(category, limit);
        System.out.println("Limit set successfully.");
    }
}
```