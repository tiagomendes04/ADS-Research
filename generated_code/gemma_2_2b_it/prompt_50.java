```java
import java.util.ArrayList;
import java.util.Scanner;

public class CalorieTracker {

    static ArrayList<Meal> meals = new ArrayList<>();
    static ArrayList<Exercise> exercises = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Add Meal");
            System.out.println("2. Add Exercise");
            System.out.println("3. View Meals");
            System.out.println("4. View Exercises");
            System.out.println("5. View Reports");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addMeal();
                    break;
                case 2:
                    addExercise();
                    break;
                case 3:
                    viewMeals();
                    break;
                case 4:
                    viewExercises();
                    break;
                case 5:
                    viewReports();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addMeal() {
        System.out.println("Enter meal name: ");
        String name = scanner.nextLine();

        System.out.println("Enter calories: ");
        double calories = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Meal meal = new Meal(name, calories);
        meals.add(meal);
        System.out.println("Meal added successfully.");
    }

    static void addExercise() {
        System.out.println("Enter exercise name: ");
        String name = scanner.nextLine();

        System.out.println("Enter duration (in minutes): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Exercise exercise = new Exercise(name, duration);
        exercises.add(exercise);
        System.out.println("Exercise added successfully.");
    }

    static void viewMeals() {
        if (meals.isEmpty()) {
            System.out.println("No meals added yet.");
            return;
        }

        System.out.println("----- Meals -----");
        for (Meal meal : meals) {
            System.out.println(meal);
        }
    }

    static void viewExercises() {
        if (exercises.isEmpty()) {
            System.out.println("No exercises added yet.");
            return;
        }

        System.out.println("----- Exercises -----");
        for (Exercise exercise : exercises) {
            System.out.println(exercise);
        }
    }

    static void viewReports() {
        // Implementation for generating reports (Calorie intake, Exercise logs, etc.)
    }
}

class Meal {
    String name;
    double calories;

    public Meal(String name, double calories) {
        this.name = name;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Meal: " + name + ", Calories: " + calories;
    }
}

class Exercise {
    String name;
    int duration;

    public Exercise(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Exercise: " + name + ", Duration: " + duration;
    }
}
```