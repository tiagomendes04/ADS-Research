import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.function.Consumer;
 import java.util.function.Predicate;
 
 public class WorkflowEngine {
 
  private List<WorkflowStep> steps;
  private Map<String, Object> context;
 
  public WorkflowEngine() {
  this.steps = new ArrayList<>();
  this.context = new HashMap<>();
  }
 
  public void addStep(WorkflowStep step) {
  this.steps.add(step);
  }
 
  public void setContext(String key, Object value) {
  this.context.put(key, value);
  }
 
  public Object getContext(String key) {
  return this.context.get(key);
  }
 
  public void run() {
  for (WorkflowStep step : steps) {
  if (step.getCondition() == null || step.getCondition().test(context)) {
  step.getAction().accept(context);
  }
  }
  }
 
  public static class WorkflowStep {
  private Consumer<Map<String, Object>> action;
  private Predicate<Map<String, Object>> condition;
 
  public WorkflowStep(Consumer<Map<String, Object>> action) {
  this(action, null);
  }
 
  public WorkflowStep(Consumer<Map<String, Object>> action, Predicate<Map<String, Object>> condition) {
  this.action = action;
  this.condition = condition;
  }
 
  public Consumer<Map<String, Object>> getAction() {
  return action;
  }
 
  public Predicate<Map<String, Object>> getCondition() {
  return condition;
  }
  }
 
  public static void main(String[] args) {
  WorkflowEngine engine = new WorkflowEngine();
 
  // Example Steps
  engine.addStep(new WorkflowStep(
  context -> {
  System.out.println("Step 1: Initializing...");
  context.put("initialized", true);
  }
  ));
 
  engine.addStep(new WorkflowStep(
  context -> {
  if ((Boolean) context.get("initialized")) {
  System.out.println("Step 2: Processing data...");
  context.put("data", "Processed Data");
  } else {
  System.out.println("Step 2: Skipping processing due to initialization failure.");
  }
  },
  context -> (Boolean) context.getOrDefault("initialized", false)
  ));
 
  engine.addStep(new WorkflowStep(
  context -> {
  System.out.println("Step 3: Saving data: " + context.get("data"));
  context.put("saved", true);
  }
  ));
 
  engine.addStep(new WorkflowStep(
  context -> {
  if ((Boolean) context.get("saved")) {
  System.out.println("Step 4: Workflow completed successfully!");
  } else {
  System.out.println("Step 4: Workflow completed, but saving failed.");
  }
  },
  context -> (Boolean) context.getOrDefault("saved", false)
  ));
 
  // Set initial context data (optional)
  engine.setContext("initialValue", 100);
 
  // Run the workflow
  engine.run();
  }
 }