```java
import java.util.*;
import java.time.LocalDate;

class Transaction {
    private final String description;
    private final double amount;
    private final LocalDate date;
    private final String category;

    public Transaction(String description, double amount, LocalDate date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
}

class CategoryLimit {
    private final String name;
    private final double monthlyLimit;

    public CategoryLimit(String name, double monthlyLimit) {
        this.name = name;
        this.monthlyLimit = monthlyLimit;
    }

    public String getName() { return name; }
    public double getMonthlyLimit() { return monthlyLimit; }
}

class BudgetManager {
    private final Map<String, CategoryLimit> limits = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public void addCategoryLimit(String name, double monthlyLimit) {
        limits.put(name, new CategoryLimit(name, monthlyLimit));
    }

    public void addTransaction(String description, double amount, LocalDate date, String category) {
        if (!limits.containsKey(category)) {
            System.out.println("Warning: No limit set for category '" + category + "'. Transaction added anyway.");
        }
        transactions.add(new Transaction(description, amount, date, category));
    }

    public double getSpentThisMonth(String category) {
        LocalDate now = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getCategory().equals(category))
                .filter(t -> t.getDate().getYear() == now.getYear() && t.getDate().getMonth() == now.getMonth())
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public void printReport() {
        System.out.println("=== Monthly Budget Report ===");
        LocalDate now = LocalDate.now();
        for (CategoryLimit limit : limits.values()) {
            double spent = getSpentThisMonth(limit.getName());
            double remaining = limit.getMonthlyLimit() - spent;
            System.out.printf("%s: Spent $%.2f / Limit $%.2f => %s%n",
                    limit.getName(),
                    spent,
                    limit.getMonthlyLimit(),
                    remaining >= 0 ? ("Remaining $" + String.format("%.2f", remaining)) : ("OVER BY $" + String.format("%.2f", -remaining)));
        }
        System.out.println("=============================");
    }
}

public class BudgetApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BudgetManager manager = new BudgetManager();

    private static void showMenu() {
        System.out.println("\n--- Budgeting App ---");
        System.out.println("1. Add Category Limit");
        System.out.println("2. Add Transaction");
        System.out.println("3. View Monthly Report");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private static void handleAddCategory() {
        System.out.print("Category name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Monthly limit: ");
        double limit = Double.parseDouble(scanner.nextLine().trim());
        manager.addCategoryLimit(name, limit);
        System.out.println("Category limit added.");
    }

    private static void handleAddTransaction() {
        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Amount (positive for expense, negative for income): ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Date (YYYY-MM-DD) or leave blank for today: ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);
        System.out.print("Category: ");
        String category = scanner.nextLine().trim();
        manager.addTransaction(desc, amount, date, category);
        System.out.println("Transaction recorded.");
    }

    private static void handleReport() {
        manager.printReport();
    }

    public static void main(String[] args) {
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> handleAddCategory();
                case "2" -> handleAddTransaction();
                case "3" -> handleReport();
                case "4" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
```