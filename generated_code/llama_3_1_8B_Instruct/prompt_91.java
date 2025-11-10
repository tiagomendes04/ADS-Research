```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HabitTracker {
    private List<Habit> habits;
    private List<Goal> goals;
    private List<Notification> notifications;

    public HabitTracker() {
        habits = new ArrayList<>();
        goals = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    public void addHabit(String name, LocalDateTime date) {
        habits.add(new Habit(name, date));
    }

    public void addGoal(String name, int days) {
        goals.add(new Goal(name, days));
    }

    public void addNotification(String message, LocalDateTime date) {
        notifications.add(new Notification(message, date));
    }

    public void markHabitAsCompleted(int habitIndex) {
        Habit habit = habits.get(habitIndex);
        habit.markAsCompleted();
    }

    public void viewHabitTracker() {
        System.out.println("Habit Tracker:");
        for (int i = 0; i < habits.size(); i++) {
            Habit habit = habits.get(i);
            System.out.println("Habit " + (i + 1) + ": " + habit.getName());
            System.out.println("Days completed: " + habit.getDaysCompleted());
            System.out.println("Streak: " + habit.getStreak());
            System.out.println();
        }

        System.out.println("Goals:");
        for (int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            System.out.println("Goal " + (i + 1) + ": " + goal.getName());
            System.out.println("Days: " + goal.getDays());
            System.out.println();
        }

        System.out.println("Notifications:");
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            System.out.println("Notification " + (i + 1) + ": " + notification.getMessage());
            System.out.println("Date: " + notification.getDate());
            System.out.println();
        }
    }
}
```

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Habit {
    private String name;
    private LocalDateTime date;
    private int daysCompleted;
    private int streak;

    public Habit(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
        this.daysCompleted = 0;
        this.streak = 0;
    }

    public void markAsCompleted() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayDateTime = LocalDateTime.of(today, LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
        long days = ChronoUnit.DAYS.between(date, todayDateTime);
        daysCompleted += 1;
        streak += 1;
    }

    public String getName() {
        return name;
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    public int getStreak() {
        return streak;
    }
}
```

```java
public class Goal {
    private String name;
    private int days;

    public Goal(String name, int days) {
        this.name = name;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public int getDays() {
        return days;
    }
}
```

```java
public class Notification {
    private String message;
    private LocalDateTime date;

    public Notification(String message, LocalDateTime date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
```

```java
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        HabitTracker habitTracker = new HabitTracker();

        habitTracker.addHabit("Exercise", LocalDateTime.of(2024, 7, 15, 8, 0));
        habitTracker.addHabit("Meditation", LocalDateTime.of(2024, 7, 15, 7, 0));
        habitTracker.addHabit("Reading", LocalDateTime.of(2024, 7, 15, 9, 0));

        habitTracker.addGoal("Exercise for 30 days", 30);
        habitTracker.addGoal("Meditation for 20 days", 20);
        habitTracker.addGoal("Reading for 40 days", 40);

        habitTracker.addNotification("Remember to exercise today!", LocalDateTime.of(2024, 7, 15, 8, 0));
        habitTracker.addNotification("Don't forget to meditate today!", LocalDateTime.of(2024, 7, 15, 7, 0));
        habitTracker.addNotification("Read something new today!", LocalDateTime.of(2024, 7, 15, 9, 0));

        habitTracker.markHabitAsCompleted(0);
        habitTracker.markHabitAsCompleted(1);
        habitTracker.markHabitAsCompleted(2);

        habitTracker.viewHabitTracker();
    }
}
```