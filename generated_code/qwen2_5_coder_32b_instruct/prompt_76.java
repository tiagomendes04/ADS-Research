```java
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class Meeting {
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;

    public Meeting(String title, LocalTime startTime, LocalTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean conflictsWith(Meeting other) {
        return this.getStartTime().isBefore(other.getEndTime()) && other.getStartTime().isBefore(this.getEndTime());
    }
}

class Reminder {
    private String message;
    private LocalTime time;

    public Reminder(String message, LocalTime time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public LocalTime getTime() {
        return time;
    }
}

class Scheduler {
    private List<Meeting> meetings;
    private List<Reminder> reminders;

    public Scheduler() {
        this.meetings = new ArrayList<>();
        this.reminders = new ArrayList<>();
    }

    public void addMeeting(Meeting meeting) throws Exception {
        for (Meeting existing : meetings) {
            if (meeting.conflictsWith(existing)) {
                throw new Exception("Meeting conflicts with an existing one.");
            }
        }
        meetings.add(meeting);
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }
}

public class SchedulingAssistant {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        try {
            scheduler.addMeeting(new Meeting("Meeting 1", LocalTime.of(9, 0), LocalTime.of(10, 0)));
            scheduler.addMeeting(new Meeting("Meeting 2", LocalTime.of(10, 30), LocalTime.of(11, 30)));
            scheduler.addMeeting(new Meeting("Meeting 3", LocalTime.of(11, 0), LocalTime.of(12, 0))); // This should cause a conflict
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        scheduler.addReminder(new Reminder("Buy groceries", LocalTime.of(8, 0)));
        scheduler.addReminder(new Reminder("Submit report", LocalTime.of(10, 15)));

        System.out.println("Scheduled Meetings:");
        for (Meeting meeting : scheduler.getMeetings()) {
            System.out.println(meeting.getTitle() + " - " + meeting.getStartTime() + " to " + meeting.getEndTime());
        }

        System.out.println("\nReminders:");
        for (Reminder reminder : scheduler.getReminders()) {
            System.out.println(reminder.getMessage() + " at " + reminder.getTime());
        }
    }
}
```