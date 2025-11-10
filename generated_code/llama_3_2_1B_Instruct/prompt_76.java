```java
import java.util.*;

public class SchedulingAssistant {
    public static class Meeting {
        String name;
        String date;
        String time;
        List<String> attendees;

        public Meeting(String name, String date, String time, List<String> attendees) {
            this.name = name;
            this.date = date;
            this.time = time;
            this.attendees = attendees;
        }
    }

    public static class Conflict {
        String room;
        String time;
        String attendees;

        public Conflict(String room, String time, String attendees) {
            this.room = room;
            this.time = time;
            this.attendees = attendees;
        }
    }

    public static class Reminder {
        String id;
        String time;
        String description;

        public Reminder(String id, String time, String description) {
            this.id = id;
            this.time = time;
            this.description = description;
        }
    }

    public static class Sched {
        List<Meeting> meetings = new ArrayList<>();
        List<Conflict> conflicts = new ArrayList<>();
        List<Reminder> reminders = new ArrayList<>();

        public void addMeeting(Meeting meeting) {
            meetings.add(meeting);
        }

        public void removeMeeting(Meeting meeting) {
            meetings.remove(meeting);
        }

        public void addConflict(Conflict conflict) {
            conflicts.add(conflict);
        }

        public void removeConflict(Conflict conflict) {
            conflicts.remove(conflict);
        }

        public void addReminder(Reminder reminder) {
            reminders.add(reminder);
        }

        public void removeReminder(Reminder reminder) {
            reminders.remove(reminder);
        }
    }

    public static class Scheduler {
        Sched schedule = new Sched();

        public void scheduleMeeting(Meeting meeting) {
            schedule.addMeeting(meeting);
        }

        public void removeMeeting(Meeting meeting) {
            schedule.removeMeeting(meeting);
        }

        public void addConflict(Conflict conflict) {
            schedule.addConflict(conflict);
        }

        public void removeConflict(Conflict conflict) {
            schedule.removeConflict(conflict);
        }

        public void addReminder(Reminder reminder) {
            schedule.addReminder(reminder);
        }

        public void removeReminder(Reminder reminder) {
            schedule.removeReminder(reminder);
        }
    }

    public static void main(String[] args) {
        Sched scheduler = new Scheduler();

        // Create meetings
        Meeting meeting1 = new Meeting("Meeting 1", "2023-12-15", "10:00 AM", Arrays.asList("Alice", "Bob"));
        Meeting meeting2 = new Meeting("Meeting 2", "2023-12-16", "11:00 AM", Arrays.asList("Charlie", "David"));
        Meeting meeting3 = new Meeting("Meeting 3", "2023-12-17", "1:00 PM", Arrays.asList("Eve", "Frank"));

        // Add meetings to scheduler
        scheduler.scheduleMeeting(meeting1);
        scheduler.scheduleMeeting(meeting2);
        scheduler.scheduleMeeting(meeting3);

        // Create conflicts
        Conflict conflict1 = new Conflict("Room 1", "10:00 AM", Arrays.asList("Alice", "Bob"));
        Conflict conflict2 = new Conflict("Room 2", "11:00 AM", Arrays.asList("Charlie", "David"));
        Conflict conflict3 = new Conflict("Room 3", "1:00 PM", Arrays.asList("Eve", "Frank"));

        // Add conflicts to scheduler
        scheduler.addConflict(conflict1);
        scheduler.addConflict(conflict2);
        scheduler.addConflict(conflict3);

        // Create reminders
        Reminder reminder1 = new Reminder("R1", "10:00 AM", "Meeting 1");
        Reminder reminder2 = new Reminder("R2", "11:00 AM", "Meeting 2");
        Reminder reminder3 = new Reminder("R3", "1:00 PM", "Meeting 3");

        // Add reminders to scheduler
        scheduler.addReminder(reminder1);
        scheduler.addReminder(reminder2);
        scheduler.addReminder(reminder3);

        // Print scheduler
        System.out.println("Meetings:");
        for (Meeting meeting : schedule.meetings) {
            System.out.println("Name: " + meeting.name + ", Date: " + meeting.date + ", Time: " + meeting.time);
            for (String attendee : meeting.attendees) {
                System.out.println("Attendee: " + attendee);
            }
        }

        System.out.println("\nConflicts:");
        for (Conflict conflict : schedule.conflicts) {
            System.out.println("Room: " + conflict.room + ", Time: " + conflict.time + ", Attendees: " + conflict.attendees);
        }

        System.out.println("\nReminders:");
        for (Reminder reminder : schedule.reminders) {
            System.out.println("ID: " + reminder.id + ", Time: " + reminder.time + ", Description: " + reminder.description);
        }
    }
}
```