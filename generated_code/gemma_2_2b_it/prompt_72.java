```java
import java.util.ArrayList;
import java.util.List;

public class WorkflowApproval {

    public static class Task {
        private String id;
        private String title;
        private String status;
        private String reviewerId;
        private String statusNotification;

        public Task(String id, String title, String status, String reviewerId, String statusNotification) {
            this.id = id;
            this.title = title;
            this.status = status;
            this.reviewerId = reviewerId;
            this.statusNotification = statusNotification;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }

        public String getReviewerId() {
            return reviewerId;
        }

        public String getStatusNotification() {
            return statusNotification;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Reviewer {
        private String id;
        private String name;

        public Reviewer(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class Notification {
        private String id;
        private String message;
        private String receiverId;

        public Notification(String id, String message, String receiverId) {
            this.id = id;
            this.message = message;
            this.receiverId = receiverId;
        }

        public String getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        public String getReceiverId() {
            return receiverId;
        }
    }

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("1", "Document 1", "Pending", "reviewer1", "Approved"));
        tasks.add(new Task("2", "Document 2", "Pending", "reviewer2", "Pending"));

        List<Reviewer> reviewers = new ArrayList<>();
        reviewers.add(new Reviewer("r1", "John Doe"));
        reviewers.add(new Reviewer("r2", "Jane Smith"));

        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("n1", "Document 1 is ready for approval", "reviewer1"));
        notifications.add(new Notification("n2", "Document 2 is ready for review", "reviewer2"));

        // ... (more code for workflow logic)
    }
}
```