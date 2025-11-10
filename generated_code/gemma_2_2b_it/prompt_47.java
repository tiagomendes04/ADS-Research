```java
import java.util.*;

public class TicketProcessor {

    private static Map<Integer, Ticket> tickets = new HashMap<>();
    private static int nextTicketId = 1;

    public static void addTicket(String customerName, String description, String priority) {
        int ticketId = nextTicketId++;
        Ticket ticket = new Ticket(ticketId, customerName, description, priority);
        tickets.put(ticketId, ticket);
    }

    public static void updateTicketStatus(int ticketId, String status) {
        if (tickets.containsKey(ticketId)) {
            tickets.get(ticketId).setStatus(status);
        } else {
            System.out.println("Ticket with ID: " + ticketId + " not found.");
        }
    }

    public static void printTickets() {
        for (Ticket ticket : tickets.values()) {
            System.out.println(ticket);
        }
    }

    public static void main(String[] args) {
        addTicket("John Doe", "Website order issue", "High");
        addTicket("Jane Smith", "Payment issue", "Low");
        addTicket("Peter Jones", "Software bug", "Medium");
        updateTicketStatus(1, "Pending");
        updateTicketStatus(2, "Resolved");
        printTickets();
    }

    static class Ticket {
        private final int id;
        private final String customerName;
        private final String description;
        private final String status;

        public Ticket(int id, String customerName, String description, String priority) {
            this.id = id;
            this.customerName = customerName;
            this.description = description;
            this.status = priority;
        }

        public int getId() {
            return id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getDescription() {
            return description;
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
                    ", customerName='" + customerName + '\'' +
                    ", description='" + description + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
```