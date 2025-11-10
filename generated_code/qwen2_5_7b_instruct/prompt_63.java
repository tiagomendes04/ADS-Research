```java
import java.util.*;

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
    private List<Transaction> transactions;

    public BudgetApp() {
        categoryLimits = new HashMap<>();
        transactions = new ArrayList<>();
    }

    public void addTransaction(String category, double amount) {
        transactions.add(new Transaction(category, amount));
    }

    public void setLimit(String category, double limit) {
        categoryLimits.put(category, limit);
    }

    public void categorizeTransactions() {
        for (Transaction transaction : transactions) {
            if (categoryLimits.containsKey(transaction.category)) {
                double limit = categoryLimits.get(transaction.category);
                if (transaction.amount > limit) {
                    System.out.println("Warning: Exceeded limit for category " + transaction.category);
                }
            }
        }
    }

    public static void main(String[] args) {
        BudgetApp app = new BudgetApp();
        app.setLimit("Groceries", 100.0);
        app.setLimit("Entertainment", 50.0);
        app.addTransaction("Groceries", 80.0);
        app.addTransaction("Entertainment", 40.0);
        app.addTransaction("Groceries", 120.0);
        app.categorizeTransactions();
    }
}
```