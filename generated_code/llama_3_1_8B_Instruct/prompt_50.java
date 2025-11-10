```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalorieTracker {

    private List<Meal> meals;
    private List<Exercise> exercises;
    private Report report;

    public CalorieTracker() {
        meals = new ArrayList<>();
        exercises = new ArrayList<>();
        report = new Report();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Meal");
            System.out.println("2. Add Exercise");
            System.out.println("3. View Report");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    addMeal(scanner);
                    break;
                case 2:
                    addExercise(scanner);
                    break;
                case 3:
                    viewReport();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private void addMeal(Scanner scanner) {
        System.out.print("Enter meal date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        System.out.print("Enter meal name: ");
        String name = scanner.nextLine();

        System.out.print("Enter calorie intake: ");
        int calorieIntake = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        meals.add(new Meal(date, name, calorieIntake));
        System.out.println("Meal added successfully.");
    }

    private void addExercise(Scanner scanner) {
        System.out.print("Enter exercise date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        System.out.print("Enter exercise name: ");
        String name = scanner.nextLine();

        System.out.print("Enter calorie burn: ");
        int calorieBurn = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        exercises.add(new Exercise(date, name, calorieBurn));
        System.out.println("Exercise added successfully.");
    }

    private void viewReport() {
        report.generateReport(meals, exercises);
        report.printReport();
    }

    public static void main(String[] args) {
        CalorieTracker tracker = new CalorieTracker();
        tracker.run();
    }
}
```

```java
public class Meal {
    private LocalDate date;
    private String name;
    private int calorieIntake;

    public Meal(LocalDate date, String name, int calorieIntake) {
        this.date = date;
        this.name = name;
        this.calorieIntake = calorieIntake;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getCalorieIntake() {
        return calorieIntake;
    }
}
```

```java
public class Exercise {
    private LocalDate date;
    private String name;
    private int calorieBurn;

    public Exercise(LocalDate date, String name, int calorieBurn) {
        this.date = date;
        this.name = name;
        this.calorieBurn = calorieBurn;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getCalorieBurn() {
        return calorieBurn;
    }
}
```

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Report {
    public void generateReport(List<Meal> meals, List<Exercise> exercises) {
        for (Meal meal : meals) {
            System.out.println("Meal on " + meal.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ": " + meal.getName() + " - " + meal.getCalorieIntake() + " calories");
        }

        for (Exercise exercise : exercises) {
            System.out.println("Exercise on " + exercise.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ": " + exercise.getName() + " - " + exercise.getCalorieBurn() + " calories");
        }
    }

    public void printReport() {
        System.out.println("Total Calories Consumed: ");
        // Calculate total calories consumed
        System.out.println("Total Calories Burned: ");
        // Calculate total calories burned
        System.out.println("Net Calories: ");
        // Calculate net calories
    }
}
```