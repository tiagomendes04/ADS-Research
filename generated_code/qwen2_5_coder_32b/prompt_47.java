```java
import java.util.*;

class Ticket {
    private int id;
    private String description;
    private int priority; // 1 - High, 2 - Medium, 3 - Low
    private String status; // Open, In Progress, Resolved, Closed

    public Ticket(int id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.status = "Open";
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                '}';
    }
}

class TicketSystem {
    private PriorityQueue<Ticket> ticketQueue;

    public TicketSystem() {
        ticketQueue = new PriorityQueue<>(Comparator.comparingInt(Ticket::getPriority));
    }

    public void addTicket(Ticket ticket) {
        ticketQueue.add(ticket);
    }

    public Ticket getNextTicket() {
        return ticketQueue.poll();
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(ticketQueue);
    }

    public void updateTicketStatus(int id, String status) {
        for (Ticket ticket : ticketQueue) {
            if (ticket.getId() == id) {
                ticket.setStatus(status);
                break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicketSystem ts = new TicketSystem();
        ts.addTicket(new Ticket(1, "Server down", 1));
        ts.addTicket(new Ticket(2, "Slow performance", 2));
        ts.addTicket(new Ticket(3, "Request for new feature", 3));

        System.out.println("Next ticket to process: " + ts.getNextTicket());
        ts.updateTicketStatus(2, "Resolved");
        System.out.println("All tickets: " + ts.getAllTickets());
    }
}
```