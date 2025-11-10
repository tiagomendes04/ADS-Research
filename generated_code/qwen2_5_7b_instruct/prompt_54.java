```java
import java.util.HashMap;
import java.util.Map;

public class OrderWorkflow {

    public enum OrderState {
        PENDING, PROCESSING, SHIPPED, COMPLETED, CANCELLED
    }

    private OrderState currentState;
    private Map<OrderState, OrderState> transitionMap;

    public OrderWorkflow() {
        currentState = OrderState.PENDING;
        transitionMap = new HashMap<>();
        transitionMap.put(OrderState.PENDING, OrderState.PROCESSING);
        transitionMap.put(OrderState.PROCESSING, OrderState.SHIPPED);
        transitionMap.put(OrderState.SHIPPED, OrderState.COMPLETED);
        transitionMap.put(OrderState.COMPLETED, null);
        transitionMap.put(OrderState.CANCELLED, null);
    }

    public void processOrder() {
        currentState = transitionMap.get(OrderState.PENDING);
        if (currentState != null) {
            System.out.println("Order is now in " + currentState);
        } else {
            System.out.println("Order cannot be processed further.");
        }
    }

    public void shipOrder() {
        currentState = transitionMap.get(OrderState.PROCESSING);
        if (currentState != null) {
            System.out.println("Order is now in " + currentState);
        } else {
            System.out.println("Order cannot be shipped.");
        }
    }

    public void completeOrder() {
        currentState = transitionMap.get(OrderState.SHIPPED);
        if (currentState != null) {
            System.out.println("Order is now in " + currentState);
        } else {
            System.out.println("Order cannot be marked as completed.");
        }
    }

    public void cancelOrder() {
        currentState = OrderState.CANCELLED;
        System.out.println("Order is now cancelled.");
    }

    public static void main(String[] args) {
        OrderWorkflow workflow = new OrderWorkflow();
        workflow.processOrder();
        workflow.shipOrder();
        workflow.completeOrder();
        workflow.cancelOrder();
    }
}
```