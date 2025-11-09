// OrderItem class
public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private String customization;

    public OrderItem(MenuItem menuItem, int quantity) {
        this(menuItem, quantity, null);
    }

    public OrderItem(MenuItem menuItem, int quantity, String customization) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.customization = customization;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomization() {
        return customization;
    }

    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    @Override
    public String toString() {
        String str =  menuItem.getName() + " x " + quantity;
        if (customization != null && !customization.isEmpty()) {
            str += " (Customization: " + customization + ")";
        }
        return str;
    }
}