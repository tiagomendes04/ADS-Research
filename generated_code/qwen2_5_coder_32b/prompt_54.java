```java
import java.util.HashMap;
import java.util.Map;

enum OrderState {
    CREATED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}

interface OrderAction {
    void perform(Order order);
}

class CreateOrder implements OrderAction {
    @Override
    public void perform(Order order) {
        order.setState(OrderState.CREATED);
    }
}

class ProcessOrder implements OrderAction {
    @Override
    public void perform(Order order) {
        if (order.getState() == OrderState.CREATED) {
            order.setState(OrderState.PROCESSING);
        }
    }
}

class ShipOrder implements OrderAction {
    @Override
    public void perform(Order order) {
        if (order.getState() == OrderState.PROCESSING) {
            order.setState(OrderState.SHIPPED);
        }
    }
}

class DeliverOrder implements OrderAction {
    @Override
    public void perform(Order order) {
        if (order.getState() == OrderState.SHIPPED) {
            order.setState(OrderState.DELIVERED);
        }
    }
}

class CancelOrder implements OrderAction {
    @Override
    public void perform(Order order) {
        if (order.getState() != OrderState.DELIVERED) {
            order.setState(OrderState.CANCELLED);
        }
    }
}

class Order {
    private OrderState state;
    private Map<OrderState, OrderAction> transitions;

    public Order() {
        this.state = OrderState.CREATED;
        this.transitions = new HashMap<>();
        transitions.put(OrderState.CREATED, new ProcessOrder());
        transitions.put(OrderState.PROCESSING, new ShipOrder());
        transitions.put(OrderState.SHIPPED, new DeliverOrder());
        transitions.put(OrderState.CREATED, new CancelOrder());
        transitions.put(OrderState.PROCESSING, new CancelOrder());
        transitions.put(OrderState.SHIPPED, new CancelOrder());
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void transitionTo(OrderState nextState) {
        OrderAction action = transitions.get(nextState);
        if (action != null) {
            action.perform(this);
        }
    }
}

public class WorkflowSystem {
    public static void main(String[] args) {
        Order order = new Order();
        order.transitionTo(OrderState.PROCESSING);
        order.transitionTo(OrderState.SHIPPED);
        order.transitionTo(OrderState.DELIVERED);
        System.out.println("Final State: " + order.getState());
    }
}
```