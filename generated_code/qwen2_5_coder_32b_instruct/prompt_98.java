```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> stocks;
    private double cash;

    public Portfolio() {
        stocks = new HashMap<>();
        cash = 10000.0;
    }

    public void buyStock(Stock stock, int quantity) {
        if (quantity * stock.getPrice() <= cash) {
            stocks.put(stock.getSymbol(), stocks.getOrDefault(stock.getSymbol(), 0) + quantity);
            cash -= quantity * stock.getPrice();
            System.out.println("Bought " + quantity + " of " + stock.getSymbol());
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        if (stocks.getOrDefault(stock.getSymbol(), 0) >= quantity) {
            stocks.put(stock.getSymbol(), stocks.get(stock.getSymbol()) - quantity);
            cash += quantity * stock.getPrice();
            System.out.println("Sold " + quantity + " of " + stock.getSymbol());
        } else {
            System.out.println("Not enough shares to sell");
        }
    }

    public void trackPortfolio(Map<String, Stock> market) {
        double totalValue = cash;
        System.out.println("Portfolio:");
        for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            double currentPrice = market.get(symbol).getPrice();
            double value = quantity * currentPrice;
            totalValue += value;
            System.out.println(symbol + ": " + quantity + " shares at $" + currentPrice + " each, value: $" + value);
        }
        System.out.println("Total portfolio value: $" + totalValue);
    }
}

public class StockTradingSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150.0));
        market.put("GOOGL", new Stock("GOOGL", 2800.0));
        market.put("MSFT", new Stock("MSFT", 300.0));

        Portfolio portfolio = new Portfolio();

        while (true) {
            System.out.println("\nOptions: buy, sell, track, exit");
            String option = scanner.nextLine().trim().toLowerCase();

            switch (option) {
                case "buy":
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (market.containsKey(buySymbol)) {
                        portfolio.buyStock(market.get(buySymbol), buyQuantity);
                    } else {
                        System.out.println("Invalid stock symbol");
                    }
                    break;

                case "sell":
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (portfolio.stocks.containsKey(sellSymbol)) {
                        portfolio.sellStock(market.get(sellSymbol), sellQuantity);
                    } else {
                        System.out.println("No shares of this stock in your portfolio");
                    }
                    break;

                case "track":
                    portfolio.trackPortfolio(market);
                    break;

                case "exit":
                    System.exit(0);

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
```