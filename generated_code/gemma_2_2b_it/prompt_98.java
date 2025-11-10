```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StockSimulator {

    static HashMap<String, Double> portfolio = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        // Initialize portfolio with some stocks
        portfolio.put("AAPL", 100.0);
        portfolio.put("MSFT", 200.0);
        portfolio.put("GOOG", 1500.0);

        while (true) {
            System.out.println("\nStock Simulator");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. Track Portfolio");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    buyStock();
                    break;
                case 2:
                    sellStock();
                    break;
                case 3:
                    trackPortfolio();
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void buyStock() {
        System.out.print("Enter the stock symbol: ");
        String stockSymbol = scanner.next();

        System.out.print("Enter the quantity to buy: ");
        int quantity = scanner.nextInt();

        Double price = getStockPrice(stockSymbol);
        if (price != null) {
            portfolio.put(stockSymbol, portfolio.getOrDefault(stockSymbol, 0.0) + quantity * price);
            System.out.println("Successfully bought " + quantity + " shares of " + stockSymbol);
        } else {
            System.out.println("Stock not found. Please try again.");
        }
    }

    static void sellStock() {
        System.out.print("Enter the stock symbol: ");
        String stockSymbol = scanner.next();

        Double quantity = portfolio.getOrDefault(stockSymbol, 0.0);
        if (quantity > 0) {
            portfolio.put(stockSymbol, portfolio.getOrDefault(stockSymbol, 0.0) - quantity);
            System.out.println("Successfully sold " + quantity + " shares of " + stockSymbol);
        } else {
            System.out.println("No shares to sell.");
        }
    }

    static void trackPortfolio() {
        System.out.println("\nCurrent Portfolio:");
        for (String symbol : portfolio.keySet()) {
            System.out.println(symbol + ": " + portfolio.get(symbol));
        }
    }

    static Double getStockPrice(String stockSymbol) {
        // Placeholder: You need to implement actual stock price retrieval logic
        return 100.0;
    }
}
```