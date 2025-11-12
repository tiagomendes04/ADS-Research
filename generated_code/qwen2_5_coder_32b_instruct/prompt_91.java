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

    public String getName() {
        return name;
    }

    public void logCompletion() {
        if (LocalDate.now().isEqual(startDate.plusDays(streak))) {
            streak++;
        } else if (LocalDate.now().isEqual(startDate.plusDays(streak + 1))) {
            streak++;
            startDate = LocalDate.now();
        } else {
            streak = 1;
            startDate = LocalDate.now();
        }
        checkGoals();
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    private void checkGoals() {
        for (Goal goal : goals) {
            if (streak >= goal.getStreakTarget() && !goal.isAchieved()) {
                goal.setAchieved(true);
                System.out.println("Goal achieved: " + goal.getDescription());
            }
        }
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

public class HabitTracker {
    private List<Habit> habits;

    public HabitTracker() {
        this.habits = new ArrayList<>();
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void logHabitCompletion(String habitName) {
        for (Habit habit : habits) {
            if (habit.getName().equals(habitName)) {
                habit.logCompletion();
                break;
            }
        }
    }

    public static void main(String[] args) {
        HabitTracker tracker = new HabitTracker();
        Habit readBook = new Habit("Read a book");
        readBook.addGoal(new Goal("Read daily for 7 days", 7));
        readBook.addGoal(new Goal("Read daily for 30 days", 30));
        tracker.addHabit(readBook);

        Habit exercise = new Habit("Exercise");
        exercise.addGoal(new Goal("Exercise daily for 5 days", 5));
        exercise.addGoal(new Goal("Exercise daily for 21 days", 21));
        tracker.addHabit(exercise);

        // Simulate completing habits
        for (int i = 0; i < 8; i++) {
            readBook.logCompletion();
        }

        for (int i = 0; i < 6; i++) {
            exercise.logCompletion();
        }
    }
}
```