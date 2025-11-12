```java
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Bid {
    private String bidderName;
    private double amount;

    public Bid(String bidderName, double amount) {
        this.bidderName = bidderName;
        this.amount = amount;
    }

    public String getBidderName() {
        return bidderName;
    }

    public double getAmount() {
        return amount;
    }
}

class AuctionItem {
    private String itemName;
    private double startingPrice;
    private double highestBid;
    private String highestBidder;
    private boolean isSold;

    public AuctionItem(String itemName, double startingPrice) {
        this.itemName = itemName;
        this.startingPrice = startingPrice;
        this.highestBid = startingPrice;
        this.highestBidder = null;
        this.isSold = false;
    }

    public void placeBid(Bid bid) {
        if (!isSold && bid.getAmount() > highestBid) {
            highestBid = bid.getAmount();
            highestBidder = bid.getBidderName();
        }
    }

    public double getHighestBid() {
        return highestBid;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public boolean isSold() {
        return isSold;
    }

    public void sell() {
        isSold = true;
    }

    public String getItemName() {
        return itemName;
    }
}

class Auction {
    private List<AuctionItem> items;
    private Timer timer;

    public Auction() {
        items = new ArrayList<>();
        timer = new Timer();
    }

    public void addItem(AuctionItem item) {
        items.add(item);
    }

    public void startAuction(int durationInMilliseconds) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endAuction();
            }
        }, durationInMilliseconds);
    }

    private void endAuction() {
        for (AuctionItem item : items) {
            if (item.getHighestBidder() != null) {
                item.sell();
                System.out.println("Item " + item.getItemName() + " sold to " + item.getHighestBidder() + " for " + item.getHighestBid());
            } else {
                System.out.println("Item " + item.getItemName() + " did not sell.");
            }
        }
        timer.cancel();
    }

    public void placeBid(String itemName, Bid bid) {
        for (AuctionItem item : items) {
            if (item.getItemName().equals(itemName)) {
                item.placeBid(bid);
                break;
            }
        }
    }
}
```