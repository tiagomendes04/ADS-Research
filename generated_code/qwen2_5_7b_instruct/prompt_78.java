```java
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Auction {
    private String item;
    private Map<String, Integer> bids;
    private Date startTime;
    private Date endTime;

    public Auction(String item, Date startTime, Date endTime) {
        this.item = item;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bids = new HashMap<>();
    }

    public void placeBid(String bidder, int amount) {
        if (isAuctionActive()) {
            bids.put(bidder, amount);
        }
    }

    public String getWinner() {
        if (isAuctionActive()) {
            throw new IllegalStateException("Auction is still active");
        }
        int highestBid = 0;
        String winner = "";
        for (Map.Entry<String, Integer> entry : bids.entrySet()) {
            if (entry.getValue() > highestBid) {
                highestBid = entry.getValue();
                winner = entry.getKey();
            }
        }
        return winner;
    }

    private boolean isAuctionActive() {
        return new Date().before(endTime);
    }
}

public class Main {
    public static void main(String[] args) {
        Auction auction = new Auction("Rare Book", new Date(), new Date(System.currentTimeMillis() + 10000));
        auction.placeBid("Alice", 100);
        auction.placeBid("Bob", 150);
        auction.placeBid("Charlie", 200);
        System.out.println("Winner: " + auction.getWinner());
    }
}
```