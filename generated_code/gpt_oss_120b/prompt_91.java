```java
// src/main/java/com/example/habittracker/Habit.java
package com.example.habittracker;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Habit {
    private final String name;
    private final int weeklyGoal; // times per week
    private final Set<LocalDate> completionDates = new HashSet<>();

    public Habit(String name, int weeklyGoal) {
        this.name = name;
        this.weeklyGoal = weeklyGoal;
    }

    public String getName() {
        return name;
    }

    public int getWeeklyGoal() {
        return weeklyGoal;
    }

    public void markCompleted(LocalDate date) {
        completionDates.add(date);
    }

    public boolean isCompletedOn(LocalDate date) {
        return completionDates.contains(date);
    }

    public int getCurrentStreak() {
        int streak = 0;
        LocalDate today = LocalDate.now();
        while (completionDates.contains(today.minusDays(streak))) {
            streak++;
        }
        return streak;
    }

    public int getWeeklyProgress() {
        LocalDate now = LocalDate.now();
        int count = 0;
        for (int i = 0; i < 7; i++) {
            if (completionDates.contains(now.minusDays(i))) {
                count++;
            }
        }
        return count;
    }
}
```

```java
// src/main/java/com/example/habittracker/HabitTracker.java
package com.example.habittracker;

import java.time.LocalDate;
import java.util.*;

public class HabitTracker {
    private final Map<String, Habit> habits = new HashMap<>();

    public void addHabit(String name, int weeklyGoal) {
        habits.putIfAbsent(name, new Habit(name, weeklyGoal));
    }

    public void removeHabit(String name) {
        habits.remove(name);
    }

    public Optional<Habit> getHabit(String name) {
        return Optional.ofNullable(habits.get(name));
    }

    public Collection<Habit> getAllHabits() {
        return habits.values();
    }

    public void markHabitCompleted(String name, LocalDate date) {
        Habit habit = habits.get(name);
        if (habit != null) {
            habit.markCompleted(date);
        }
    }

    public List<String> getDueHabits() {
        List<String> due = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Habit habit : habits.values()) {
            if (!habit.isCompletedOn(today)) {
                due.add(habit.getName());
            }
        }
        return due;
    }
}
```

```java
// src/main/java/com/example/habittracker/NotificationService.java
package com.example.habittracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;

public class NotificationService {
    private final HabitTracker tracker;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public NotificationService(HabitTracker tracker) {
        this.tracker = tracker;
    }

    public void start() {
        Runnable task = () -> {
            List<String> due = tracker.getDueHabits();
            if (!due.isEmpty()) {
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                System.out.println("[" + time + "] Reminder: Complete your habits: " + String.join(", ", due));
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}
```

```java
// src/main/java/com/example/habittracker/Main.java
package com.example.habittracker;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final HabitTracker tracker = new HabitTracker();
    private static final NotificationService notifier = new NotificationService(tracker);

    public static void main(String[] args) {
        notifier.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    System.out.print("Habit name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Weekly goal (times per week): ");
                    int goal = Integer.parseInt(scanner.nextLine().trim());
                    tracker.addHabit(name, goal);
                    System.out.println("Habit added.");
                    break;
                case "2":
                    System.out.print("Habit name to mark completed: ");
                    String hName = scanner.nextLine().trim();
                    tracker.markHabitCompleted(hName, LocalDate.now());
                    System.out.println("Marked as completed for today.");
                    break;
                case "3":
                    System.out.println("--- Habits Overview ---");
                    for (Habit habit : tracker.getAllHabits()) {
                        System.out.printf("Name: %s | Streak: %d days | Weekly Progress: %d/%d%n",
                                habit.getName(),
                                habit.getCurrentStreak(),
                                habit.getWeeklyProgress(),
                                habit.getWeeklyGoal());
                    }
                    break;
                case "4":
                    System.out.print("Habit name to remove: ");
                    String rm = scanner.nextLine().trim();
                    tracker.removeHabit(rm);
                    System.out.println("Habit removed.");
                    break;
                case "0":
                    notifier.stop();
                    System.out.println("Exiting.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Habit Tracker ---");
        System.out.println("1. Add Habit");
        System.out.println("2. Mark Habit Completed Today");
        System.out.println("3. View Habits");
        System.out.println("4. Remove Habit");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }
}
```