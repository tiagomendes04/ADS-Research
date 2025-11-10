```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Meeting {
    private String title;
    private Date startDate;
    private Date endDate;

    public Meeting(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

class SchedulingAssistant {
    private List<Meeting> meetings;

    public SchedulingAssistant() {
        this.meetings = new ArrayList<>();
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    public boolean hasConflict(Date date) {
        for (Meeting m : meetings) {
            if (date.after(m.getStartDate()) && date.before(m.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void printMeetings() {
        for (Meeting m : meetings) {
            System.out.println(m.getTitle() + " - " + m.getStartDate() + " to " + m.getEndDate());
        }
    }

    public void addReminder(Date date, String message) {
        System.out.println("Reminder: " + message + " at " + date);
    }
}

public class Main {
    public static void main(String[] args) {
        SchedulingAssistant assistant = new SchedulingAssistant();

        assistant.addMeeting(new Meeting("Meeting 1", new Date(2023, 1, 15, 10, 0), new Date(2023, 1, 15, 11, 0)));
        assistant.addMeeting(new Meeting("Meeting 2", new Date(2023, 1, 15, 11, 30), new Date(2023, 1, 15, 12, 30)));
        assistant.addMeeting(new Meeting("Meeting 3", new Date(2023, 1, 15, 13, 0), new Date(2023, 1, 15, 14, 0)));

        Date checkDate = new Date(2023, 1, 15, 11, 0);
        if (assistant.hasConflict(checkDate)) {
            assistant.addReminder(checkDate, "You have a meeting at this time.");
        }

        assistant.printMeetings();
    }
}
```