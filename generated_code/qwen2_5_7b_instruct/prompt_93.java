```java
import java.util.ArrayList;
import java.util.List;

public class Workout {
    private String name;
    private List<Exercise> exercises;

    public Workout(String name) {
        this.name = name;
        this.exercises = new ArrayList<>();
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}

class Exercise {
    private String name;
    private int sets;
    private int reps;
    private double weight;

    public Exercise(String name, int sets, int reps, double weight) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }
}

class Routine {
    private String name;
    private List<Workout> workouts;

    public Routine(String name) {
        this.name = name;
        this.workouts = new ArrayList<>();
    }

    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
    }

    public String getName() {
        return name;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }
}

class ProgressTracker {
    private List<Routine> routines;

    public ProgressTracker() {
        this.routines = new ArrayList<>();
    }

    public void addRoutine(Routine routine) {
        this.routines.add(routine);
    }

    public void displayProgress() {
        for (Routine routine : routines) {
            System.out.println("Routine: " + routine.getName());
            for (Workout workout : routine.getWorkouts()) {
                System.out.println("  Workout: " + workout.getName());
                for (Exercise exercise : workout.getExercises()) {
                    System.out.println("    Exercise: " + exercise.getName() + " (" + exercise.getSets() + "x" + exercise.getReps() + ", " + exercise.getWeight() + " lbs)");
                }
            }
        }
    }
}
```