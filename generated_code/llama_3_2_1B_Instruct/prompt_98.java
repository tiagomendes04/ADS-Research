```java
import java.util.*;

class Stock {
    String name;
    double price;
    double quantity;

    public Stock(String name, double price, double quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

class Portfolio {
    List<Stock> stocks;

    public Portfolio() {
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public void sell(Stock stock, int quantity) {
        if (stock.quantity >= quantity) {
            stock.quantity -= quantity;
            for (int i = 0; i < quantity; i++) {
                System.out.println(stock.name + " sold for $" + stock.price);
            }
        } else {
            System.out.println("Not enough stocks available");
        }
    }

    public double calculateValue() {
        double totalValue = 0;
        for (Stock stock : this.stocks) {
            totalValue += stock.price * stock.quantity;
        }
        return totalValue;
    }
}

public class Main {
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();

        Stock stock1 = new Stock("AAPL", 150, 10);
        Stock stock2 = new Stock("GOOG", 2000, 5);
        Stock stock3 = new Stock("MSFT", 300, 8);

        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);

        System.out.println("Initial value: $" + portfolio.calculateValue());

        portfolio.sell(stock1, 2);
        System.out.println("Value after selling AAPL: $" + portfolio.calculateValue());
    }
}
```