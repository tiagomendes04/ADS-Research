```java
import java.util.*;
import java.text.*;

class PersonalFinanceTracker {
    private Map<String, Budget> budgets;
    private List<Expense> expenses;

    public PersonalFinanceTracker() {
        this.budgets = new HashMap<>();
        this.expenses = new ArrayList<>();
    }

    public void addBudget(String category, double amount) {
        budgets.put(category, new Budget(category, amount));
    }

    public void addExpense(String category, double amount, String date) {
        Expense expense = new Expense(category, amount, date);
        expenses.add(expense);
        if (budgets.containsKey(category)) {
            budgets.get(category).addExpense(amount);
        }
    }

    public void generateReport() {
        System.out.println("Budget Report:");
        for (Budget budget : budgets.values()) {
            System.out.println(budget);
        }
        System.out.println("\nExpenses Report:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    class Budget {
        private String category;
        private double totalAmount;
        private double spentAmount;

        public Budget(String category, double totalAmount) {
            this.category = category;
            this.totalAmount = totalAmount;
            this.spentAmount = 0.0;
        }

        public void addExpense(double amount) {
            this.spentAmount += amount;
        }

        @Override
        public String toString() {
            return String.format("%s: Total Budget=%.2f, Spent=%.2f, Remaining=%.2f", category, totalAmount, spentAmount, totalAmount - spentAmount);
        }
    }

    class Expense {
        private String category;
        private double amount;
        private Date date;

        public Expense(String category, double amount, String dateString) {
            this.category = category;
            this.amount = amount;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                this.date = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                this.date = new Date();
            }
        }

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return String.format("%s: %.2f on %s", category, amount, sdf.format(date));
        }
    }

    public static void main(String[] args) {
        PersonalFinanceTracker tracker = new PersonalFinanceTracker();
        tracker.addBudget("Food", 500.0);
        tracker.addBudget("Entertainment", 300.0);
        tracker.addExpense("Food", 120.50, "2023-10-01");
        tracker.addExpense("Entertainment", 60.00, "2023-10-02");
        tracker.addExpense("Food", 80.25, "2023-10-03");
        tracker.generateReport();
    }
}
```