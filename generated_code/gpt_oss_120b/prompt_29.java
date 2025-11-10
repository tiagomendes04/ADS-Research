```java
// File: com/example/workflow/Context.java
package com.example.workflow;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Map<String, Object> data = new HashMap<>();

    public void set(String key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }
}
```

```java
// File: com/example/workflow/Step.java
package com.example.workflow;

public interface Step {
    String getName();
    void execute(Context context) throws Exception;
}
```

```java
// File: com/example/workflow/ProcessDefinition.java
package com.example.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessDefinition {
    private final String id;
    private final List<Step> steps = new ArrayList<>();

    public ProcessDefinition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ProcessDefinition addStep(Step step) {
        steps.add(step);
        return this;
    }

    public List<Step> getSteps() {
        return Collections.unmodifiableList(steps);
    }
}
```

```java
// File: com/example/workflow/ProcessInstance.java
package com.example.workflow;

import java.util.Iterator;

public class ProcessInstance {
    private final ProcessDefinition definition;
    private final Context context = new Context();
    private Iterator<Step> iterator;
    private boolean completed = false;

    public ProcessInstance(ProcessDefinition definition) {
        this.definition = definition;
        this.iterator = definition.getSteps().iterator();
    }

    public Context getContext() {
        return context;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void start() throws Exception {
        while (iterator.hasNext()) {
            Step step = iterator.next();
            step.execute(context);
        }
        completed = true;
    }
}
```

```java
// File: com/example/workflow/WorkflowEngine.java
package com.example.workflow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkflowEngine {
    private final Map<String, ProcessDefinition> definitions = new ConcurrentHashMap<>();

    public void register(ProcessDefinition definition) {
        definitions.put(definition.getId(), definition);
    }

    public ProcessInstance createInstance(String processId) {
        ProcessDefinition def = definitions.get(processId);
        if (def == null) {
            throw new IllegalArgumentException("No process definition with id: " + processId);
        }
        return new ProcessInstance(def);
    }
}
```

```java
// File: com/example/workflow/steps/PrintStep.java
package com.example.workflow.steps;

import com.example.workflow.Context;
import com.example.workflow.Step;

public class PrintStep implements Step {
    private final String name;
    private final String messageKey;

    public PrintStep(String name, String messageKey) {
        this.name = name;
        this.messageKey = messageKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(Context context) {
        Object msg = context.get(messageKey);
        System.out.println("[" + name + "] " + (msg != null ? msg : "null"));
    }
}
```

```java
// File: com/example/workflow/steps/DecisionStep.java
package com.example.workflow.steps;

import com.example.workflow.Context;
import com.example.workflow.Step;

public class DecisionStep implements Step {
    private final String name;
    private final String conditionKey;
    private final String trueStepName;
    private final String falseStepName;

    public DecisionStep(String name, String conditionKey, String trueStepName, String falseStepName) {
        this.name = name;
        this.conditionKey = conditionKey;
        this.trueStepName = trueStepName;
        this.falseStepName = falseStepName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(Context context) {
        Boolean condition = context.get(conditionKey);
        if (condition == null) {
            throw new IllegalStateException("Condition '" + conditionKey + "' not set in context");
        }
        context.set("nextStep", condition ? trueStepName : falseStepName);
    }
}
```

```java
// File: com/example/workflow/Main.java
package com.example.workflow;

import com.example.workflow.steps.DecisionStep;
import com.example.workflow.steps.PrintStep;

public class Main {
    public static void main(String[] args) throws Exception {
        // Define process
        ProcessDefinition orderProcess = new ProcessDefinition("order")
                .addStep(new PrintStep("Start", "orderId"))
                .addStep(new DecisionStep("CheckStock", "inStock", "Reserve", "OutOfStock"))
                .addStep(new PrintStep("Reserve", "orderId"))
                .addStep(new PrintStep("OutOfStock", "orderId"))
                .addStep(new PrintStep("End", "orderId"));

        // Register
        WorkflowEngine engine = new WorkflowEngine();
        engine.register(orderProcess);

        // Create instance
        ProcessInstance instance = engine.createInstance("order");
        Context ctx = instance.getContext();
        ctx.set("orderId", "ORD-12345");
        ctx.set("inStock", true); // change to false to follow other branch

        // Simple runner that respects the "nextStep" set by DecisionStep
        while (!instance.isCompleted()) {
            Step current = instance.definition.getSteps().stream()
                    .filter(s -> s.getName().equals(ctx.get("nextStep") != null ? ctx.get("nextStep") : s.getName()))
                    .findFirst()
                    .orElse(null);
            if (current == null) {
                // first iteration
                current = instance.definition.getSteps().get(0);
            }
            current.execute(ctx);
            // advance iterator manually if we used a custom nextStep
            if (ctx.contains("nextStep")) {
                // reset iterator to start from the chosen step
                instance.iterator = instance.definition.getSteps().stream()
                        .filter(s -> s.getName().equals(ctx.get("nextStep")))
                        .findFirst()
                        .map(s -> instance.definition.getSteps().listIterator(instance.definition.getSteps().indexOf(s)))
                        .orElse(instance.iterator);
                ctx.set("nextStep", null);
            } else {
                // normal progression handled by start()
                break;
            }
        }
    }
}
```