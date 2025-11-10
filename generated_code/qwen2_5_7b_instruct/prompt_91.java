```java
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Habit {
    private String name;
    private List<Date> dates;

    public Habit(String name) {
        this.name = name;
        this.dates = new ArrayList<>();
    }

    public void markHabit() {
        dates.add(new Date());
    }

    public int getStreak() {
        if (dates.isEmpty()) return 0;
        Date lastDate = dates.get(dates.size() - 1);
        Date secondLastDate = dates.get(dates.size() - 2);
        long diffInDays = (lastDate.getTime() - secondLastDate.getTime()) / (24 * 60 * 60 * 1000);
        return diffInDays == 1 ? 1 : 0;
    }
}

class HabitTracker {
    private Map<String, Habit> habits;

    public HabitTracker() {
        this.habits = new HashMap<>();
    }

    public void addHabit(String habitName) {
        if (!habits.containsKey(habitName)) {
            habits.put(habitName, new Habit(habitName));
        }
    }

    public void markHabit(String habitName) {
        Habit habit = habits.get(habitName);
        if (habit != null) {
            habit.markHabit();
            System.out.println("Marked " + habitName + " as completed.");
        } else {
            System.out.println("Habit not found.");
        }
    }

    public void showStreaks() {
        for (Map.Entry<String, Habit> entry : habits.entrySet()) {
            String habitName = entry.getKey();
            Habit habit = entry.getValue();
            int streak = habit.getStreak();
            System.out.println(habitName + " Streak: " + streak);
        }
    }

    public void setGoal(String habitName, int goal) {
        Habit habit = habits.get(habitName);
        if (habit != null) {
            habit.dates.add(new Date());
            if (habit.dates.size() >= goal) {
                System.out.println("Goal for " + habitName + " reached!");
            } else {
                System.out.println("Goal for " + habitName + " not reached yet.");
            }
        } else {
            System.out.println("Habit not found.");
        }
    }

    public void showNotifications() {
        for (Map.Entry<String, Habit> entry : habits.entrySet()) {
            String habitName = entry.getKey();
            Habit habit = entry.getValue();
            int streak = habit.getStreak();
            if (streak > 0) {
                System.out.println("Notification: Keep up the good work on " + habitName + " with a streak of " + streak + " days!");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        HabitTracker tracker = new HabitTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Habit");
            System.out.println("2. Mark Habit");
            System.out.println("3. Show Streaks");
            System.out.println("4. Set Goal");
            System.out.println("5. Show Notifications");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter habit name: ");
                    String habitName = scanner.nextLine();
                    tracker.addHabit(habitName);
                    break;
                case 2:
                    System.out.print("Enter habit name: ");
                    habitName = scanner.nextLine();
                    tracker.markHabit(habitName);
                    break;
                case 3:
                    tracker.showStreaks();
                    break;
                case 4:
                    System.out.print("Enter habit name: ");
                    habitName = scanner.nextLine();
                    System.out.print("Enter goal: ");
                    int goal = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    tracker.setGoal(habitName, goal);
                    break;
                case 5:
                    tracker.showNotifications();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
```