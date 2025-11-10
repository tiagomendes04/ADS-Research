```java
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

class Event {
    String name;
    LocalDate date;
    LocalTime time;
    String type;
    boolean recurring;

    public Event(String name, LocalDate date, LocalTime time, String type, boolean recurring) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.type = type;
        this.recurring = recurring;
    }
}

class CalendarApp {
    private Map<String, Event> events;
    private Map<String, Event> reminders;
    private Map<String, Event> recurringSchedules;

    public CalendarApp() {
        events = new HashMap<>();
        reminders = new HashMap<>();
        recurringSchedules = new HashMap<>();
    }

    public void addEvent(Event event) {
        events.put(event.name, event);
    }

    public void addReminder(Event event) {
        reminders.put(event.name, event);
    }

    public void addRecurringSchedule(String name, Event event, LocalDate date) {
        recurringSchedules.put(name, event);
        event.recurring = true;
        event.date = date;
    }

    public void printEvents() {
        for (Event event : events.values()) {
            System.out.println(event.name);
            System.out.println(event.date);
            System.out.println(event.time);
            System.out.println(event.type);
            System.out.println("Recurring: " + (event.recurring ? "Yes" : "No"));
            System.out.println();
        }
    }

    public void printReminders() {
        for (Event event : reminders.values()) {
            System.out.println(event.name);
            System.out.println(event.date);
            System.out.println(event.time);
            System.out.println(event.type);
            System.out.println("Recurring: " + (event.recurring ? "Yes" : "No"));
            System.out.println();
        }
    }

    public void printRecurringSchedules() {
        for (Map.Entry<String, Event> entry : recurringSchedules.entrySet()) {
            Event event = entry.getValue();
            System.out.println(entry.getKey());
            System.out.println("Date: " + event.date);
            System.out.println("Time: " + event.time);
            System.out.println("Type: " + event.type);
            System.out.println("Recurring: " + (event.recurring ? "Yes" : "No"));
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CalendarApp calendarApp = new CalendarApp();
        calendarApp.addEvent(new Event("Meeting", LocalDate.of(2024, 3, 16), LocalTime.of(10, 0), "Work", true));
        calendarApp.addEvent(new Event("Lunch", LocalDate.of(2024, 3, 16), LocalTime.of(12, 0), "Work", true));
        calendarApp.addEvent(new Event("Movie", LocalDate.of(2024, 3, 17), LocalTime.of(19, 0), "Free", false));
        calendarApp.addEvent(new Event("Party", LocalDate.of(2024, 3, 18), LocalTime.of(20, 0), "Work", true));
        calendarApp.addRecurringSchedule("Weekly Meeting", calendarApp.addEvent(new Event("Meeting", LocalDate.of(2024, 3, 16), LocalTime.of(10, 0), "Work", true)), LocalDate.of(2024, 3, 16));
        calendarApp.addReminder(new Event("Lunch", LocalDate.of(2024, 3, 16), LocalTime.of(12, 0), "Work", false));
        calendarApp.addReminder(new Event("Movie", LocalDate.of(2024, 3, 17), LocalTime.of(19, 0), "Free", false));
        calendarApp.printEvents();
        calendarApp.printReminders();
        calendarApp.printRecurringSchedules();
    }
}
```