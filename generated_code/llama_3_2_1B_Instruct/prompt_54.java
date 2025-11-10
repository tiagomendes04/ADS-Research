```java
import java.util.HashMap;
import java.util.Map;

public class WorkflowSystem {
    public static Map<String, Workflow> workflow = new HashMap<>();

    static {
        workflow.put("IN_PROGRESS", new Workflow() {
            @Override
            public void start() {
                System.out.println("Order is being processed");
            }

            @Override
            public void finish() {
                System.out.println("Order finished");
            }

            @Override
            public void handle(String action) {
                switch (action) {
                    case "accept":
                        System.out.println("Order accepted");
                        break;
                    case "reject":
                        System.out.println("Order rejected");
                        break;
                    default:
                        System.out.println("Unknown action");
                }
            }
        });

        workflow.put("REJECTED", new Workflow() {
            @Override
            public void start() {
                System.out.println("Order is being rejected");
            }

            @Override
            public void finish() {
                System.out.println("Order rejected");
            }

            @Override
            public void handle(String action) {
                switch (action) {
                    case "accept":
                        System.out.println("Order accepted");
                        break;
                    case "reject":
                        System.out.println("Order rejected");
                        break;
                    default:
                        System.out.println("Unknown action");
                }
            }
        });

        workflow.put("PENDING", new Workflow() {
            @Override
            public void start() {
                System.out.println("Order is being processed");
            }

            @Override
            public void finish() {
                System.out.println("Order finished");
            }

            @Override
            public void handle(String action) {
                switch (action) {
                    case "accept":
                        System.out.println("Order accepted");
                        break;
                    case "reject":
                        System.out.println("Order rejected");
                        break;
                    default:
                        System.out.println("Unknown action");
                }
            }
        });
    }

    public static void main(String[] args) {
        workflow.get("IN_PROGRESS").start();
        workflow.get("PENDING").start();
    }
}

class Workflow {
    private String state;

    public Workflow() {
        state = "";
    }

    public void start() {
        System.out.println(state);
    }

    public void finish() {
        System.out.println(state);
    }

    public void handle(String action) {
        switch (action) {
            case "accept":
                state = "ACCEPTED";
                System.out.println(state);
                break;
            case "reject":
                state = "REJECTED";
                System.out.println(state);
                break;
            default:
                System.out.println("Unknown action");
        }
    }
}
```