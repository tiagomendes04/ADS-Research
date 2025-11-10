```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SchedulingAssistant {

    private Map<String, Meeting> meetings;
    private Map<String, Reminder> reminders;

    public SchedulingAssistant() {
        meetings = new HashMap<>();
        reminders = new HashMap<>();
    }

    public void addMeeting(String title, String startTime, String endTime) {
        meetings.put(title, new Meeting(title, startTime, endTime));
    }

    public void addReminder(String reminderTitle, String time) {
        reminders.put(reminderTitle, new Reminder(reminderTitle, time));
    }

    public void scheduleMeeting(String meetingTitle) {
        Meeting meeting = meetings.get(meetingTitle);
        if (meeting != null) {
            System.out.println("Meeting scheduled: " + meeting.getTitle());
        } else {
            System.out.println("Meeting not found: " + meetingTitle);
        }
    }

    public void showReminders() {
        Set<String> keySet = reminders.keySet();
        for (String key : keySet) {
            Reminder reminder = reminders.get(key);
            System.out.println(reminder);
        }
    }

    public void detectConflicts() {
        for (Meeting meeting : meetings.values()) {
            String title = meeting.getTitle();
            for (Meeting otherMeeting : meetings.values()) {
                if (title.equals(otherMeeting.getTitle())) {
                    System.out.println("Conflict detected for " + title);
                }
            }
        }
    }

    public static void main(String[] args) {
        SchedulingAssistant assistant = new SchedulingAssistant();
        assistant.addMeeting("Meeting 1", "10:00 AM", "11:00 AM");
        assistant.addReminder("Reminder 1", "10:00 AM");
        assistant.scheduleMeeting("Meeting 1");
        assistant.showReminders();
        assistant.detectConflicts();
    }
}

class Meeting {
    private String title;
    private String startTime;
    private String endTime;

    public Meeting(String title, String startTime, String endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}

class Reminder {
    private String title;
    private String time;

    public Reminder(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
}
```