```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Event {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isRecurring;
    private String recurrencePattern; // e.g., "DAILY", "WEEKLY", "MONTHLY"

    public Event(String title, LocalDateTime startTime, LocalDateTime endTime, boolean isRecurring, String recurrencePattern) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isRecurring = isRecurring;
        this.recurrencePattern = recurrencePattern;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public boolean occursOn(LocalDate date) {
        if (!isRecurring) {
            return date.equals(startTime.toLocalDate());
        }
        // Simplified recurrence check for demonstration
        switch (recurrencePattern) {
            case "DAILY":
                return true;
            case "WEEKLY":
                return date.getDayOfWeek().equals(startTime.getDayOfWeek());
            case "MONTHLY":
                return date.getDayOfMonth() == startTime.getDayOfMonth();
        }
        return false;
    }
}

class Reminder {
    private Event event;
    private LocalTime reminderTime;

    public Reminder(Event event, LocalTime reminderTime) {
        this.event = event;
        this.reminderTime = reminderTime;
    }

    public Event getEvent() {
        return event;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public boolean shouldRemindAt(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(event.getStartTime().toLocalDate()) &&
               dateTime.toLocalTime().equals(reminderTime);
    }
}

class CalendarApp {
    private Map<LocalDate, List<Event>> eventsMap;
    private List<Reminder> reminders;

    public CalendarApp() {
        eventsMap = new HashMap<>();
        reminders = new ArrayList<>();
    }

    public void addEvent(Event event) {
        LocalDate date = event.getStartTime().toLocalDate();
        eventsMap.computeIfAbsent(date, k -> new ArrayList<>()).add(event);
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    public List<Event> getEventsForDate(LocalDate date) {
        return eventsMap.getOrDefault(date, new ArrayList<>());
    }

    public List<Reminder> getRemindersForDateTime(LocalDateTime dateTime) {
        List<Reminder> relevantReminders = new ArrayList<>();
        for (Reminder reminder : reminders) {
            if (reminder.shouldRemindAt(dateTime)) {
                relevantReminders.add(reminder);
            }
        }
        return relevantReminders;
    }

    public void printEventsAndRemindersForDate(LocalDate date) {
        List<Event> events = getEventsForDate(date);
        System.out.println("Events on " + date + ":");
        for (Event event : events) {
            System.out.println(" - " + event.getTitle() + ": " + event.getStartTime().toLocalTime() + " to " + event.getEndTime().toLocalTime());
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        for (LocalDateTime time = startOfDay; !time.isAfter(endOfDay); time = time.plusMinutes(1)) {
            List<Reminder> remindersAtTime = getRemindersForDateTime(time);
            for (Reminder reminder : remindersAtTime) {
                System.out.println("Reminder at " + time.toLocalTime() + " for: " + reminder.getEvent().getTitle());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CalendarApp calendarApp = new CalendarApp();

        Event event1 = new Event("Meeting", LocalDateTime.of(2023, 10, 5, 9, 0), LocalDateTime.of(2023, 10, 5, 10, 0), true, "DAILY");
        Event event2 = new Event("Conference", LocalDateTime.of(2023, 10, 5, 14, 0), LocalDateTime.of(2023, 10, 5, 16, 0), false, null);

        Reminder reminder1 = new Reminder(event1, LocalTime.of(8, 30));
        Reminder reminder2 = new Reminder(event2, LocalTime.of(13, 30));

        calendarApp.addEvent(event1);
        calendarApp.addEvent(event2