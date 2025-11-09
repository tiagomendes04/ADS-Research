**Workflow Engine Implementation**
```java
// WorkflowStep.java
public abstract class WorkflowStep {
    private String id;
    private String name;

    public WorkflowStep(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void execute();
}

// SimpleStep.java
public class SimpleStep extends WorkflowStep {
    private String description;

    public SimpleStep(String id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    @Override
    public void execute() {
        System.out.println("Executing step: " + getName() + " (" + getId() + ")");
        System.out.println("Description: " + description);
    }
}

// ConditionalStep.java
public class ConditionalStep extends WorkflowStep {
    private boolean condition;
    private WorkflowStep yesStep;
    private WorkflowStep noStep;

    public ConditionalStep(String id, String name, boolean condition, WorkflowStep yesStep, WorkflowStep noStep) {
        super(id, name);
        this.condition = condition;
        this.yesStep = yesStep;
        this.noStep = noStep;
    }

    @Override
    public void execute() {
        if (condition) {
            yesStep.execute();
        } else {
            noStep.execute();
        }
    }
}

// ParallelStep.java
public class ParallelStep extends WorkflowStep {
    private List<WorkflowStep> steps;

    public ParallelStep(String id, String name, List<WorkflowStep> steps) {
        super(id, name);
        this.steps = steps;
    }

    @Override
    public void execute() {
        for (WorkflowStep step : steps) {
            step.execute();
        }
    }
}

// WorkflowEngine.java
public class WorkflowEngine {
    private WorkflowStep currentStep;

    public void startWorkflow(WorkflowStep firstStep) {
        currentStep = firstStep;
        currentStep.execute();
    }

    public void addStep(WorkflowStep step) {
        if (currentStep != null) {
            currentStep = new ConditionalStep("next-step", "Next Step", true, step, currentStep);
        } else {
            currentStep = step;
        }
    }

    public void addParallelSteps(List<WorkflowStep> steps) {
        if (currentStep != null) {
            currentStep = new ParallelStep("parallel-step", "Parallel Step", steps);
        } else {
            currentStep = new ParallelStep("parallel-step", "Parallel Step", steps);
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        WorkflowEngine engine = new WorkflowEngine();

        WorkflowStep step1 = new SimpleStep("step-1", "Step 1", "This is step 1");
        WorkflowStep step2 = new SimpleStep("step-2", "Step 2", "This is step 2");
        WorkflowStep step3 = new SimpleStep("step-3", "Step 3", "This is step 3");

        engine.startWorkflow(step1);
        engine.addStep(step2);
        engine.addParallelSteps(Arrays.asList(step3, new SimpleStep("step-4", "Step 4", "This is step 4")));
    }
}
```
This implementation provides a basic workflow engine with support for simple steps, conditional steps, and parallel steps. The `WorkflowEngine` class manages the current step and allows adding new steps or parallel steps to the workflow. The `Main` class demonstrates how to use the workflow engine to execute a simple workflow.