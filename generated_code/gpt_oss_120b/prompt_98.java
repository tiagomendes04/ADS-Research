```java
import java.util.*;

class Stock {
    private final String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol.toUpperCase();
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
    private final Map<String, Integer> holdings = new HashMap<>();
    private double cash;

    public Portfolio(double initialCash) {
        this.cash = initialCash;
    }

    public double getCash() {
        return cash;
    }

    public void deposit(double amount) {
        cash += amount;
    }

    public boolean buy(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > cash) return false;
        cash -= cost;
        holdings.merge(stock.getSymbol(), quantity, Integer::sum);
        return true;
    }

    public boolean sell(Stock stock, int quantity) {
        Integer owned = holdings.getOrDefault(stock.getSymbol(), 0);
        if (owned < quantity) return false;
        double proceeds = stock.getPrice() * quantity;
        cash += proceeds;
        if (owned == quantity) holdings.remove(stock.getSymbol());
        else holdings.put(stock.getSymbol(), owned - quantity);
        return true;
    }

    public Map<String, Integer> getHoldings() {
        return Collections.unmodifiableMap(holdings);
    }

    public double getMarketValue(Map<String, Stock> market) {
        double value = cash;
        for (Map.Entry<String, Integer> e : holdings.entrySet()) {
            Stock s = market.get(e.getKey());
            if (s != null) value += s.getPrice() * e.getValue();
        }
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cash: $").append(String.format("%.2f", cash)).append("\n");
        sb.append("Holdings:\n");
        if (holdings.isEmpty()) sb.append("  (none)\n");
        else {
            for (Map.Entry<String, Integer> e : holdings.entrySet()) {
                sb.append("  ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
            }
        }
        return sb.toString();
    }
}

public class TradeSimulator {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Stock> market = new HashMap<>();
    private static Portfolio portfolio;

    public static void main(String[] args) {
        initMarket();
        System.out.print("Enter starting cash: ");
        double cash = readDouble();
        portfolio = new Portfolio(cash);
        System.out.println("\n--- Stock Trading Simulator ---");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1) View Market");
            System.out.println("2) View Portfolio");
            System.out.println("3) Buy Stock");
            System.out.println("4) Sell Stock");
            System.out.println("5) Update Prices");
            System.out.println("6) Exit");
            System.out.print("Choose an option: ");
            int choice = readInt();
            switch (choice) {
                case 1 -> viewMarket();
                case 2 -> viewPortfolio();
                case 3 -> buyStock();
                case 4 -> sellStock();
                case 5 -> updatePrices();
                case 6 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void initMarket() {
        market.put("AAPL", new Stock("AAPL", 150.00));
        market.put("GOOG", new Stock("GOOG", 2800.00));
        market.put("MSFT", new Stock("MSFT", 300.00));
        market.put("AMZN", new Stock("AMZN", 3400.00));
        market.put("TSLA", new Stock("TSLA", 750.00));
    }

    private static void viewMarket() {
        System.out.println("\n--- Market Prices ---");
        for (Stock s : market.values()) {
            System.out.println(s.getSymbol() + ": $" + String.format("%.2f", s.getPrice()));
        }
    }

    private static void viewPortfolio() {
        System.out.println("\n--- Portfolio ---");
        System.out.println(portfolio);
        double total = portfolio.getMarketValue(market);
        System.out.println("Total Market Value: $" + String.format("%.2f", total));
    }

    private static void buyStock() {
        System.out.print("Enter stock symbol to BUY: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        Stock stock = market.get(symbol);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = readInt();
        if (qty <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        boolean success = portfolio.buy(stock, qty);
        if (success) System.out.println("Bought " + qty + " shares of " + symbol + ".");
        else System.out.println("Insufficient cash.");
    }

    private static void sellStock() {
        System.out.print("Enter stock symbol to SELL: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        Stock stock = market.get(symbol);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = readInt();
        if (qty <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        boolean success = portfolio.sell(stock, qty);
        if (success) System.out.println("Sold " + qty + " shares of " + symbol + ".");
        else System.out.println("Insufficient holdings.");
    }

    private static void updatePrices() {
        Random rand = new Random();
        for (Stock s : market.values()) {
            double changePct = (rand.nextDouble() - 0.5) * 0.1; // -5% to +5%
            double newPrice = s.getPrice() * (1 + changePct);
            s.setPrice(Math.max(newPrice, 0.01));
        }
        System.out.println("Prices updated.");
    }

    private static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, try again: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, try again: ");
            }
        }
    }
}
```