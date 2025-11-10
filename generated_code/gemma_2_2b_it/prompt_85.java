```java
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class CalendarApp {
    static Map<String, Event> events = new HashMap<>();
    static List<Calendar> reminders = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Add event");
            System.out.println("2. View events");
            System.out.println("3. Add reminder");
            System.out.println("4. View reminders");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addEvent(scanner);
                    break;
                case 2:
                    viewEvents();
                    break;
                case 3:
                    addReminder(scanner);
                    break;
                case 4:
                    viewReminders();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addEvent(Scanner scanner) {
        System.out.println("Enter event details:");
        System.out.println("Title:");
        String title = scanner.nextLine();
        System.out.println("Description:");
        String description = scanner.nextLine();
        System.out.println("Date (YYYY-MM-DD):");
        String dateStr = scanner.nextLine();
        Calendar date = parseDate(dateStr);
        
        events.put(title, new Event(title, description, date));
        System.out.println("Event added successfully.");
    }
    
    private static void viewEvents() {
        System.out.println("Events:");
        for (Event event : events.values()) {
            System.out.println(event);
        }
    }
    
    private static void addReminder(Scanner scanner) {
        System.out.println("Enter reminder details:");
        System.out.println("Title:");
        String title = scanner.nextLine();
        System.out.println("Description:");
        String description = scanner.nextLine();
        System.out.println("Date (YYYY-MM-DD HH:MM:SS):");
        String timeStr = scanner.nextLine();
        Calendar time = parseTime(timeStr);
        
        reminders.add(time);
        System.out.println("Reminder added successfully.");
    }
    
    private static void viewReminders() {
        System.out.println("Reminders:");
        for (Calendar reminder : reminders) {
            System.out.println(reminder);
        }
    }
    
    private static Calendar parseDate(String dateStr) {
        // ... Implementation for parsing date
        return null;
    }
    
    private static Calendar parseTime(String timeStr) {
        // ... Implementation for parsing time
        return null;
    }
}
```

 
 
```java
class Event {
    String title;
    String description;
    Calendar date;

    public Event(String title, String description, Calendar date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
``` 

 
 
```java
class Reminder {
    String title;
    String description;
    Calendar time;

    public Reminder(String title, String description, Calendar time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
```