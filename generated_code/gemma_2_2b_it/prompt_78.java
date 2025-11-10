```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Auction {
    static List<Bid> bids = new ArrayList<>();
    static List<Item> items = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeItems();
        int itemIndex = 0;
        while (true) {
            printAuction();
            System.out.println("Choose an action: ");
            System.out.println("1. Place a bid");
            System.out.println("2. Continue");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    placeBid(itemIndex);
                    break;
                case 2:
                    continueAuction();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void initializeItems() {
        items.add(new Item("Apple", 10));
        items.add(new Item("Banana", 5));
        items.add(new Item("Orange", 8));
    }

    private static void printAuction() {
        for (Item item : items) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    private static void placeBid(int itemIndex) {
        System.out.println("Enter bid amount: ");
        int bidAmount = scanner.nextInt();
        Bid bid = new Bid(bidAmount, itemIndex);
        bids.add(bid);
        System.out.println("Bid placed successfully!");
    }

    private static void continueAuction() {
        System.out.println("Auction continues...");
        System.out.println("Please wait for the timer to expire");
        // Add timer functionality
    }
}

class Item {
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class Bid {
    private int amount;
    private int itemId;

    public Bid(int amount, int itemId) {
        this.amount = amount;
        this.itemId = itemId;
    }

    public int getAmount() {
        return amount;
    }

    public int getItemId() {
        return itemId;
    }
}
```