```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HabitTrackerApplication {
    private static List<Habit> habits = new ArrayList<>();
    private static List<Goal> goals = new ArrayList<>();
    private static List<Notification> notifications = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Habit");
            System.out.println("2. Add Goal");
            System.out.println("3. Add Notification");
            System.out.println("4. View Habits");
            System.out.println("5. View Goals");
            System.out.println("6. View Notifications");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    addHabit();
                    break;
                case 2:
                    addGoal();
                    break;
                case 3:
                    addNotification();
                    break;
                case 4:
                    viewHabits();
                    break;
                case 5:
                    viewGoals();
                    break;
                case 6:
                    viewNotifications();
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void addHabit() {
        System.out.print("Enter habit name: ");
        String habitName = scanner.nextLine();
        Habit habit = new Habit(habitName);
        habits.add(habit);
    }

    private static void addGoal() {
        System.out.print("Enter goal name: ");
        String goalName = scanner.nextLine();
        Goal goal = new Goal(goalName);
        goals.add(goal);
    }

    private static void addNotification() {
        System.out.print("Enter notification message: ");
        String notificationMessage = scanner.nextLine();
        Notification notification = new Notification(notificationMessage);
        notifications.add(notification);
    }

    private static void viewHabits() {
        System.out.println("Habits:");
        for (Habit habit : habits) {
            System.out.println(habit.toString());
        }
    }

    private static void viewGoals() {
        System.out.println("Goals:");
        for (Goal goal : goals) {
            System.out.println(goal.toString());
        }
    }

    private static void viewNotifications() {
        System.out.println("Notifications:");
        for (Notification notification : notifications) {
            System.out.println(notification.toString());
        }
    }

    private static class Habit {
        private String name;

        public Habit(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Habit{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static class Goal {
        private String name;

        public Goal(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Goal{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static class Notification {
        private String message;

        public Notification(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Notification{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
```