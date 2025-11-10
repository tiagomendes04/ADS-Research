```java
import java.util.ArrayList;
import java.util.List;

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

class Trade {
    private String symbol;
    private double quantity;
    private double price;

    public Trade(String symbol, double quantity, double price) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getValue() {
        return quantity * price;
    }
}

class Portfolio {
    private List<Trade> trades;

    public Portfolio() {
        trades = new ArrayList<>();
    }

    public void buy(Trade trade) {
        trades.add(trade);
    }

    public void sell(Trade trade) {
        trades.removeIf(t -> t.getSymbol().equals(trade.getSymbol()) && t.getQuantity() == trade.getQuantity());
    }

    public double getPortfolioValue() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }
}

public class StockTradingSimulator {
    public static void main(String[] args) {
        Stock apple = new Stock("AAPL", 150.0);
        Stock google = new Stock("GOOGL", 2800.0);

        Portfolio portfolio = new Portfolio();
        portfolio.buy(new Trade(apple.getSymbol(), 10, apple.getPrice()));
        portfolio.buy(new Trade(google.getSymbol(), 5, google.getPrice()));

        System.out.println("Portfolio Value: $" + portfolio.getPortfolioValue());

        portfolio.sell(new Trade(apple.getSymbol(), 10, apple.getPrice()));

        System.out.println("Portfolio Value after selling AAPL: $" + portfolio.getPortfolioValue());
    }
}
```