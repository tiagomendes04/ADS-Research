```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class Workflow {
    private final List<Consumer<String>> steps = new ArrayList<>();

    public void addStep(Consumer<String> step) {
        steps.add(step);
    }

    public void execute(String input) {
        for (Consumer<String> step : steps) {
            step.accept(input);
        }
    }
}

public class WorkflowEngine {
    public static void main(String[] args) {
        Workflow workflow = new Workflow();
        
        workflow.addStep(input -> System.out.println("Step 1: Starting with " + input));
        workflow.addStep(input -> System.out.println("Step 2: Processing " + input));
        workflow.addStep(input -> System.out.println("Step 3: Completing " + input));

        workflow.execute("Initial Data");
    }
}
```