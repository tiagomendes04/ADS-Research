```java
import java.util.HashMap;
import java.util.Map;

public class WorkflowEngine {
    private Map<String, Step> steps = new HashMap<>();

    public WorkflowEngine() {
        steps.put("step1", new Step1());
        steps.put("step2", new Step2());
        steps.put("step3", new Step3());
    }

    public void start() {
        Step currentStep = steps.get("step1");
        while (currentStep != null) {
            System.out.println("Executing step: " + currentStep.getName());
            currentStep.execute();
            currentStep = steps.get(currentStep.getNextStep());
        }
    }

    public static void main(String[] args) {
        WorkflowEngine engine = new WorkflowEngine();
        engine.start();
    }
}

abstract class Step {
    protected String name;
    protected String nextStep;

    Step() {
        this.name = this.getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void execute() {
        System.out.println("Executing " + name);
        // Logic for the step
    }
}

class Step1 extends Step {
    {
        nextStep = "step2";
    }
}

class Step2 extends Step {
    {
        nextStep = "step3";
    }
}

class Step3 extends Step {
    {
        nextStep = null;
    }
}
```