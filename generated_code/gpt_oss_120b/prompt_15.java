```java
package com.restaurant.menu;

import java.util.*;

public class Menu {
    private final List<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Optional<MenuItem> findItemById(String id) {
        return categories.stream()
                .flatMap(c -> c.getItems().stream())
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }
}

class Category {
    private final String id;
    private final String name;
    private final List<MenuItem> items = new ArrayList<>();

    public Category(String id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<MenuItem> getItems() { return Collections.unmodifiableList(items); }
}

abstract class MenuItem {
    private final String id;
    private final String name;
    private final double basePrice;
    private final List<CustomizationOption> customizationOptions = new ArrayList<>();

    protected MenuItem(String id, String name, double basePrice) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.basePrice = basePrice;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }

    public void addCustomizationOption(CustomizationOption option) {
        customizationOptions.add(option);
    }

    public List<CustomizationOption> getCustomizationOptions() {
        return Collections.unmodifiableList(customizationOptions);
    }

    public abstract boolean isAvailable();
}

class FoodItem extends MenuItem {
    private final boolean available;

    public FoodItem(String id, String name, double basePrice, boolean available) {
        super(id, name, basePrice);
        this.available = available;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }
}

class DrinkItem extends MenuItem {
    private final boolean available;
    private final boolean alcoholic;

    public DrinkItem(String id, String name, double basePrice, boolean available, boolean alcoholic) {
        super(id, name, basePrice);
        this.available = available;
        this.alcoholic = alcoholic;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }
}

class CustomizationOption {
    private final String id;
    private final String description;
    private final double additionalPrice;
    private final boolean mandatory;
    private final List<String> allowedValues; // e.g., ["Small", "Medium", "Large"]

    public CustomizationOption(String id, String description, double additionalPrice,
                               boolean mandatory, List<String> allowedValues) {
        this.id = Objects.requireNonNull(id);
        this.description = Objects.requireNonNull(description);
        this.additionalPrice = additionalPrice;
        this.mandatory = mandatory;
        this.allowedValues = allowedValues != null ? new ArrayList<>(allowedValues) : Collections.emptyList();
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getAdditionalPrice() { return additionalPrice; }
    public boolean isMandatory() { return mandatory; }
    public List<String> getAllowedValues() { return Collections.unmodifiableList(allowedValues); }
}

/* ---------- ORDER DOMAIN ---------- */

class Order {
    private final String id;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.NEW;
    private final Date createdAt = new Date();

    public Order(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getId() { return id; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public OrderStatus getStatus() { return status; }
    public Date getCreatedAt() { return new Date(createdAt.getTime()); }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::calculatePrice).sum();
    }

    public void setStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status);
    }
}

class OrderItem {
    private final MenuItem menuItem;
    private final int quantity;
    private final Map<CustomizationOption, String> selectedCustomizations = new HashMap<>();

    public OrderItem(MenuItem menuItem, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        this.menuItem = Objects.requireNonNull(menuItem);
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }

    public void selectCustomization(CustomizationOption option, String value) {
        if (!menuItem.getCustomizationOptions().contains(option)) {
            throw new IllegalArgumentException("Option not applicable to this item");
        }
        if (!option.getAllowedValues().isEmpty() && !option.getAllowedValues().contains(value)) {
            throw new IllegalArgumentException("Invalid value for option");
        }
        selectedCustomizations.put(option, value);
    }

    public Map<CustomizationOption, String> getSelectedCustomizations() {
        return Collections.unmodifiableMap(selectedCustomizations);
    }

    public double calculatePrice() {
        double price = menuItem.getBasePrice();
        for (CustomizationOption opt : selectedCustomizations.keySet()) {
            price += opt.getAdditionalPrice();
        }
        return price * quantity;
    }
}

enum OrderStatus {
    NEW, PREPARING, READY, SERVED, CANCELLED
}

/* ---------- SAMPLE USAGE (for testing) ---------- */

class Demo {
    public static void main(String[] args) {
        // Build menu
        Menu menu = new Menu();

        Category burgers = new Category("c1", "Burgers");
        FoodItem cheeseburger = new FoodItem("i1", "Cheeseburger", 8.99, true);
        cheeseburger.addCustomizationOption(
                new CustomizationOption("opt1", "Add extra cheese", 0.99, false, Collections.emptyList()));
        cheeseburger.addCustomizationOption(
                new CustomizationOption("opt2", "Patty doneness", 0.0, true,
                        Arrays.asList("Rare", "Medium", "Well Done")));
        burgers.addItem(cheeseburger);
        menu.addCategory(burgers);

        Category drinks = new Category("c2", "Drinks");
        DrinkItem cola = new DrinkItem("i2", "Cola", 1.99, true, false);
        cola.addCustomizationOption(
                new CustomizationOption("opt3", "Size", 0.0, true,
                        Arrays.asList("Small", "Medium", "Large")));
        drinks.addItem(cola);
        menu.addCategory(drinks);

        // Create order
        Order order = new Order(UUID.randomUUID().toString());

        OrderItem orderItem1 = new OrderItem(cheeseburger, 2);
        orderItem1.selectCustomization(cheeseburger.getCustomizationOptions().get(0), "yes");
        orderItem1.selectCustomization(cheeseburger.getCustomizationOptions().get(1), "Medium");
        order.addItem(orderItem1);

        OrderItem orderItem2 = new OrderItem(cola, 1);
        orderItem2.selectCustomization(cola.getCustomizationOptions().get(0), "Large");
        order.addItem(orderItem2);

        System.out.println("Order total: $" + String.format("%.2f", order.getTotal()));
    }
}
```