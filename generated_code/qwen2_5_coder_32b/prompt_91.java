```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Habit {
    private String name;
    private LocalDate startDate;
    private int streak;
    private List<Goal> goals;

    public Habit(String name) {
        this.name = name;
        this.startDate = LocalDate.now();
        this.streak = 0;
        this.goals = new ArrayList<>();
    }

    public void logCompletion() {
        streak++;
        System.out.println("Habit " + name + " completed. Streak: " + streak);
        checkGoals();
    }

    public void resetStreak() {
        streak = 0;
        System.out.println("Habit " + name + " streak reset.");
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
        System.out.println("Goal added to " + name + ": " + goal.getDescription());
    }

    private void checkGoals() {
        for (Goal goal : goals) {
            if (streak >= goal.getStreakTarget()) {
                System.out.println("Goal achieved for " + name + ": " + goal.getDescription());
                goal.setAchieved(true);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getStreak() {
        return streak;
    }
}

class Goal {
    private String description;
    private int streakTarget;
    private boolean achieved;

    public Goal(String description, int streakTarget) {
        this.description = description;
        this.streakTarget = streakTarget;
        this.achieved = false;
    }

    public String getDescription() {
        return description;
    }

    public int getStreakTarget() {
        return streakTarget;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}

class NotificationService {
    public void sendNotification(String message) {
        System.out.println("Notification sent: " + message);
    }
}

public class HabitTracker {
    private List<Habit> habits;
    private NotificationService notificationService;

    public HabitTracker() {
        this.habits = new ArrayList<>();
        this.notificationService = new NotificationService();
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
        System.out.println("Habit added: " + habit.getName());
    }

    public void logHabitCompletion(String habitName) {
        for (Habit habit : habits) {
            if (habit.getName().equals(habitName)) {
                habit.logCompletion();
                notificationService.sendNotification("Good job on completing " + habitName + "!");
                break;
            }
        }
    }

    public void resetHabitStreak(String habitName) {
        for (Habit habit : habits) {
            if (habit.getName().equals(habitName)) {
                habit.resetStreak();
                notificationService.sendNotification("Your streak for " + habitName + " has been reset.");
                break;
            }
        }
    }

    public void addGoalToHabit(String habitName, Goal goal) {
        for (Habit habit : habits) {
            if (habit.getName().equals(habitName)) {
                habit.addGoal(goal);
                break;
            }
        }
    }

    public static void main(String[] args) {
        HabitTracker tracker = new HabitTracker();
        Habit readBook = new Habit("Read a book");
        Habit exercise = new Habit("Exercise");

        tracker.addHabit(readBook);
        tracker.addHabit(exercise);

        Goal read5Books = new Goal("Read 5 books", 5);
        Goal exercise30Days = new Goal("Exercise for 30 consecutive days", 30);

        tracker.addGoalToHabit("Read a book", read5Books);
        tracker.addGoalToHabit("Exercise", exercise30Days);

        tracker.logHabitCompletion("Read a book");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");
        tracker.logHabitCompletion("Exercise");

        tracker.resetHabitStreak("Exercise");
    }
}