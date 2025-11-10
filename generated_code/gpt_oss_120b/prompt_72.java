```java
// File: src/main/java/com/example/workflow/WorkflowEngine.java
package com.example.workflow;

import java.util.*;
import java.util.concurrent.*;

public class WorkflowEngine {
    private final Map<UUID, ApprovalRequest> requests = new ConcurrentHashMap<>();
    private final NotificationService notificationService;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public WorkflowEngine(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public UUID submitRequest(String title, String description, List<Reviewer> reviewers) {
        ApprovalRequest request = new ApprovalRequest(title, description, reviewers);
        requests.put(request.getId(), request);
        notifyReviewers(request);
        return request.getId();
    }

    public void approve(UUID requestId, String reviewerId) {
        ApprovalRequest request = requests.get(requestId);
        if (request == null) return;
        request.approve(reviewerId);
        evaluate(request);
    }

    public void reject(UUID requestId, String reviewerId, String reason) {
        ApprovalRequest request = requests.get(requestId);
        if (request == null) return;
        request.reject(reviewerId, reason);
        evaluate(request);
    }

    private void evaluate(ApprovalRequest request) {
        if (request.isCompleted()) {
            notificationService.notifyCompletion(request);
            requests.remove(request.getId());
        } else {
            // still pending reviewers
            notifyPendingReviewers(request);
        }
    }

    private void notifyReviewers(ApprovalRequest request) {
        for (Reviewer r : request.getPendingReviewers()) {
            executor.submit(() -> notificationService.notifyReviewer(request, r));
        }
    }

    private void notifyPendingReviewers(ApprovalRequest request) {
        for (Reviewer r : request.getPendingReviewers()) {
            executor.submit(() -> notificationService.notifyReminder(request, r));
        }
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}
```

```java
// File: src/main/java/com/example/workflow/ApprovalRequest.java
package com.example.workflow;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ApprovalRequest {
    private final UUID id = UUID.randomUUID();
    private final String title;
    private final String description;
    private final Map<String, Reviewer> reviewers = new ConcurrentHashMap<>();
    private final Set<String> approved = Collections.synchronizedSet(new HashSet<>());
    private final Set<String> rejected = Collections.synchronizedSet(new HashSet<>());
    private volatile boolean completed = false;
    private volatile boolean approvedOverall = false;
    private volatile String rejectionReason = null;

    public ApprovalRequest(String title, String description, List<Reviewer> reviewerList) {
        this.title = title;
        this.description = description;
        for (Reviewer r : reviewerList) {
            reviewers.put(r.getId(), r);
        }
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public Collection<Reviewer> getPendingReviewers() {
        List<Reviewer> pending = new ArrayList<>();
        for (Reviewer r : reviewers.values()) {
            if (!approved.contains(r.getId()) && !rejected.contains(r.getId())) {
                pending.add(r);
            }
        }
        return pending;
    }

    public synchronized void approve(String reviewerId) {
        if (completed || !reviewers.containsKey(reviewerId)) return;
        approved.add(reviewerId);
        checkCompletion();
    }

    public synchronized void reject(String reviewerId, String reason) {
        if (completed || !reviewers.containsKey(reviewerId)) return;
        rejected.add(reviewerId);
        rejectionReason = reason;
        checkCompletion();
    }

    private void checkCompletion() {
        if (!rejected.isEmpty()) {
            completed = true;
            approvedOverall = false;
        } else if (approved.size() == reviewers.size()) {
            completed = true;
            approvedOverall = true;
        }
    }

    public boolean isCompleted() { return completed; }
    public boolean isApprovedOverall() { return approvedOverall; }
    public String getRejectionReason() { return rejectionReason; }
    public Set<String> getApprovedReviewerIds() { return Collections.unmodifiableSet(approved); }
    public Set<String> getRejectedReviewerIds() { return Collections.unmodifiableSet(rejected); }
}
```

```java
// File: src/main/java/com/example/workflow/Reviewer.java
package com.example.workflow;

public class Reviewer {
    private final String id;
    private final String name;
    private final String email;

    public Reviewer(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
```

```java
// File: src/main/java/com/example/workflow/NotificationService.java
package com.example.workflow;

public interface NotificationService {
    void notifyReviewer(ApprovalRequest request, Reviewer reviewer);
    void notifyReminder(ApprovalRequest request, Reviewer reviewer);
    void notifyCompletion(ApprovalRequest request);
}
```

```java
// File: src/main/java/com/example/workflow/EmailNotificationService.java
package com.example.workflow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailNotificationService implements NotificationService {

    @Override
    public void notifyReviewer(ApprovalRequest request, Reviewer reviewer) {
        String subject = "New Approval Request: " + request.getTitle();
        String body = String.format(
                "Hello %s,%n%nYou have a new approval request:%nTitle: %s%nDescription: %s%nRequest ID: %s%nPlease review at your earliest convenience.%n%nSent at: %s",
                reviewer.getName(),
                request.getTitle(),
                request.getDescription(),
                request.getId(),
                now()
        );
        sendEmail(reviewer.getEmail(), subject, body);
    }

    @Override
    public void notifyReminder(ApprovalRequest request, Reviewer reviewer) {
        String subject = "Reminder: Pending Approval Request " + request.getTitle();
        String body = String.format(
                "Hello %s,%n%nThis is a reminder to review the pending request:%nTitle: %s%nRequest ID: %s%nPlease take action.%n%nSent at: %s",
                reviewer.getName(),
                request.getTitle(),
                request.getId(),
                now()
        );
        sendEmail(reviewer.getEmail(), subject, body);
    }

    @Override
    public void notifyCompletion(ApprovalRequest request) {
        String subject = "Approval Request Completed: " + request.getTitle();
        String status = request.isApprovedOverall() ? "APPROVED" : "REJECTED";
        String details = request.isApprovedOverall()
                ? "All reviewers approved."
                : "Rejected by: " + String.join(", ", request.getRejectedReviewerIds())
                  + ". Reason: " + request.getRejectionReason();

        String body = String.format(
                "Hello,%n%nThe approval request has been completed.%nTitle: %s%nRequest ID: %s%nStatus: %s%nDetails: %s%n%nSent at: %s",
                request.getTitle(),
                request.getId(),
                status,
                details,
                now()
        );
        // In a real system you'd notify the requestor; here we just log.
        System.out.println("[EMAIL] " + subject + "\n" + body);
    }

    private void sendEmail(String to, String subject, String body) {
        // Placeholder for actual email sending logic.
        System.out.println("[EMAIL] To: " + to);
        System.out.println("[EMAIL] Subject: " + subject);
        System.out.println("[EMAIL] Body:\n" + body);
        System.out.println("----------------------------------------------------");
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
```

```java
// File: src/main/java/com/example/workflow/Main.java
package com.example.workflow;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        NotificationService notificationService = new EmailNotificationService();
        WorkflowEngine engine = new WorkflowEngine(notificationService);

        Reviewer alice = new Reviewer("alice", "Alice Johnson", "alice@example.com");
        Reviewer bob   = new Reviewer("bob",   "Bob Smith",   "bob@example.com");
        Reviewer carol = new Reviewer("carol", "Carol Lee",   "carol@example.com");

        List<Reviewer> reviewers = Arrays.asList(alice, bob, carol);
        UUID requestId = engine.submitRequest(
                "Purchase New Laptops",
                "Request to purchase 10 new development laptops.",
                reviewers
        );

        // Simulate actions
        Thread.sleep(500);
        engine.approve(requestId, "alice");
        Thread.sleep(500);