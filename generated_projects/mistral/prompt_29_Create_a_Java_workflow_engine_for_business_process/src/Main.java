public class Main {
    public static void main(String[] args) {
        WorkflowEngine engine = new WorkflowEngine();

        // Create steps
        WorkflowEngine.Step step1 = context -> {
            System.out.println("Executing Step 1");
            context.put("step1Completed", true);
        };

        WorkflowEngine.Step step2 = context -> {
            System.out.println("Executing Step 2");
            context.put("step2Completed", true);
        };

        WorkflowEngine.Step conditionalStep = new WorkflowEngine.ConditionalStep(
            "step1Completed",
            context -> System.out.println("Conditional step executed")
        );

        WorkflowEngine.ParallelStep parallelStep = new WorkflowEngine.ParallelStep();
        parallelStep.addSubStep(step1);
        parallelStep.addSubStep(step2);

        // Create workflow
        WorkflowEngine.Workflow workflow = new WorkflowEngine.Workflow("SampleWorkflow");
        workflow.addStep(parallelStep);
        workflow.addStep(conditionalStep);

        // Register and execute
        engine.registerWorkflow("sample", workflow);
        engine.executeWorkflow("sample", new HashMap<>());
    }
}