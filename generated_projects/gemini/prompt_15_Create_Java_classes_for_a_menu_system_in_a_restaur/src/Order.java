// Order class
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items;
    private String customerName;
    private String orderNotes;

    public Order(String customerName) {
        this(customerName, null);
    }

    public Order(String customerName, String orderNotes) {
        this.items = new ArrayList<>();
        this.customerName = customerName;
        this.orderNotes = orderNotes;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order for: ").append(customerName).append("\n");
        if (orderNotes != null && !orderNotes.isEmpty()) {
            sb.append("Notes: ").append(orderNotes).append("\n");
        }
        for (OrderItem item : items) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("Total: $").append(getTotal());
        return sb.toString();
    }
}