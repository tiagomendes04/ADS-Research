```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String name;
    private double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Double> stocks;

    public Portfolio() {
        this.stocks = new HashMap<>();
    }

    public void addStock(String stockName, int quantity) {
        if (stocks.containsKey(stockName)) {
            stocks.put(stockName, stocks.get(stockName) + quantity);
        } else {
            stocks.put(stockName, (double) quantity);
        }
    }

    public void removeStock(String stockName, int quantity) {
        if (stocks.containsKey(stockName)) {
            if (stocks.get(stockName) <= quantity) {
                stocks.remove(stockName);
            } else {
                stocks.put(stockName, stocks.get(stockName) - quantity);
            }
        }
    }

    public void updateStockPrice(String stockName, double newPrice) {
        if (stocks.containsKey(stockName)) {
            double currentValue = stocks.get(stockName) * newPrice;
            System.out.println("Updated value of " + stockName + ": " + currentValue);
        }
    }

    public void printPortfolio() {
        System.out.println("Portfolio:");
        for (Map.Entry<String, Double> entry : stocks.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
        }
    }
}

public class StockTradingSimulator {
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Buy stock");
            System.out.println("2. Sell stock");
            System.out.println("3. Update stock price");
            System.out.println("4. Print portfolio");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter stock name: ");
                    String stockName = scanner.nextLine();
                    System.out.print("Enter quantity to buy: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    portfolio.addStock(stockName, quantity);
                    break;
                case 2:
                    System.out.print("Enter stock name: ");
                    stockName = scanner.nextLine();
                    System.out.print("Enter quantity to sell: ");
                    quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    portfolio.removeStock(stockName, quantity);
                    break;
                case 3:
                    System.out.print("Enter stock name: ");
                    stockName = scanner.nextLine();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left-over
                    portfolio.updateStockPrice(stockName, newPrice);
                    break;
                case 4:
                    portfolio.printPortfolio();
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