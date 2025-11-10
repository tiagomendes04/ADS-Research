```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HabitTracker {

    private static Map<String, Integer> habits = new HashMap<>();
    private static Map<String, Integer> goals = new HashMap<>();
    private static boolean notificationsEnabled = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Add habit");
            System.out.println("2. View habits");
            System.out.println("3. Add goal");
            System.out.println("4. View goals");
            System.out.println("5. Track habit");
            System.out.println("6. Check streak");
            System.out.println("7. Set notifications");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addHabit(scanner);
                    break;
                case 2:
                    viewHabits();
                    break;
                case 3:
                    addGoal(scanner);
                    break;
                case 4:
                    viewGoals();
                    break;
                case 5:
                    trackHabit(scanner);
                    break;
                case 6:
                    checkStreak();
                    break;
                case 7:
                    setNotifications(scanner);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addHabit(Scanner scanner) {
        System.out.println("Enter habit name:");
        String habitName = scanner.nextLine();
        habits.put(habitName, 0);
        System.out.println("Habit added!");
    }

    private static void viewHabits() {
        System.out.println("Habits:");
        for (String habit : habits.keySet()) {
            System.out.println(habit + ": " + habits.get(habit));
        }
    }

    private static void addGoal(Scanner scanner) {
        System.out.println("Enter goal name:");
        String goalName = scanner.nextLine();
        goals.put(goalName, 0);
        System.out.println("Goal added!");
    }

    private static void viewGoals() {
        System.out.println("Goals:");
        for (String goal : goals.keySet()) {
            System.out.println(goal + ": " + goals.get(goal));
        }
    }

    private static void trackHabit(Scanner scanner) {
        System.out.println("Enter habit name to track:");
        String habitName = scanner.nextLine();
        if (habits.containsKey(habitName)) {
            habits.put(habitName, habits.get(habitName) + 1);
            System.out.println("Habit tracked successfully!");
        } else {
            System.out.println("Habit not found!");
        }
    }

    private static void checkStreak() {
        System.out.println("Check streak for each habit!");
    }

    private static void setNotifications(Scanner scanner) {
        System.out.println("Enable notifications? (yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            notificationsEnabled = true;
            System.out.println("Notifications enabled!");
        } else {
            notificationsEnabled = false;
            System.out.println("Notifications disabled!");
        }
    }
}
```