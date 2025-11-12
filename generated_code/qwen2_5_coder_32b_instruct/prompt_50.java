```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Meal {
    private String name;
    private int calories;

    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }
}

class Exercise {
    private String name;
    private int caloriesBurned;

    public Exercise(String name, int caloriesBurned) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    public String getName() {
        return name;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }
}

class CalorieTracker {
    private List<Meal> meals;
    private List<Exercise> exercises;

    public CalorieTracker() {
        meals = new ArrayList<>();
        exercises = new ArrayList<>();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public int getTotalCaloriesConsumed() {
        int total = 0;
        for (Meal meal : meals) {
            total += meal.getCalories();
        }
        return total;
    }

    public int getTotalCaloriesBurned() {
        int total = 0;
        for (Exercise exercise : exercises) {
            total += exercise.getCaloriesBurned();
        }
        return total;
    }

    public int getNetCalories() {
        return getTotalCaloriesConsumed() - getTotalCaloriesBurned();
    }

    public void generateReport() {
        System.out.println("Calorie Tracker Report");
        System.out.println(new Date());
        System.out.println("Meals:");
        for (Meal meal : meals) {
            System.out.println("- " + meal.getName() + ": " + meal.getCalories() + " calories");
        }
        System.out.println("Exercises:");
        for (Exercise exercise : exercises) {
            System.out.println("- " + exercise.getName() + ": " + exercise.getCaloriesBurned() + " calories burned");
        }
        System.out.println("Total Calories Consumed: " + getTotalCaloriesConsumed());
        System.out.println("Total Calories Burned: " + getTotalCaloriesBurned());
        System.out.println("Net Calories: " + getNetCalories());
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CalorieTracker tracker = new CalorieTracker();

        while (true) {
            System.out.println("\nCalorie Tracker Menu");
            System.out.println("1. Add Meal");
            System.out.println("2. Add Exercise");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter meal name: ");
                    String mealName = scanner.nextLine();
                    System.out.print("Enter calories in meal: ");
                    int mealCalories = scanner.nextInt();
                    tracker.addMeal(new Meal(mealName, mealCalories));
                    break;
                case 2:
                    System.out.print("Enter exercise name: ");
                    String exerciseName = scanner.nextLine();
                    System.out.print("Enter calories burned in exercise: ");
                    int exerciseCalories = scanner.nextInt();
                    tracker.addExercise(new Exercise(exerciseName, exerciseCalories));
                    break;
                case 3:
                    tracker.generateReport();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
```