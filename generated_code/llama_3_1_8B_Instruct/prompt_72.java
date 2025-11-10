```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class Reviewer {
    private String name;
    private boolean approved;
    private boolean reviewed;

    public Reviewer(String name) {
        this.name = name;
        this.approved = false;
        this.reviewed = false;
    }

    public String getName() {
        return name;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isReviewed() {
        return reviewed;
    }
}

class Task {
    private String name;
    private List<Reviewer> reviewers;
    private boolean approved;
    private boolean completed;
    private ReentrantLock lock;

    public Task(String name) {
        this.name = name;
        this.reviewers = new ArrayList<>();
        this.approved = false;
        this.completed = false;
        this.lock = new ReentrantLock();
    }

    public String getName() {
        return name;
    }

    public void addReviewer(Reviewer reviewer) {
        reviewers.add(reviewer);
    }

    public void approveTask() {
        lock.lock();
        try {
            if (reviewers.stream().allMatch(Reviewer::isApproved)) {
                approved = true;
                System.out.println(name + " has been approved");
            } else {
                System.out.println(name + " is not approved yet");
            }
        } finally {
            lock.unlock();
        }
    }

    public void completeTask() {
        lock.lock();
        try {
            if (approved) {
                completed = true;
                System.out.println(name + " has been completed");
            } else {
                System.out.println(name + " is not completed yet");
            }
        } finally {
            lock.unlock();
        }
    }
}

class NotificationService {
    private List<String> notifications;

    public NotificationService() {
        notifications = new ArrayList<>();
    }

    public void sendNotification(String message) {
        notifications.add(message);
        System.out.println(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }
}

public class WorkflowApprovalSystem {
    public static void main(String[] args) {
        NotificationService notificationService = new NotificationService();
        Task task = new Task("Example Task");

        Reviewer reviewer1 = new Reviewer("Reviewer 1");
        Reviewer reviewer2 = new Reviewer("Reviewer 2");
        Reviewer reviewer3 = new Reviewer("Reviewer 3");

        task.addReviewer(reviewer1);
        task.addReviewer(reviewer2);
        task.addReviewer(reviewer3);

        reviewer1.setApproved(true);
        reviewer2.setApproved(true);
        reviewer3.setApproved(true);

        task.approveTask();
        task.completeTask();

        notificationService.sendNotification("Task completed successfully");
        System.out.println("Notifications: " + notificationService.getNotifications());
    }
}
```