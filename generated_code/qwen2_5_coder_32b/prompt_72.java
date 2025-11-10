```java
import java.util.ArrayList;
import java.util.List;

interface NotificationService {
    void sendNotification(String message, String recipient);
}

class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("Sending email to " + recipient + ": " + message);
    }
}

class SMSNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}

class Reviewer {
    private String name;
    private String contactInfo;
    private NotificationService notificationService;

    public Reviewer(String name, String contactInfo, NotificationService notificationService) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.notificationService = notificationService;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void approve(WorkflowItem item) {
        item.setStatus(WorkflowStatus.APPROVED);
        item.notifyReviewers();
    }

    public void reject(WorkflowItem item, String reason) {
        item.setStatus(WorkflowStatus.REJECTED);
        item.setRejectionReason(reason);
        item.notifyReviewers();
    }

    public void notify(String message) {
        notificationService.sendNotification(message, contactInfo);
    }
}

enum WorkflowStatus {
    PENDING, APPROVED, REJECTED
}

class WorkflowItem {
    private String description;
    private WorkflowStatus status;
    private List<Reviewer> reviewers;
    private String rejectionReason;

    public WorkflowItem(String description) {
        this.description = description;
        this.status = WorkflowStatus.PENDING;
        this.reviewers = new ArrayList<>();
    }

    public void addReviewer(Reviewer reviewer) {
        reviewers.add(reviewer);
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    public WorkflowStatus getStatus() {
        return status;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void notifyReviewers() {
        String message = "Workflow item '" + description + "' has been " + status;
        if (status == WorkflowStatus.REJECTED) {
            message += " with reason: " + rejectionReason;
        }
        for (Reviewer reviewer : reviewers) {
            reviewer.notify(message);
        }
    }
}

public class WorkflowApprovalSystem {
    public static void main(String[] args) {
        NotificationService emailService = new EmailNotificationService();
        NotificationService smsService = new SMSNotificationService();

        Reviewer reviewer1 = new Reviewer("Alice", "alice@example.com", emailService);
        Reviewer reviewer2 = new Reviewer("Bob", "+1234567890", smsService);

        WorkflowItem item = new WorkflowItem("New Project Proposal");
        item.addReviewer(reviewer1);
        item.addReviewer(reviewer2);

        System.out.println("Current Status: " + item.getStatus());
        reviewer1.approve(item);
        System.out.println("Current Status: " + item.getStatus());
    }
}
```