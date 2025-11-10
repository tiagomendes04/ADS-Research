```java
import java.util.HashMap;
import java.util.Scanner;

public class PersonalFinanceTracker {

    static HashMap<String, Double> budget = new HashMap<>();
    static HashMap<String, Double> expenses = new HashMap<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nPersonal Finance Tracker");
            System.out.println("1. Add Budget");
            System.out.println("2. Add Expense");
            System.out.println("3. View Budget");
            System.out.println("4. View Expenses");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBudget();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    viewBudget();
                    break;
                case 4:
                    viewExpenses();
                    break;
                case 5:
                    generateReport();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addBudget() {
        System.out.print("Enter budget category: ");
        String category = input.nextLine();
        System.out.print("Enter budget amount: ");
        double amount = input.nextDouble();
        budget.put(category, amount);
        System.out.println("Budget added successfully!");
    }

    static void addExpense() {
        System.out.print("Enter expense category: ");
        String category = input.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = input.nextDouble();
        expenses.put(category, amount);
        System.out.println("Expense added successfully!");
    }

    static void viewBudget() {
        System.out.println("\nBudget:");
        for (String category : budget.keySet()) {
            System.out.println(category + ": " + budget.get(category));
        }
    }

    static void viewExpenses() {
        System.out.println("\nExpenses:");
        for (String category : expenses.keySet()) {
            System.out.println(category + ": " + expenses.get(category));
        }
    }

    static void generateReport() {
        System.out.println("\nGenerate Report...");
    }
}
```