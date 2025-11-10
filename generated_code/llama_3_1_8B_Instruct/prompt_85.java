```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Event {
    String title;
    LocalDateTime start;
    LocalDateTime end;
    boolean recurring;
    String frequency;
    String reminder;

    public Event(String title, LocalDateTime start, LocalDateTime end, boolean recurring, String frequency, String reminder) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.recurring = recurring;
        this.frequency = frequency;
        this.reminder = reminder;
    }
}

class Calendar {
    private Map<LocalDate, List<Event>> events;

    public Calendar() {
        this.events = new HashMap<>();
    }

    public void addEvent(Event event) {
        LocalDate date = event.start.toLocalDate();
        if (!events.containsKey(date)) {
            events.put(date, new ArrayList<>());
        }
        events.get(date).add(event);
    }

    public void removeEvent(Event event) {
        LocalDate date = event.start.toLocalDate();
        if (events.containsKey(date)) {
            events.get(date).remove(event);
        }
    }

    public void displayEvents() {
        for (LocalDate date : events.keySet()) {
            System.out.println("Events on " + date.toString());
            for (Event event : events.get(date)) {
                System.out.println("Title: " + event.title + ", Start: " + event.start.toString() + ", End: " + event.end.toString());
            }
        }
    }

    public void sendReminders() {
        for (LocalDate date : events.keySet()) {
            for (Event event : events.get(date)) {
                if (event.reminder != null) {
                    if (LocalDateTime.now().isAfter(event.start) && LocalDateTime.now().isBefore(event.end)) {
                        System.out.println("Reminder: " + event.title + " at " + event.start.toString());
                    }
                }
            }
        }
    }

    public void displayRecurringEvents() {
        for (LocalDate date : events.keySet()) {
            for (Event event : events.get(date)) {
                if (event.recurring) {
                    System.out.println("Recurring Event: " + event.title + ", Frequency: " + event.frequency);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Calendar calendar = new Calendar();

        Event event1 = new Event("Meeting", LocalDateTime.of(2024, 7, 26, 10, 0), LocalDateTime.of(2024, 7, 26, 11, 0), true, "Daily", "10 minutes before start");
        Event event2 = new Event("Lunch", LocalDateTime.of(2024, 7, 26, 12, 0), LocalDateTime.of(2024, 7, 26, 13, 0), false, "", "5 minutes before start");
        Event event3 = new Event("Dinner", LocalDateTime.of(2024, 7, 26, 18, 0), LocalDateTime.of(2024, 7, 26, 19, 0), true, "Weekly", "15 minutes before start");

        calendar.addEvent(event1);
        calendar.addEvent(event2);
        calendar.addEvent(event3);

        calendar.displayEvents();
        calendar.sendReminders();
        calendar.displayRecurringEvents();
    }
}
```