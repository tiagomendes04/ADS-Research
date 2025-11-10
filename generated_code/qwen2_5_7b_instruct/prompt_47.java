```java
import java.util.*;

class Ticket {
    private int id;
    private String customer;
    private String issue;
    private int priority;
    private String status;

    public Ticket(int id, String customer, String issue, int priority) {
        this.id = id;
        this.customer = customer;
        this.issue = issue;
        this.priority = priority;
        this.status = "NEW";
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getIssue() {
        return issue;
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
                ", customer='" + customer + '\'' +
                ", issue='" + issue + '\'' +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                '}';
    }
}

class TicketSystem {
    private List<Ticket> tickets;

    public TicketSystem() {
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void processTickets() {
        tickets.sort(Comparator.comparing(Ticket::getPriority).reversed());
        for (Ticket ticket : tickets) {
            if (ticket.getStatus().equals("NEW")) {
                ticket.setStatus("IN_PROGRESS");
                System.out.println("Processing ticket " + ticket.getId() + " for " + ticket.getCustomer());
            }
        }
    }

    public void updateTicketStatus(int id, String status) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                ticket.setStatus(status);
                System.out.println("Updated ticket " + id + " status to " + status);
                return;
            }
        }
        System.out.println("Ticket not found");
    }

    public void printTickets() {
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TicketSystem system = new TicketSystem();
        system.addTicket(new Ticket(1, "Alice", "Payment issue", 2));
        system.addTicket(new Ticket(2, "Bob", "Account access", 1));
        system.addTicket(new Ticket(3, "Charlie", "Product refund", 3));

        system.printTickets();
        system.processTickets();
        system.printTickets();
        system.updateTicketStatus(2, "RESOLVED");
        system.printTickets();
    }
}
```