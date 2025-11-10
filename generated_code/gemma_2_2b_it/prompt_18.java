```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void equip(Item item) {
        if (items.contains(item)) {
            System.out.println("Equipped " + item.getName());
        } else {
            System.out.println("Item not found!");
        }
    }

    public void combine(Item item1, Item item2) {
        if (items.contains(item1) && items.contains(item2)) {
            Item combinedItem = new Item(item1.getName() + " " + item2.getName());
            items.add(combinedItem);
            System.out.println("Combined " + combinedItem.getName());
        } else {
            System.out.println("One or both items not found!");
        }
    }
}

class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class GameInventory {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Inventory inventory = new Inventory();

        while (true) {
            System.out.println("Inventory Menu:");
            System.out.println("1. Add Item");
            System.out.println("2. Equip Item");
            System.out.println("3. Combine Items");
            System.out.println("4. List Items");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String itemName = scanner.nextLine();
                    inventory.addItem(new Item(itemName));
                    break;
                case 2:
                    System.out.print("Enter item name to equip: ");
                    String equipItemName = scanner.nextLine();
                    inventory.equip(new Item(equipItemName));
                    break;
                case 3:
                    System.out.print("Enter item names to combine: ");
                    String combineItemName1 = scanner.nextLine();
                    String combineItemName2 = scanner.nextLine();
                    inventory.combine(new Item(combineItemName1), new Item(combineItemName2));
                    break;
                case 4:
                    System.out.println("Current Inventory:");
                    for (Item item : inventory.getItems()) {
                        System.out.println(item);
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
```