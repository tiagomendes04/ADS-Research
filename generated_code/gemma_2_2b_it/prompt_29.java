```java
import java.util.HashMap;
import java.util.Map;

public class WorkflowEngine {

    private Map<String, Process> processes;

    public WorkflowEngine() {
        processes = new HashMap<>();
    }

    public void registerProcess(Process process) {
        processes.put(process.getName(), process);
    }

    public void executeProcess(String processName) {
        Process process = processes.get(processName);
        if (process != null) {
            process.execute();
        } else {
            System.err.println("Process not found: " + processName);
        }
    }

    public static class Process {
        private String name;
        private Map<String, Action> actions;

        public Process(String name) {
            this.name = name;
            this.actions = new HashMap<>();
        }

        public void addAction(String actionName, Action action) {
            actions.put(actionName, action);
        }

        public void execute() {
            for (Map.Entry<String, Action> entry : actions.entrySet()) {
                entry.getValue().execute();
            }
        }
    }

    public static class Action {
        public void execute() {
            System.out.println("Action executed: " + this.getClass().getName());
        }
    }

    public static void main(String[] args) {
        WorkflowEngine engine = new WorkflowEngine();

        engine.registerProcess(new Process("Process1"));
        engine.registerProcess(new Process("Process2"));

        engine.executeProcess("Process1");
        engine.executeProcess("Process2");
    }
}
```