```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AuctionSystem {
    private List<AuctionItem> items;
    private List<Bidder> bidders;
    private int currentBidderIndex;

    public AuctionSystem() {
        items = new ArrayList<>();
        bidders = new ArrayList<>();
        currentBidderIndex = 0;
    }

    public void addItem(AuctionItem item) {
        items.add(item);
    }

    public void startAuction() {
        for (AuctionItem item : items) {
            item.startAuction();
        }
    }

    public void processBids() {
        for (AuctionItem item : items) {
            item.processBids();
        }
    }

    public void endAuction() {
        for (AuctionItem item : items) {
            item.endAuction();
        }
    }

    public void selectWinner() {
        List<AuctionItem> winningItems = new ArrayList<>();
        for (AuctionItem item : items) {
            if (item.isWon()) {
                winningItems.add(item);
            }
        }
        if (winningItems.isEmpty()) {
            System.out.println("No items won the auction.");
            return;
        }
        Random rand = new Random();
        int index = rand.nextInt(winningItems.size());
        auctionWinner = winningItems.get(index);
    }

    private int auctionWinner;

    public static void main(String[] args) {
        AuctionSystem auctionSystem = new AuctionSystem();
        auctionSystem.addItem(new AuctionItem("Item 1", 100));
        auctionSystem.addItem(new AuctionItem("Item 2", 200));
        auctionSystem.startAuction();
        auctionSystem.processBids();
        auctionSystem.endAuction();
        auctionSystem.selectWinner();
    }
}

class AuctionItem {
    private String name;
    private int price;
    private boolean won;

    public AuctionItem(String name, int price) {
        this.name = name;
        this.price = price;
        this.won = false;
    }

    public void startAuction() {
        System.out.println(name + " is being auctioned.");
    }

    public void processBids() {
        System.out.println(name + " has received bids from " + bidders.size() + " bidders.");
    }

    public void endAuction() {
        System.out.println(name + " has won the auction.");
        won = true;
    }

    public boolean isWon() {
        return won;
    }
}

class Bidder {
    private String name;

    public Bidder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public void startAuction() {
        System.out.println(name + " is being auctioned.");
    }

    public void processBids() {
        System.out.println(name + " has received bids from " + bidders.size() + " bidders.");
    }

    public boolean isWon() {
        return false;
    }
}
```

```java
class Bidder {
    private String name;

    public Bidder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```