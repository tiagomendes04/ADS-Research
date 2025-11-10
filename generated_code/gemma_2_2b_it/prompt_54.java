```java
import java.util.HashMap;
import java.util.Map;

public class OrderProcessor {

    private Map<String, OrderState> orderStates;

    public OrderProcessor() {
        orderStates = new HashMap<>();
        orderStates.put("Pending", new PendingOrderState());
        orderStates.put("Processing", new ProcessingOrderState());
        orderStates.put("Shipped", new ShippedOrderState());
        orderStates.put("Delivered", new DeliveredOrderState());
    }

    public void processOrder(String orderId, OrderState nextState) {
        OrderState current = orderStates.get(orderId);
        if (current != null) {
            current.transitionTo(nextState);
        } else {
            throw new IllegalArgumentException("Invalid Order ID");
        }
    }

    public void printOrderStatus(String orderId) {
        OrderState current = orderStates.get(orderId);
        if (current != null) {
            current.printStatus();
        } else {
            System.out.println("Order not found");
        }
    }
}

interface OrderState {
    void transitionTo(OrderState nextState);
    void printStatus();
}

class PendingOrderState implements OrderState {
    @Override
    public void transitionTo(OrderState nextState) {
        // TODO: Implement transition logic
    }

    @Override
    public void printStatus() {
        System.out.println("Order is pending");
    }
}

class ProcessingOrderState implements OrderState {
    @Override
    public void transitionTo(OrderState nextState) {
        // TODO: Implement transition logic
    }

    @Override
    public void printStatus() {
        System.out.println("Order is being processed");
    }
}

class ShippedOrderState implements OrderState {
    @Override
    public void transitionTo(OrderState nextState) {
        // TODO: Implement transition logic
    }

    @Override
    public void printStatus() {
        System.out.println("Order is shipped");
    }
}

class DeliveredOrderState implements OrderState {
    @Override
    public void transitionTo(OrderState nextState) {
        // TODO: Implement transition logic
    }

    @Override
    public void printStatus() {
        System.out.println("Order is delivered");
    }
}
```