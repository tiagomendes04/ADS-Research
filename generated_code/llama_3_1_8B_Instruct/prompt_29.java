```java
public interface WorkflowEngine {
    void startWorkflow(String processDefinitionId);
    void addStep(String processDefinitionId, String stepName);
    void addStep(String processDefinitionId, String stepName, Step nextStep);
    void addTransition(String processDefinitionId, String currentStep, String nextStep);
    void executeWorkflow(String processDefinitionId);
    void registerStep(Step step);
}
```

```java
public interface Step {
    String getName();
    Step getNextStep();
    void execute();
}
```

```java
public class ConcreteStep implements Step {
    private String name;
    private Step nextStep;

    public ConcreteStep(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Step getNextStep() {
        return nextStep;
    }

    public void setNextStep(Step nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public void execute() {
        System.out.println("Executing step: " + name);
        if (nextStep != null) {
            nextStep.execute();
        }
    }
}
```

```java
import java.util.*;

public class WorkflowEngineImpl implements WorkflowEngine {
    private Map<String, List<Step>> steps;
    private Map<String, Map<String, Step>> transitions;
    private Map<String, Step> registeredSteps;

    public WorkflowEngineImpl() {
        this.steps = new HashMap<>();
        this.transitions = new HashMap<>();
        this.registeredSteps = new HashMap<>();
    }

    @Override
    public void startWorkflow(String processDefinitionId) {
        if (!steps.containsKey(processDefinitionId)) {
            System.out.println("Process definition not found");
            return;
        }
        executeWorkflow(processDefinitionId);
    }

    @Override
    public void addStep(String processDefinitionId, String stepName) {
        if (!steps.containsKey(processDefinitionId)) {
            steps.put(processDefinitionId, new ArrayList<>());
        }
        steps.get(processDefinitionId).add(new ConcreteStep(stepName));
    }

    @Override
    public void addStep(String processDefinitionId, String stepName, Step nextStep) {
        addStep(processDefinitionId, stepName);
        ConcreteStep concreteStep = (ConcreteStep) getStep(processDefinitionId, stepName);
        concreteStep.setNextStep(nextStep);
    }

    @Override
    public void addTransition(String processDefinitionId, String currentStep, String nextStep) {
        if (!transitions.containsKey(processDefinitionId)) {
            transitions.put(processDefinitionId, new HashMap<>());
        }
        transitions.get(processDefinitionId).put(currentStep, nextStep);
    }

    @Override
    public void executeWorkflow(String processDefinitionId) {
        if (!steps.containsKey(processDefinitionId)) {
            System.out.println("Process definition not found");
            return;
        }
        List<Step> stepsList = steps.get(processDefinitionId);
        for (Step step : stepsList) {
            step.execute();
        }
    }

    @Override
    public void registerStep(Step step) {
        registeredSteps.put(step.getName(), step);
    }

    private Step getStep(String processDefinitionId, String stepName) {
        for (Step step : steps.get(processDefinitionId)) {
            if (step.getName().equals(stepName)) {
                return step;
            }
        }
        return null;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        WorkflowEngine engine = new WorkflowEngineImpl();

        engine.addStep("process1", "step1");
        engine.addStep("process1", "step2", new ConcreteStep("step3"));
        engine.addTransition("process1", "step1", "step2");
        engine.registerStep(new ConcreteStep("step3"));

        engine.startWorkflow("process1");
    }
}
```