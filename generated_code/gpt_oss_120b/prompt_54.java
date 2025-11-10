```java
import java.util.*;

enum OrderState {
    CREATED,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED
}

class InvalidTransitionException extends RuntimeException {
    public InvalidTransitionException(String message) {
        super(message);
    }
}

class Order {
    private UUID id;
    private OrderState state;
    private final List<String> history = new ArrayList<>();

    public Order() {
        this.id = UUID.randomUUID();
        this.state = OrderState.CREATED;
        record("Order created");
    }

    public UUID getId() {
        return id;
    }

    public OrderState getState() {
        return state;
    }

    public List<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    private void record(String action) {
        history.add(String.format("%s - %s", new Date(), action));
    }

    public void pay() {
        if (state != OrderState.CREATED) {
            throw new InvalidTransitionException("Pay not allowed from state " + state);
        }
        state = OrderState.PAID;
        record("Payment received");
    }

    public void ship() {
        if (state != OrderState.PAID) {
            throw new InvalidTransitionException("Ship not allowed from state " + state);
        }
        state = OrderState.SHIPPED;
        record("Order shipped");
    }

    public void deliver() {
        if (state != OrderState.SHIPPED) {
            throw new InvalidTransitionException("Deliver not allowed from state " + state);
        }
        state = OrderState.DELIVERED;
        record("Order delivered");
    }

    public void cancel() {
        if (state == OrderState.DELIVERED || state == OrderState.CANCELLED) {
            throw new InvalidTransitionException("Cancel not allowed from state " + state);
        }
        state = OrderState.CANCELLED;
        record("Order cancelled");
    }
}

public class OrderWorkflowDemo {
    public static void main(String[] args) {
        Order order = new Order();
        System.out.println("Initial state: " + order.getState());

        order.pay();
        System.out.println("After pay: " + order.getState());

        order.ship();
        System.out.println("After ship: " + order.getState());

        order.deliver();
        System.out.println("After deliver: " + order.getState());

        System.out.println("\nHistory:");
        order.getHistory().forEach(System.out::println);
    }
}
```