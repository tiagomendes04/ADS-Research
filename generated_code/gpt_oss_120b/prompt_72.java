```java
package com.example.workflow;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// ----- Core Domain -----
enum ApprovalStatus {
    PENDING, APPROVED, REJECTED
}

class ApprovalRequest {
    private static final AtomicInteger ID_GEN = new AtomicInteger(1);
    private final int id;
    private final String title;
    private final String description;
    private final List<Reviewer> reviewers;
    private final Map<Reviewer, ApprovalStatus> decisions = new ConcurrentHashMap<>();
    private volatile ApprovalStatus finalStatus = ApprovalStatus.PENDING;

    public ApprovalRequest(String title, String description, List<Reviewer> reviewers) {
        this.id = ID_GEN.getAndIncrement();
        this.title = title;
        this.description = description;
        this.reviewers = Collections.unmodifiableList(new ArrayList<>(reviewers));
        reviewers.forEach(r -> decisions.put(r, ApprovalStatus.PENDING));
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<Reviewer> getReviewers() { return reviewers; }
    public ApprovalStatus getFinalStatus() { return finalStatus; }

    public synchronized void recordDecision(Reviewer reviewer, ApprovalStatus status) {
        if (!reviewers.contains(reviewer) || finalStatus != ApprovalStatus.PENDING) {
            return;
        }
        decisions.put(reviewer, status);
        evaluate();
    }

    private void evaluate() {
        boolean allApproved = decisions.values().stream()
                .allMatch(s -> s == ApprovalStatus.APPROVED);
        boolean anyRejected = decisions.values().stream()
                .anyMatch(s -> s == ApprovalStatus.REJECTED);

        if (anyRejected) {
            finalStatus = ApprovalStatus.REJECTED;
        } else if (allApproved && decisions.keySet().size() == reviewers.size()) {
            finalStatus = ApprovalStatus.APPROVED;
        }
    }

    public Map<Reviewer, ApprovalStatus> getDecisions() {
        return Collections.unmodifiableMap(decisions);
    }
}

// ----- Reviewer -----
class Reviewer {
    private final String name;
    private final NotificationService notificationService;

    public Reviewer(String name, NotificationService notificationService) {
        this.name = name;
        this.notificationService = notificationService;
    }

    public String getName() { return name; }

    public void receiveNotification(String message) {
        notificationService.send(name, message);
    }

    public void approve(ApprovalRequest request) {
        request.recordDecision(this, ApprovalStatus.APPROVED);
        receiveNotification("You approved request #" + request.getId());
    }

    public void reject(ApprovalRequest request) {
        request.recordDecision(this, ApprovalStatus.REJECTED);
        receiveNotification("You rejected request #" + request.getId());
    }
}

// ----- Notification Service -----
interface NotificationService {
    void send(String recipient, String message);
}

class ConsoleNotificationService implements NotificationService {
    @Override
    public void send(String recipient, String message) {
        System.out.println("[Notification] To: " + recipient + " | Message: " + message);
    }
}

// ----- Workflow Engine -----
class WorkflowEngine {
    private final NotificationService notificationService;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<Integer, ApprovalRequest> activeRequests = new ConcurrentHashMap<>();

    public WorkflowEngine(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public ApprovalRequest startRequest(String title, String description, List<String> reviewerNames) {
        List<Reviewer> reviewers = new ArrayList<>();
        for (String name : reviewerNames) {
            reviewers.add(new Reviewer(name, notificationService));
        }
        ApprovalRequest request = new ApprovalRequest(title, description, reviewers);
        activeRequests.put(request.getId(), request);
        notifyReviewers(request);
        monitorCompletion(request);
        return request;
    }

    private void notifyReviewers(ApprovalRequest request) {
        String msg = "New approval request #" + request.getId() + ": " + request.getTitle();
        request.getReviewers().forEach(r -> r.receiveNotification(msg));
    }

    private void monitorCompletion(ApprovalRequest request) {
        executor.submit(() -> {
            while (request.getFinalStatus() == ApprovalStatus.PENDING) {
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
            String resultMsg = "Request #" + request.getId() + " completed with status: " + request.getFinalStatus();
            request.getReviewers().forEach(r -> r.receiveNotification(resultMsg));
            activeRequests.remove(request.getId());
        });
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}

// ----- Demo Application -----
public class WorkflowDemo {
    public static void main(String[] args) throws InterruptedException {
        NotificationService notifService = new ConsoleNotificationService();
        WorkflowEngine engine = new WorkflowEngine(notifService);

        List<String> reviewers = Arrays.asList("Alice", "Bob", "Carol");
        ApprovalRequest request = engine.startRequest(
                "Purchase Laptop",
                "Request to purchase a new development laptop.",
                reviewers
        );

        // Simulate reviewers acting asynchronously
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        scheduler.schedule(() -> request.getReviewers().get(0).approve(request), 1, TimeUnit.SECONDS);
        scheduler.schedule(() -> request.getReviewers().get(1).approve(request), 2, TimeUnit.SECONDS);
        scheduler.schedule(() -> request.getReviewers().get(2).reject(request), 3, TimeUnit.SECONDS);

        // Wait enough time for the workflow to finish
        Thread.sleep(5000);
        engine.shutdown();
        scheduler.shutdownNow();
    }
}
```