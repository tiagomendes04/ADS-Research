```java
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/* Enums for ticket status and priority */
enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}

enum TicketPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}

/* Ticket entity */
class Ticket {
    private final long id;
    private String title;
    private String description;
    private TicketPriority priority;
    private TicketStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    Ticket(long id, String title, String description, TicketPriority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TicketStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /* Getters & Setters */
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TicketPriority getPriority() { return priority; }
    public TicketStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setTitle(String title) {
        this.title = title;
        touch();
    }

    public void setDescription(String description) {
        this.description = description;
        touch();
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
        touch();
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
        touch();
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
            "Ticket{id=%d, title='%s', priority=%s, status=%s, created=%s, updated=%s}",
            id, title, priority, status, createdAt, updatedAt
        );
    }
}

/* Manager handling ticket lifecycle */
class TicketManager {
    private final Map<Long, Ticket> tickets = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /* Create a new ticket */
    public Ticket createTicket(String title, String description, TicketPriority priority) {
        long id = idGenerator.getAndIncrement();
        Ticket ticket = new Ticket(id, title, description, priority);
        synchronized (tickets) {
            tickets.put(id, ticket);
        }
        return ticket;
    }

    /* Retrieve ticket by id */
    public Optional<Ticket> getTicket(long id) {
        synchronized (tickets) {
            return Optional.ofNullable(tickets.get(id));
        }
    }

    /* Update ticket status */
    public boolean updateStatus(long id, TicketStatus newStatus) {
        Optional<Ticket> opt = getTicket(id);
        if (opt.isPresent()) {
            opt.get().setStatus(newStatus);
            return true;
        }
        return false;
    }

    /* Update ticket priority */
    public boolean updatePriority(long id, TicketPriority newPriority) {
        Optional<Ticket> opt = getTicket(id);
        if (opt.isPresent()) {
            opt.get().setPriority(newPriority);
            return true;
        }
        return false;
    }

    /* Get all tickets */
    public List<Ticket> getAllTickets() {
        synchronized (tickets) {
            return new ArrayList<>(tickets.values());
        }
    }

    /* Filter tickets by status */
    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return getAllTickets()
                .stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    /* Filter tickets by priority */
    public List<Ticket> getTicketsByPriority(TicketPriority priority) {
        return getAllTickets()
                .stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }

    /* Close a ticket (sets status to CLOSED) */
    public boolean closeTicket(long id) {
        return updateStatus(id, TicketStatus.CLOSED);
    }

    /* Delete a ticket */
    public boolean deleteTicket(long id) {
        synchronized (tickets) {
            return tickets.remove(id) != null;
        }
    }
}

/* Demo */
public class TicketSystemDemo {
    public static void main(String[] args) {
        TicketManager manager = new TicketManager();

        Ticket t1 = manager.createTicket("Login issue", "User cannot log in.", TicketPriority.HIGH);
        Ticket t2 = manager.createTicket("Feature request", "Add dark mode.", TicketPriority.MEDIUM);
        Ticket t3 = manager.createTicket("Crash on start", "App crashes on startup.", TicketPriority.URGENT);

        manager.updateStatus(t1.getId(), TicketStatus.IN_PROGRESS);
        manager.updateStatus(t3.getId(), TicketStatus.RESOLVED);
        manager.updatePriority(t2.getId(), TicketPriority.HIGH);

        System.out.println("All tickets:");
        manager.getAllTickets().forEach(System.out::println);

        System.out.println("\nOpen tickets:");
        manager.getTicketsByStatus(TicketStatus.OPEN).forEach(System.out::println);

        System.out.println("\nUrgent tickets:");
        manager.getTicketsByPriority(TicketPriority.URGENT).forEach(System.out::println);

        manager.closeTicket(t3.getId());
        System.out.println("\nAfter closing ticket " + t3.getId() + ":");
        manager.getTicket(t3.getId()).ifPresent(System.out::println);
    }
}
```