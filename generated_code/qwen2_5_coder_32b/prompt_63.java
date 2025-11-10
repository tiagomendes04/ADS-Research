```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Transaction {
    String category;
    double amount;

    public Transaction(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

class BudgetApp {
    private Map<String, Double> categoryLimits;
    private Map<String, Double> categorySpent;

    public BudgetApp() {
        categoryLimits = new HashMap<>();
        categorySpent = new HashMap<>();
    }

    public void setLimit(String category, double limit) {
        categoryLimits.put(category, limit);
    }

    public void addTransaction(Transaction transaction) {
        if (categorySpent.containsKey(transaction.category)) {
            double spent = categorySpent.get(transaction.category);
            categorySpent.put(transaction.category, spent + transaction.amount);
        } else {
            categorySpent.put(transaction.category, transaction.amount);
        }
    }

    public boolean isOverLimit(Transaction transaction) {
        double totalSpent = categorySpent.getOrDefault(transaction.category, 0.0);
        double limit = categoryLimits.getOrDefault(transaction.category, Double.MAX_VALUE);
        return totalSpent + transaction.amount > limit;
    }

    public void displayStatus() {
        for (String category : categoryLimits.keySet()) {
            double limit = categoryLimits.get(category);
            double spent = categorySpent.getOrDefault(category, 0.0);
            System.out.println("Category: " + category + ", Spent: " + spent + ", Limit: " + limit);
        }
    }

    public static void main(String[] args) {
        BudgetApp app = new BudgetApp();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command (setLimit/addTransaction/displayStatus/exit):");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("setLimit")) {
                System.out.print("Enter category: ");
                String category = scanner.nextLine();
                System.out.print("Enter limit: ");
                double limit = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                app.setLimit(category, limit);
            } else if (command.equalsIgnoreCase("addTransaction")) {
                System.out.print("Enter category: ");
                String category = scanner.nextLine();
                System.out.print("Enter amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                Transaction transaction = new Transaction(category, amount);
                if (app.isOverLimit(transaction)) {
                    System.out.println("Adding this transaction would exceed the limit for " + category + ".");
                } else {
                    app.addTransaction(transaction);
                    System.out.println("Transaction added.");
                }
            } else if (command.equalsIgnoreCase("displayStatus")) {
                app.displayStatus();
            } else if (command.equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }

        scanner.close();
    }
}
```