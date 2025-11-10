```java
import java.util.ArrayList;
import java.util.List;

public class PersonalFinanceTracker {
    private List<Budget> budgets;
    private List<Expense> expenses;

    public PersonalFinanceTracker() {
        this.budgets = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    public void generateReport() {
        System.out.println("Budgets:");
        for (Budget budget : budgets) {
            System.out.println(budget.toString());
        }

        System.out.println("\nExpenses:");
        for (Expense expense : expenses) {
            System.out.println(expense.toString());
        }

        System.out.println("\nTotal Expenses: " + getTotalExpenses());
        System.out.println("Total Budgeted: " + getTotalBudgeted());
        System.out.println("Remaining Budget: " + getRemainingBudget());
    }

    private double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    private double getTotalBudgeted() {
        double total = 0;
        for (Budget budget : budgets) {
            total += budget.getAmount();
        }
        return total;
    }

    private double getRemainingBudget() {
        return getTotalBudgeted() - getTotalExpenses();
    }
}
```

```java
public class Budget {
    private String name;
    private double amount;

    public Budget(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return name + ": $" + amount;
    }
}
```

```java
public class Expense {
    private String description;
    private double amount;
    private String category;

    public Expense(String description, double amount, String category) {
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

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return description + ": $" + amount + " (Category: " + category + ")";
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        PersonalFinanceTracker tracker = new PersonalFinanceTracker();

        tracker.addBudget(new Budget("Rent", 1000));
        tracker.addBudget(new Budget("Groceries", 500));

        tracker.addExpense(new Expense("Groceries: Bread", 5, "Food"));
        tracker.addExpense(new Expense("Groceries: Milk", 3, "Food"));
        tracker.addExpense(new Expense("Rent: Deposit", 200, "Housing"));

        tracker.generateReport();
    }
}
```