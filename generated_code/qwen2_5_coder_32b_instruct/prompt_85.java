```java
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Event {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isRecurring;
    private ChronoUnit recurrenceUnit;
    private int recurrenceInterval;

    public Event(String title, LocalDateTime startTime, LocalDateTime endTime, boolean isRecurring, ChronoUnit recurrenceUnit, int recurrenceInterval) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isRecurring = isRecurring;
        this.recurrenceUnit = recurrenceUnit;
        this.recurrenceInterval = recurrenceInterval;
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

    public ChronoUnit getRecurrenceUnit() {
        return recurrenceUnit;
    }

    public int getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public LocalDateTime getNextOccurrence(LocalDateTime fromTime) {
        if (isRecurring) {
            return fromTime.plus(recurrenceInterval, recurrenceUnit);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isRecurring=" + isRecurring +
                ", recurrenceUnit=" + recurrenceUnit +
                ", recurrenceInterval=" + recurrenceInterval +
                '}';
    }
}

class Reminder {
    private String message;
    private LocalDateTime reminderTime;

    public Reminder(String message, LocalDateTime reminderTime) {
        this.message = message;
        this.reminderTime = reminderTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "message='" + message + '\'' +
                ", reminderTime=" + reminderTime +
                '}';
    }
}

class CalendarApp {
    private Map<LocalDateTime, List<Event>> events;
    private Map<LocalDateTime, List<Reminder>> reminders;

    public CalendarApp() {
        this.events = new HashMap<>();
        this.reminders = new HashMap<>();
    }

    public void addEvent(Event event) {
        LocalDateTime currentTime = event.getStartTime();
        while (currentTime != null && (event.getEndTime() == null || currentTime.isBefore(event.getEndTime()))) {
            events.computeIfAbsent(currentTime, k -> new ArrayList<>()).add(event);
            currentTime = event.getNextOccurrence(currentTime);
        }
    }

    public void addReminder(Reminder reminder) {
        reminders.computeIfAbsent(reminder.getReminderTime(), k -> new ArrayList<>()).add(reminder);
    }

    public List<Event> getEventsOnDate(LocalDateTime date) {
        List<Event> result = new ArrayList<>();
        for (Map.Entry<LocalDateTime, List<Event>> entry : events.entrySet()) {
            if (entry.getKey().toLocalDate().equals(date.toLocalDate())) {
                result.addAll(entry.getValue());
            }
        }
        return result;
    }

    public List<Reminder> getRemindersOnDate(LocalDateTime date) {
        List<Reminder> result = new ArrayList<>();
        for (Map.Entry<LocalDateTime, List<Reminder>> entry : reminders.entrySet()) {
            if (entry.getKey().toLocalDate().equals(date.toLocalDate())) {
                result.addAll(entry.getValue());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CalendarApp calendarApp = new CalendarApp();

        Event event1 = new Event("Meeting", LocalDateTime.of(2023, 10, 1, 9, 0), LocalDateTime.of(2023, 10, 31, 9, 0), true, ChronoUnit.DAYS, 7);
        Event event2 = new Event("Conference", LocalDateTime.of(2023, 10, 15, 14, 0), LocalDateTime.of(2023, 10, 15, 18, 0), false, null, 0);
        calendarApp.addEvent(event1);
        calendarApp.addEvent(event2);

        Reminder reminder1 = new Reminder("Submit report", LocalDateTime.of(2023, 10, 15, 13, 0));
        Reminder reminder2 = new Reminder("Buy groceries", LocalDateTime.of(2023, 10, 1, 12, 0));
        calendarApp.addReminder(reminder1);
        calendarApp.addReminder(reminder2);

        System.out.println("Events on 2023-10-15: " + calendarApp.getEventsOnDate(LocalDateTime.of(2023, 10, 15, 0, 0)));
        System.out.println("Reminders on 2023-10-15: " + calendarApp.getRemindersOnDate(LocalDateTime.of(2023, 10, 15, 0, 0)));
    }
}
```