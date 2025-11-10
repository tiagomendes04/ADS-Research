```java
// WorkflowEngine.java

import java.util.*;

public class WorkflowEngine {
    private Map<String, WorkflowStep> steps;
    private Map<String, Map<String, Object>> processParameters;

    public WorkflowEngine() {
        steps = new HashMap<>();
        processParameters = new HashMap<>();
    }

    public void addStep(String id, WorkflowStep step) {
        steps.put(id, step);
    }

    public void addParameter(String id, String name, Object value) {
        processParameters.put(id, new HashMap<>());
        processParameters.get(id).put("name", name);
        processParameters.get(id).put("value", value);
    }

    public WorkflowStep getStep(String id) {
        return steps.get(id);
    }

    public void startWorkflow(String id) {
        WorkflowStep step = getStep(id);
        step.start();
    }

    public void finishWorkflow(String id) {
        WorkflowStep step = getStep(id);
        step.finish();
    }

    public void addParameter(String id, String name, Object value) {
        addParameter(id, name, value);
    }

    public void removeParameter(String id, String name) {
        processParameters.remove(id);
        removeParameter(id, name);
    }

    public void removeParameter(String id, String name) {
        processParameters.remove(id);
        removeStep(id);
    }

    public void removeStep(String id) {
        steps.remove(id);
        processParameters.remove(id);
        removeParameter(id, "name");
        removeParameter(id, "value");
    }

    public void removeParameter(String id, String name) {
        processParameters.remove(id);
        removeStep(id);
    }

    public void removeStep(String id) {
        processParameters.remove(id);
        removeStep(id);
    }
}

// WorkflowStep.java

import java.util.*;

class WorkflowStep {
    private Map<String, Object> variables;

    public WorkflowStep() {
        variables = new HashMap<>();
    }

    public void start() {
        System.out.println("Starting workflow with variables:");
        System.out.println(variables);
    }

    public void finish() {
        System.out.println("Finishing workflow with variables:");
        System.out.println(variables);
    }
}

// WorkflowStepExample.java

import java.util.*;

public class WorkflowStepExample {
    public static void main(String[] args) {
        WorkflowEngine engine = new WorkflowEngine();

        WorkflowStep step1 = new WorkflowStep();
        step1.start();
        step1.setVariable("variable1", "value1");

        WorkflowStep step2 = new WorkflowStep();
        step2.start();
        step2.setVariable("variable2", "value2");

        engine.addStep("step1", step1);
        engine.addStep("step2", step2);

        engine.addParameter("step1", "name", "John");
        engine.addParameter("step2", "name", "Jane");

        engine.startWorkflow("step1");
        engine.finishWorkflow("step1");

        engine.removeParameter("step1", "name");
        engine.removeStep("step1");
        engine.removeStep("step2");

        engine.removeStep("step1");
        engine.removeStep("step2");
    }
}
```