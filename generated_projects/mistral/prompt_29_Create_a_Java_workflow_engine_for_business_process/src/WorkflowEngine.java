import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WorkflowEngine {
    private final Map<String, Workflow> workflows = new HashMap<>();
    private final Map<String, Step> steps = new HashMap<>();

    public void registerWorkflow(String name, Workflow workflow) {
        workflows.put(name, workflow);
    }

    public void registerStep(String name, Step step) {
        steps.put(name, step);
    }

    public void executeWorkflow(String workflowName, Map<String, Object> context) {
        Workflow workflow = workflows.get(workflowName);
        if (workflow == null) {
            throw new IllegalArgumentException("Workflow not found: " + workflowName);
        }

        workflow.execute(context);
    }

    public static class Workflow {
        private final List<Step> steps = new ArrayList<>();
        private String name;

        public Workflow(String name) {
            this.name = name;
        }

        public void addStep(Step step) {
            steps.add(step);
        }

        public void execute(Map<String, Object> context) {
            for (Step step : steps) {
                step.execute(context);
            }
        }
    }

    public interface Step {
        void execute(Map<String, Object> context);
    }

    public static class ConditionalStep implements Step {
        private final Consumer<Map<String, Object>> action;
        private final String condition;

        public ConditionalStep(String condition, Consumer<Map<String, Object>> action) {
            this.condition = condition;
            this.action = action;
        }

        @Override
        public void execute(Map<String, Object> context) {
            if (evaluateCondition(context)) {
                action.accept(context);
            }
        }

        private boolean evaluateCondition(Map<String, Object> context) {
            // Simple condition evaluation - can be enhanced
            return condition != null && context.containsKey(condition);
        }
    }

    public static class ParallelStep implements Step {
        private final List<Step> subSteps = new ArrayList<>();

        public void addSubStep(Step step) {
            subSteps.add(step);
        }

        @Override
        public void execute(Map<String, Object> context) {
            subSteps.parallelStream().forEach(step -> step.execute(context));
        }
    }
}