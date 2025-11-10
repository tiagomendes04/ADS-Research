```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalorieTrackerApp {
    public static void main(String[] args) {
        CalorieTracker tracker = new CalorieTracker();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Calorie Tracker ---");
            System.out.println("1. Add Meal");
            System.out.println("2. Add Exercise");
            System.out.println("3. Show Report");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addMeal(tracker, scanner);
                    break;
                case "2":
                    addExercise(tracker, scanner);
                    break;
                case "3":
                    ReportGenerator.printReport(tracker);
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addMeal(CalorieTracker tracker, Scanner scanner) {
        System.out.print("Meal name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Calories (kcal): ");
        int calories = parseInt(scanner.nextLine().trim());
        Meal meal = new Meal(name, calories);
        tracker.addMeal(meal);
        System.out.println("Meal added.");
    }

    private static void addExercise(CalorieTracker tracker, Scanner scanner) {
        System.out.print("Exercise name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Calories burned (kcal): ");
        int calories = parseInt(scanner.nextLine().trim());
        Exercise exercise = new Exercise(name, calories);
        tracker.addExercise(exercise);
        System.out.println("Exercise added.");
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

class Meal {
    private final String name;
    private final int calories;

    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() { return name; }
    public int getCalories() { return calories; }
}

class Exercise {
    private final String name;
    private final int caloriesBurned;

    public Exercise(String name, int caloriesBurned) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    public String getName() { return name; }
    public int getCaloriesBurned() { return caloriesBurned; }
}

class CalorieTracker {
    private final List<Meal> meals = new ArrayList<>();
    private final List<Exercise> exercises = new ArrayList<>();

    public void addMeal(Meal meal) { meals.add(meal); }
    public void addExercise(Exercise exercise) { exercises.add(exercise); }

    public List<Meal> getMeals() { return new ArrayList<>(meals); }
    public List<Exercise> getExercises() { return new ArrayList<>(exercises); }

    public int totalIntake() {
        return meals.stream().mapToInt(Meal::getCalories).sum();
    }

    public int totalBurned() {
        return exercises.stream().mapToInt(Exercise::getCaloriesBurned).sum();
    }

    public int netCalories() {
        return totalIntake() - totalBurned();
    }
}

class ReportGenerator {
    public static void printReport(CalorieTracker tracker) {
        System.out.println("\n--- Daily Report ---");
        System.out.println("Meals:");
        if (tracker.getMeals().isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Meal m : tracker.getMeals()) {
                System.out.printf("  %s: %d kcal%n", m.getName(), m.getCalories());
            }
        }
        System.out.println("Exercises:");
        if (tracker.getExercises().isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Exercise e : tracker.getExercises()) {
                System.out.printf("  %s: %d kcal burned%n", e.getName(), e.getCaloriesBurned());
            }
        }
        System.out.println("---------------------");
        System.out.printf("Total intake: %d kcal%n", tracker.totalIntake());
        System.out.printf("Total burned: %d kcal%n", tracker.totalBurned());
        System.out.printf("Net calories: %d kcal%n", tracker.netCalories());
    }
}
```