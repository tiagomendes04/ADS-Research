```java
import java.util.*;

class CustomerSupportTicket {
    int id;
    String description;
    int priority;
    String status;
    String assignedTo;
    String tags;

    public CustomerSupportTicket(int id, String description, int priority, String status, String assignedTo, String tags) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.assignedTo = assignedTo;
        this.tags = tags;
    }
}

class TicketTracker {
    Map<Integer, CustomerSupportTicket> tickets;

    public TicketTracker() {
        tickets = new HashMap<>();
    }

    public void addTicket(int id, String description, int priority, String status, String assignedTo, String tags) {
        CustomerSupportTicket ticket = new CustomerSupportTicket(id, description, priority, status, assignedTo, tags);
        tickets.put(id, ticket);
    }

    public void updateTicket(int id, String description, int priority, String status, String assignedTo, String tags) {
        if (tickets.containsKey(id)) {
            CustomerSupportTicket ticket = tickets.get(id);
            ticket.description = description;
            ticket.priority = priority;
            ticket.status = status;
            ticket.assignedTo = assignedTo;
            ticket.tags = tags;
        } else {
            System.out.println("Ticket not found");
        }
    }

    public void printTickets() {
        for (CustomerSupportTicket ticket : tickets.values()) {
            System.out.println("Ticket ID: " + ticket.id);
            System.out.println("Description: " + ticket.description);
            System.out.println("Priority: " + ticket.priority);
            System.out.println("Status: " + ticket.status);
            System.out.println("Assigned To: " + ticket.assignedTo);
            System.out.println("Tags: " + ticket.tags);
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicketTracker tracker = new TicketTracker();
        tracker.addTicket(1, "Issue with product", 1, "Resolved", "John Doe", "frontend, issue");
        tracker.addTicket(2, "Error with payment", 2, "Open", "Jane Smith", "security");
        tracker.updateTicket(1, "Issue with product", 1, "Resolved", "John Doe", "frontend, issue");
        tracker.printTickets();
    }
}
```