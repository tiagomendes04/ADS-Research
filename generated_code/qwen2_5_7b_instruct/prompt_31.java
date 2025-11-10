```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Expense {
    private String description;
    private double amount;
    private Category category;

    public Expense(String description, double amount, Category category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }
}

class Budget {
    private String category;
    private double amount;

    public Budget(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

class FinanceTracker {
    private List<Expense> expenses;
    private List<Budget> budgets;

    public FinanceTracker() {
        expenses = new ArrayList<>();
        budgets = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void addBudget(Budget budget) {
        budgets.add(budget);
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public void displayExpenses() {
        for (Expense expense : expenses) {
            System.out.println(expense.getDescription() + ": $" + expense.getAmount() + " - " + expense.getCategory().getName());
        }
    }

    public void displayBudgets() {
        for (Budget budget : budgets) {
            System.out.println(budget.getCategory() + ": $" + budget.getAmount());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinanceTracker tracker = new FinanceTracker();

        while (true) {
            System.out.println("\n1. Add Expense\n2. Add Budget\n3. Display Expenses\n4. Display Budgets\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    Category expenseCategory = new Category(category);
                    tracker.addExpense(new Expense(desc, amount, expenseCategory));
                    break;
                case 2:
                    System.out.print("Category: ");
                    category = scanner.nextLine();
                    System.out.print("Amount: ");
                    double budgetAmount = scanner.nextDouble();
                    tracker.addBudget(new Budget(category, budgetAmount));
                    break;
                case 3:
                    tracker.displayExpenses();
                    break;
                case 4:
                    tracker.displayBudgets();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
```