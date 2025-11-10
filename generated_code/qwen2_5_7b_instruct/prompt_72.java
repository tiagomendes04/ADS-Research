```java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkflowApprovalSystem {
    private List<Reviewer> reviewers = new ArrayList<>();
    private List<ApprovalStep> steps = new ArrayList<>();

    public void addReviewer(Reviewer reviewer) {
        reviewers.add(reviewer);
    }

    public void addApprovalStep(ApprovalStep step) {
        steps.add(step);
    }

    public void processWorkflow() {
        for (ApprovalStep step : steps) {
            Optional<Reviewer> nextReviewer = reviewers.stream()
                    .filter(reviewer -> reviewer.getRole().equals(step.getRequiredRole()))
                    .findFirst();
            if (nextReviewer.isPresent()) {
                System.out.println("Notifying " + nextReviewer.get().getName() + " for approval step: " + step.getName());
                nextReviewer.get().notifyForApproval(step);
            } else {
                System.out.println("No reviewer found for role: " + step.getRequiredRole());
            }
        }
    }

    public static void main(String[] args) {
        WorkflowApprovalSystem system = new WorkflowApprovalSystem();
        system.addReviewer(new Reviewer("Alice", "Manager"));
        system.addReviewer(new Reviewer("Bob", "Supervisor"));
        system.addReviewer(new Reviewer("Charlie", "Supervisor"));

        system.addApprovalStep(new ApprovalStep("Step1", "Manager"));
        system.addApprovalStep(new ApprovalStep("Step2", "Supervisor"));

        system.processWorkflow();
    }
}

class Reviewer {
    private String name;
    private String role;

    public Reviewer(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void notifyForApproval(ApprovalStep step) {
        System.out.println(name + " has been notified for approval step: " + step.getName());
    }
}

class ApprovalStep {
    private String name;
    private String requiredRole;

    public ApprovalStep(String name, String requiredRole) {
        this.name = name;
        this.requiredRole = requiredRole;
    }

    public String getName() {
        return name;
    }

    public String getRequiredRole() {
        return requiredRole;
    }
}
```