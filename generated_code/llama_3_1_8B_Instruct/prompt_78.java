```java
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionSystem {
    private List<AuctionItem> items;
    private Timer timer;
    private long duration;

    public AuctionSystem(long duration) {
        this.duration = duration;
        this.items = new ArrayList<>();
        this.timer = new Timer();
    }

    public void addAuctionItem(AuctionItem item) {
        items.add(item);
        item.startTimer(duration, timer);
    }

    public void startAuction() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                selectWinner();
            }
        }, 0, 1000); // 1 second interval
    }

    public void selectWinner() {
        if (!items.isEmpty()) {
            AuctionItem winner = items.stream()
                    .max((a, b) -> Long.compare(b.getBidAmount(), a.getBidAmount()))
                    .orElse(null);
            if (winner != null) {
                System.out.println("Winner of auction: " + winner.getAuctionItemName() + " with bid of " + winner.getBidAmount());
            }
        }
    }

    public void stopAuction() {
        timer.cancel();
    }
}
```

```java
import java.util.Timer;
import java.util.TimerTask;

public class AuctionItem {
    private String itemName;
    private long bidAmount;
    private Timer timer;

    public AuctionItem(String itemName) {
        this.itemName = itemName;
        this.bidAmount = 0;
    }

    public void bid(long amount) {
        bidAmount = amount;
    }

    public String getAuctionItemName() {
        return itemName;
    }

    public long getBidAmount() {
        return bidAmount;
    }

    public void startTimer(long duration, Timer timer) {
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (bidAmount == 0) {
                    System.out.println(itemName + " has no bids.");
                } else {
                    System.out.println(itemName + " has a bid of " + bidAmount);
                }
            }
        }, 0, 1000); // 1 second interval
    }
}
```

```java
public class Bidder {
    private String name;
    private AuctionSystem auctionSystem;
    private AuctionItem auctionItem;

    public Bidder(String name, AuctionSystem auctionSystem, AuctionItem auctionItem) {
        this.name = name;
        this.auctionSystem = auctionSystem;
        this.auctionItem = auctionItem;
    }

    public void placeBid(long amount) {
        auctionItem.bid(amount);
        System.out.println(name + " has placed a bid of " + amount + " on " + auctionItem.getAuctionItemName());
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        AuctionSystem auctionSystem = new AuctionSystem(10000); // 10 seconds
        AuctionItem item1 = new AuctionItem("Item 1");
        AuctionItem item2 = new AuctionItem("Item 2");

        auctionSystem.addAuctionItem(item1);
        auctionSystem.addAuctionItem(item2);

        auctionSystem.startAuction();

        Bidder bidder1 = new Bidder("John", auctionSystem, item1);
        Bidder bidder2 = new Bidder("Jane", auctionSystem, item2);

        bidder1.placeBid(100);
        bidder2.placeBid(200);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        auctionSystem.stopAuction();
    }
}
```