public class Auction {
    private final String itemId;
    private final long endTimeMillis;
    private final List<Bid> bids = new ArrayList<>();
    private volatile boolean closed = false;
    private Bid winningBid = null;

    public Auction(String itemId, long durationMillis) {
        this.itemId = itemId;
        this.endTimeMillis = System.currentTimeMillis() + durationMillis;
        new Thread(new AuctionTimer(this, durationMillis)).start();
    }

    public synchronized void placeBid(User bidder, double amount) {
        if (closed) {
            throw new IllegalStateException("Auction is closed.");
        }
        if (System.currentTimeMillis() >= endTimeMillis) {
            closeAuction();
            throw new IllegalStateException("Auction time has ended.");
        }
        if (!bids.isEmpty() && amount <= bids.get(bids.size() - 1).getAmount()) {
            throw new IllegalArgumentException("Bid must be higher than current highest bid.");
        }
        bids.add(new Bid(bidder, amount, System.currentTimeMillis()));
    }

    public synchronized void closeAuction() {
        if (closed) return;
        closed = true;
        if (!bids.isEmpty()) {
            winningBid = bids.get(bids.size() - 1);
        }
        // Optionally notify winner/losers here
    }

    public synchronized Optional<Bid> getWinningBid() {
        return Optional.ofNullable(winningBid);
    }

    public String getItemId() {
        return itemId;
    }

    public boolean isClosed() {
        return closed;
    }

    public List<Bid> getAllBids() {
        return Collections.unmodifiableList(bids);
    }
}

public class Bid {
    private final User bidder;
    private final double amount;
    private final long timestamp;

    public Bid(User bidder, double amount, long timestamp) {
        this.bidder = bidder;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public User getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidder=" + bidder.getUsername() +
                ", amount=" + amount +
                ", timestamp=" + new Date(timestamp) +
                '}';
    }
}

public class AuctionTimer implements Runnable {
    private final Auction auction;
    private final long durationMillis;

    public AuctionTimer(Auction auction, long durationMillis) {
        this.auction = auction;
        this.durationMillis = durationMillis;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException ignored) {
        }
        auction.closeAuction();
    }
}

public class User {
    private final String username;
    private final String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

public class AuctionDemo {
    public static void main(String[] args) throws InterruptedException {
        User alice = new User("alice", "alice@example.com");
        User bob = new User("bob", "bob@example.com");

        Auction auction = new Auction("item-123", 10_000); // 10 seconds

        auction.placeBid(alice, 100.0);
        Thread.sleep(2000);
        auction.placeBid(bob, 150.0);
        Thread.sleep(3000);
        auction.placeBid(alice, 200.0);

        // Wait for auction to close
        Thread.sleep(6000);

        auction.getWinningBid().ifPresentOrElse(
                bid -> System.out.println("Winner: " + bid.getBidder().getUsername() + " with $" + bid.getAmount()),
                () -> System.out.println("No bids placed.")
        );
    }
}