```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Transaction {
    private String category;
    private double amount;

    public Transaction(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

class BudgetApp {
    private List<Transaction> transactions;
    private Map<String, Double> categories;

    public BudgetApp() {
        this.transactions = new ArrayList<>();
        this.categories = new HashMap<>();
    }

    public void addTransaction(String category, double amount) {
        transactions.add(new Transaction(category, amount));
        updateCategoryLimit(category, amount);
    }

    public void updateCategoryLimit(String category, double amount) {
        if (categories.containsKey(category)) {
            double limit = categories.get(category);
            double newLimit = limit + amount;
            categories.put(category, newLimit);
        } else {
            categories.put(category, amount);
        }
    }

    public void viewTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println("Category: " + transaction.getCategory() + ", Amount: " + transaction.getAmount());
        }
    }

    public void viewCategoryLimits() {
        for (Map.Entry<String, Double> entry : categories.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Limit: " + entry.getValue());
        }
    }

    public void setCategoryLimit(String category, double limit) {
        if (categories.containsKey(category)) {
            categories.put(category, limit);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BudgetApp app = new BudgetApp();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add transaction");
            System.out.println("2. View transactions");
            System.out.println("3. View category limits");
            System.out.println("4. Set category limit");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.next();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    app.addTransaction(category, amount);
                    break;
                case 2:
                    app.viewTransactions();
                    break;
                case 3:
                    app.viewCategoryLimits();
                    break;
                case 4:
                    System.out.print("Enter category: ");
                    category = scanner.next();
                    System.out.print("Enter new limit: ");
                    double newLimit = scanner.nextDouble();
                    app.setCategoryLimit(category, newLimit);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}
```