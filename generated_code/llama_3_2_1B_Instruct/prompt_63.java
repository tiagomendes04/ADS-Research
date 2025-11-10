```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Transaction {
    String category;
    String type;
    double amount;

    public Transaction(String category, String type, double amount) {
        this.category = category;
        this.type = type;
        this.amount = amount;
    }
}

class Budget {
    Map<String, Transaction> transactions;
    Map<String, Double> limits;

    public Budget() {
        transactions = new HashMap<>();
        limits = new HashMap<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.category, transaction);
    }

    public void addLimit(String category, double limit) {
        limits.put(category, limit);
    }

    public void checkLimit(Transaction transaction) {
        if (limits.containsKey(transaction.category) && limits.get(transaction.category) < transaction.amount) {
            System.out.println("Insufficient funds for " + transaction.category + ": " + transaction.amount + " exceeds the limit of " + limits.get(transaction.category));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Budget budget = new Budget();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add transaction");
            System.out.println("2. Add limit");
            System.out.println("3. Check transaction");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter transaction category: ");
                    String category = scanner.next();
                    System.out.print("Enter transaction type: ");
                    String type = scanner.next();
                    System.out.print("Enter transaction amount: ");
                    double amount = scanner.nextDouble();
                    budget.addTransaction(new Transaction(category, type, amount));
                    break;
                case 2:
                    System.out.print("Enter category: ");
                    String category = scanner.next();
                    System.out.print("Enter limit: ");
                    double limit = scanner.nextDouble();
                    budget.addLimit(category, limit);
                    break;
                case 3:
                    System.out.print("Enter transaction category: ");
                    category = scanner.next();
                    Transaction transaction = budget.transactions.get(category);
                    if (transaction != null) {
                        budget.checkLimit(transaction);
                    } else {
                        System.out.println("Transaction not found for category: " + category);
                    }
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
```