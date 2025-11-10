```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Meal {
    private String name;
    private int calories;
    private String date;

    public Meal(String name, int calories, String date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public String getDate() {
        return date;
    }
}

class Exercise {
    private String name;
    private int calories;

    public Exercise(String name, int calories) {
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

class Report {
    private String date;
    private String food;
    private int calories;
    private String exercise;

    public Report(String date, String food, int calories, String exercise) {
        this.date = date;
        this.food = food;
        this.calories = calories;
        this.exercise = exercise;
    }

    public String getDate() {
        return date;
    }

    public String getFood() {
        return food;
    }

    public int getCalories() {
        return calories;
    }

    public String getExercise() {
        return exercise;
    }
}

public class CalorieTracker {
    private ArrayList<Meal> meals = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<Report> reports = new ArrayList<>();

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public void report() {
        for (Report report : reports) {
            System.out.println("Date: " + report.getDate() + ", Food: " + report.getFood() + ", Calories: " + report.getCalories() + ", Exercise: " + report.getExercise());
        }
    }

    public static void main(String[] args) {
        CalorieTracker tracker = new CalorieTracker();
        tracker.addMeal(new Meal("Breakfast", 500, LocalDate.of(2024, 1, 1)));
        tracker.addMeal(new Meal("Lunch", 700, LocalDate.of(2024, 1, 2)));
        tracker.addMeal(new Meal("Dinner", 800, LocalDate.of(2024, 1, 3)));

        tracker.addExercise(new Exercise("Run", 400));
        tracker.addExercise(new Exercise("Swim", 200));

        tracker.addReport(new Report("2024-01-01", "Eggs", 250, "Run"));
        tracker.addReport(new Report("2024-01-02", "Oatmeal", 300, "Swim"));
        tracker.addReport(new Report("2024-01-03", "Chicken", 350, "Run"));
        tracker.addReport(new Report("2024-01-04", "Salad", 200, "Swim"));
        tracker.addReport(new Report("2024-01-05", "Bread", 300, "Run"));
        tracker.addReport(new Report("2024-01-06", "Yogurt", 250, "Swim"));
        tracker.addReport(new Report("2024-01-07", "Apple", 200, "Run"));
        tracker.addReport(new Report("2024-01-08", "Carrot", 300, "Swim"));
        tracker.addReport(new Report("2024-01-09", "Banana", 250, "Run"));
        tracker.addReport(new Report("2024-01-10", "Wheat", 300, "Swim"));
    }
}
```