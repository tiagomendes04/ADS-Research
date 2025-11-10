```java
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class SchedulingAssistant {

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        // Sample meetings
        scheduler.addMeeting(new Meeting("Team Sync",
                LocalDateTime.of(2025, 11, 12, 10, 0),
                Duration.ofHours(1)));

        scheduler.addMeeting(new Meeting("Client Call",
                LocalDateTime.of(2025, 11, 12, 11, 0),
                Duration.ofMinutes(45)));

        // Sample reminder
        scheduler.addReminder(new Reminder("Prepare slides",
                LocalDateTime.of(2025, 11, 12, 9, 30)));

        // Conflict detection demo
        Meeting newMeeting = new Meeting("Project Review",
                LocalDateTime.of(2025, 11, 12, 10, 30),
                Duration.ofHours(1));

        List<Meeting> conflicts = scheduler.findConflicts(newMeeting);
        if (!conflicts.isEmpty()) {
            System.out.println("Conflicts detected with the following meetings:");
            conflicts.forEach(m -> System.out.println("- " + m));
        } else {
            scheduler.addMeeting(newMeeting);
            System.out.println("Meeting added successfully.");
        }

        // List all scheduled items
        System.out.println("\nAll Meetings:");
        scheduler.getMeetings().forEach(System.out::println);

        System.out.println("\nAll Reminders:");
        scheduler.getReminders().forEach(System.out::println);
    }
}

/* ---------- Core Entities ---------- */

class Meeting {
    private final String title;
    private final LocalDateTime start;
    private final Duration duration;

    public Meeting(String title, LocalDateTime start, Duration duration) {
        this.title = Objects.requireNonNull(title);
        this.start = Objects.requireNonNull(start);
        this.duration = Objects.requireNonNull(duration);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return start.plus(duration);
    }

    public String getTitle() {
        return title;
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean overlaps(Meeting other) {
        return !(this.getEnd().isBefore(other.getStart()) || this.getStart().isAfter(other.getEnd()));
    }

    @Override
    public String toString() {
        return String.format("%s [%s - %s] (%s)",
                title,
                start,
                getEnd(),
                formatDuration(duration));
    }

    private static String formatDuration(Duration d) {
        long hours = d.toHours();
        long minutes = d.toMinutesPart();
        if (hours > 0) {
            return minutes > 0 ? hours + "h " + minutes + "m" : hours + "h";
        }
        return minutes + "m";
    }
}

class Reminder {
    private final String note;
    private final LocalDateTime time;

    public Reminder(String note, LocalDateTime time) {
        this.note = Objects.requireNonNull(note);
        this.time = Objects.requireNonNull(time);
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("%s at %s", note, time);
    }
}

/* ---------- Scheduler Service ---------- */

class Scheduler {
    private final List<Meeting> meetings = new ArrayList<>();
    private final List<Reminder> reminders = new ArrayList<>();

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
        meetings.sort(Comparator.comparing(Meeting::getStart));
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        reminders.sort(Comparator.comparing(Reminder::getTime));
    }

    public List<Meeting> getMeetings() {
        return Collections.unmodifiableList(meetings);
    }

    public List<Reminder> getReminders() {
        return Collections.unmodifiableList(reminders);
    }

    public List<Meeting> findConflicts(Meeting newMeeting) {
        return meetings.stream()
                .filter(m -> m.overlaps(newMeeting))
                .collect(Collectors.toList());
    }

    public Optional<Reminder> nextReminder() {
        LocalDateTime now = LocalDateTime.now();
        return reminders.stream()
                .filter(r -> !r.getTime().isBefore(now))
                .findFirst();
    }

    public Optional<Meeting> nextMeeting() {
        LocalDateTime now = LocalDateTime.now();
        return meetings.stream()
                .filter(m -> !m.getStart().isBefore(now))
                .findFirst();
    }
}
```