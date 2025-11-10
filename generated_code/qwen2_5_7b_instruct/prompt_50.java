```java
import java.util.ArrayList;
import java.util.Date;
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

class Report {
    private ArrayList<Meal> meals;
    private ArrayList<Exercise> exercises;

    public Report() {
        this.meals = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public int getTotalCaloriesConsumed() {
        int totalCalories = 0;
        for (Meal meal : meals) {
            totalCalories += meal.getCalories();
        }
        return totalCalories;
    }

    public int getTotalCaloriesBurned() {
        int totalCalories = 0;
        for (Exercise exercise : exercises) {
            totalCalories += exercise.getCaloriesBurned();
        }
        return totalCalories;
    }

    public void printReport() {
        System.out.println("Calorie Tracker Report");
        System.out.println("Date: " + new Date());

        System.out.println("\nMeals:");
        for (Meal meal : meals) {
            System.out.println("- " + meal.getName() + ": " + meal.getCalories() + " calories");
        }

        System.out.println("\nExercises:");
        for (Exercise exercise : exercises) {
            System.out.println("- " + exercise.getName() + ": " + exercise.getCaloriesBurned() + " calories burned");
        }

        int netCalories = getTotalCaloriesBurned() - getTotalCaloriesConsumed();
        System.out.println("\nNet Calories: " + netCalories + " calories");
    }
}

public class CalorieTracker {
    public static void main(String[] args) {
        Report report = new Report();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Meal\n2. Add Exercise\n3. Print Report\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter meal name: ");
                    String mealName = scanner.nextLine();
                    System.out.print("Enter calories: ");
                    int mealCalories = scanner.nextInt();
                    scanner.nextLine();
                    report.addMeal(new Meal(mealName, mealCalories));
                    break;
                case 2:
                    System.out.print("Enter exercise name: ");
                    String exerciseName = scanner.nextLine();
                    System.out.print("Enter calories burned: ");
                    int exerciseCalories = scanner.nextInt();
                    scanner.nextLine();
                    report.addExercise(new Exercise(exerciseName, exerciseCalories));
                    break;
                case 3:
                    report.printReport();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
```