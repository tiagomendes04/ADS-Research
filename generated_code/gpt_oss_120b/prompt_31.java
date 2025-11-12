```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Budget {
    private String category;
    private double limit;

    public Budget(String category, double limit) {
        this.category = category;
        this.limit = limit;
    }

    public String getCategory() {
        return category;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}

class Expense {
    private String category;
    private double amount;
    private LocalDate date;
    private String description;

    public Expense(String category, double amount, LocalDate date, String description) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}

class FinanceTracker {
    private Map<String, Budget> budgets = new HashMap<>();
    private List<Expense> expenses = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        new FinanceTracker().run();
    }

    private void run() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addBudget(); break;
                case "2": addExpense(); break;
                case "3": viewBudgets(); break;
                case "4": viewExpenses(); break;
                case "5": generateReport(); break;
                case "0": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Personal Finance Tracker ---");
        System.out.println("1. Add/Update Budget");
        System.out.println("2. Add Expense");
        System.out.println("3. View Budgets");
        System.out.println("4. View Expenses");
        System.out.println("5. Generate Report");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private void addBudget() {
        System.out.print("Enter category: ");
        String cat = scanner.nextLine().trim();
        System.out.print("Enter budget limit: ");
        double limit = Double.parseDouble(scanner.nextLine().trim());
        budgets.put(cat, new Budget(cat, limit));
        System.out.println("Budget saved.");
    }

    private void addExpense() {
        System.out.print("Enter category: ");
        String cat = scanner.nextLine().trim();
        System.out.print("Enter amount: ");
        double amt = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter date (yyyy-MM-dd) or leave empty for today: ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr, dateFormatter);
        System.out.print("Enter description: ");
        String desc = scanner.nextLine().trim();
        expenses.add(new Expense(cat, amt, date, desc));
        System.out.println("Expense recorded.");
    }

    private void viewBudgets() {
        if (budgets.isEmpty()) {
            System.out.println("No budgets defined.");
            return;
        }
        System.out.println("\nBudgets:");
        budgets.values().forEach(b ->
                System.out.printf("- %s: $%.2f%n", b.getCategory(), b.getLimit()));
    }

    private void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\nExpenses:");
        expenses.forEach(e ->
                System.out.printf("- %s | $%.2f | %s | %s%n",
                        e.getCategory(),
                        e.getAmount(),
                        e.getDate().format(dateFormatter),
                        e.getDescription()));
    }

    private void generateReport() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to report.");
            return;
        }
        System.out.print("Enter start date (yyyy-MM-dd) or leave empty: ");
        String startStr = scanner.nextLine().trim();
        System.out.print("Enter end date (yyyy-MM-dd) or leave empty: ");
        String endStr = scanner.nextLine().trim();
        LocalDate start = startStr.isEmpty() ? null : LocalDate.parse(startStr, dateFormatter);
        LocalDate end = endStr.isEmpty() ? null : LocalDate.parse(endStr, dateFormatter);

        List<Expense> filtered = expenses.stream()
                .filter(e -> (start == null || !e.getDate().isBefore(start)) &&
                             (end == null || !e.getDate().isAfter(end)))
                .collect(Collectors.toList());

        Map<String, Double> totalByCategory = filtered.stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)));

        double overallTotal = filtered.stream()
                .mapToDouble(Expense::getAmount).sum();

        System.out.println("\n--- Report ---");
        totalByCategory.forEach((cat, amt) -> {
            double budgetLimit = budgets.containsKey(cat) ? budgets.get(cat).getLimit() : 0;
            double remaining = budgetLimit - amt;
            String status = budgetLimit > 0
                    ? (remaining >= 0 ? "Within budget" : "Over budget")
                    : "No budget set";
            System.out.printf("Category: %s%n", cat);
            System.out.printf("  Spent: $%.2f%n", amt);
            if (budgetLimit > 0) {
                System.out.printf("  Budget: $%.2f%n", budgetLimit);
                System.out.printf("  Remaining: $%.2f%n", remaining);
            }
            System.out.printf("  Status: %s%n", status);
        });
        System.out.printf("Overall spent: $%.2f%n", overallTotal);
    }
}
```