```java
import java.util.*;

public class WorkflowApprovalSystem {

    public static class Task {
        String name;
        List<Reviewer> reviewers;
        boolean approved;

        public Task(String name) {
            this.name = name;
            this.reviewers = new ArrayList<>();
            this.approved = false;
        }

        public void addReviewer(Reviewer reviewer) {
            this.reviewers.add(reviewer);
        }

        public void approve() {
            this.approved = true;
        }
    }

    public static class Reviewer {
        String name;
        String email;

        public Reviewer(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public void review(Task task) {
            System.out.println(task.name + " reviewed by " + this.name);
            if (task.approved) {
                System.out.println(this.name + " approved " + task.name);
            } else {
                System.out.println(this.name + " did not approve " + task.name);
            }
        }
    }

    public static class Workflow {
        String name;
        List<Task> tasks;
        List<Reviewer> reviewers;
        Map<Reviewer, List<Task>> reviewerTasks;

        public Workflow(String name) {
            this.name = name;
            this.tasks = new ArrayList<>();
            this.reviewers = new ArrayList<>();
            this.reviewerTasks = new HashMap<>();
        }

        public void addTask(Task task) {
            this.tasks.add(task);
        }

        public void addReviewer(Reviewer reviewer) {
            this.reviewers.add(reviewer);
            this.reviewerTasks.put(reviewer, new ArrayList<>());
        }

        public void assignTask(Reviewer reviewer, Task task) {
            this.reviewerTasks.get(reviewer).add(task);
            reviewer.review(task);
        }

        public void notifyReviewers() {
            for (Reviewer reviewer : this.reviewers) {
                System.out.println(reviewer.name + " notified to review tasks");
                for (Task task : this.reviewerTasks.get(reviewer)) {
                    System.out.println("Task " + task.name + " assigned to " + reviewer.name);
                }
            }
        }
    }

    public static void main(String[] args) {
        Workflow workflow = new Workflow("Development");

        Reviewer reviewer1 = new Reviewer("John Doe", "john.doe@example.com");
        Reviewer reviewer2 = new Reviewer("Jane Doe", "jane.doe@example.com");

        workflow.addReviewer(reviewer1);
        workflow.addReviewer(reviewer2);

        Task task1 = new Task("Feature 1");
        Task task2 = new Task("Feature 2");
        Task task3 = new Task("Feature 3");

        workflow.addTask(task1);
        workflow.addTask(task2);
        workflow.addTask(task3);

        task1.addReviewer(reviewer1);
        task1.addReviewer(reviewer2);
        task2.addReviewer(reviewer1);
        task3.addReviewer(reviewer2);

        workflow.assignTask(reviewer1, task1);
        workflow.assignTask(reviewer2, task2);
        workflow.assignTask(reviewer1, task3);

        workflow.notifyReviewers();

        task1.approve();
        task2.approve();
        task3.approve();
    }
}
```