```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Ticket {
    private int id;
    private String subject;
    private String description;
    private String priority;
    private String status;
    private LocalDate createdDate;

    public Ticket(int id, String subject, String description, String priority) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.priority = priority;
        this.status = "New";
        this.createdDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}

class CustomerSupportSystem {
    private List<Ticket> tickets;
    private int ticketId;

    public CustomerSupportSystem() {
        this.tickets = new ArrayList<>();
        this.ticketId = 1;
    }

    public void createTicket(String subject, String description, String priority) {
        Ticket ticket = new Ticket(ticketId, subject, description, priority);
        tickets.add(ticket);
        System.out.println("Ticket created successfully. ID: " + ticketId);
        ticketId++;
    }

    public void viewTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets available.");
            return;
        }
        System.out.println("Available Tickets:");
        for (Ticket ticket : tickets) {
            System.out.println("ID: " + ticket.getId() + ", Subject: " + ticket.getSubject() + ", Priority: " + ticket.getPriority() + ", Status: " + ticket.getStatus());
        }
    }

    public void viewTicket(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                System.out.println("Ticket Details:");
                System.out.println("ID: " + ticket.getId());
                System.out.println("Subject: " + ticket.getSubject());
                System.out.println("Description: " + ticket.getDescription());
                System.out.println("Priority: " + ticket.getPriority());
                System.out.println("Status: " + ticket.getStatus());
                System.out.println("Created Date: " + ticket.getCreatedDate());
                return;
            }
        }
        System.out.println("Ticket not found.");
    }

    public void updateTicketStatus(int id, String status) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                ticket.updateStatus(status);
                System.out.println("Ticket status updated successfully.");
                return;
            }
        }
        System.out.println("Ticket not found.");
    }

    public void deleteTicket(int id) {
        tickets.removeIf(ticket -> ticket.getId() == id);
        System.out.println("Ticket deleted successfully.");
    }

    public void sortTicketsByPriority() {
        tickets.sort(Comparator.comparing(Ticket::getPriority));
        System.out.println("Tickets sorted by priority:");
        for (Ticket ticket : tickets) {
            System.out.println("ID: " + ticket.getId() + ", Subject: " + ticket.getSubject() + ", Priority: " + ticket.getPriority() + ", Status: " + ticket.getStatus());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CustomerSupportSystem system = new CustomerSupportSystem();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Ticket");
            System.out.println("2. View Tickets");
            System.out.println("3. View Ticket");
            System.out.println("4. Update Ticket Status");
            System.out.println("5. Delete Ticket");
            System.out.println("6. Sort Tickets by Priority");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
            switch (option) {
                case 1:
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter priority (High, Medium, Low): ");
                    String priority = scanner.nextLine();
                    system.createTicket(subject, description, priority);
                    break;
                case 2:
                    system.viewTickets();
                    break;
                case 3:
                    System.out.print("Enter ticket ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    system.viewTicket(id);
                    break;
                case 4:
                    System.out.print("Enter ticket ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    System.out.print("Enter new status: ");
                    String status = scanner.nextLine();
                    system.updateTicketStatus(id, status);
                    break;
                case 5:
                    System.out.print("Enter ticket ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    system.deleteTicket(id);
                    break;
                case 6:
                    system.sortTicketsByPriority();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}
```