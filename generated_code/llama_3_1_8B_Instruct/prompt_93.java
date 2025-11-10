```java
// Workout.java
public class Workout {
    private String name;
    private String description;
    private List<Exercise> exercises;

    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
        this.exercises = new ArrayList<>();
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}

// Exercise.java
public class Exercise {
    private String name;
    private String description;
    private int sets;
    private int reps;
    private double weight;

    public Exercise(String name, String description, int sets, int reps, double weight) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

// Routine.java
public class Routine {
    private String name;
    private String description;
    private List<Workout> workouts;
    private List<Progress> progress;

    public Routine(String name, String description) {
        this.name = name;
        this.description = description;
        this.workouts = new ArrayList<>();
        this.progress = new ArrayList<>();
    }

    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
    }

    public void addProgress(Progress progress) {
        this.progress.add(progress);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public List<Progress> getProgress() {
        return progress;
    }
}

// Progress.java
public class Progress {
    private String date;
    private String description;
    private List<WorkoutProgress> workoutProgress;

    public Progress(String date, String description) {
        this.date = date;
        this.description = description;
        this.workoutProgress = new ArrayList<>();
    }

    public void addWorkoutProgress(WorkoutProgress workoutProgress) {
        this.workoutProgress.add(workoutProgress);
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public List<WorkoutProgress> getWorkoutProgress() {
        return workoutProgress;
    }
}

// WorkoutProgress.java
public class WorkoutProgress {
    private Workout workout;
    private boolean completed;

    public WorkoutProgress(Workout workout, boolean completed) {
        this.workout = workout;
        this.completed = completed;
    }

    public Workout getWorkout() {
        return workout;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

// FitnessApp.java
import java.util.ArrayList;
import java.util.List;

public class FitnessApp {
    private List<Routine> routines;

    public FitnessApp() {
        this.routines = new ArrayList<>();
    }

    public void addRoutine(Routine routine) {
        this.routines.add(routine);
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public static void main(String[] args) {
        FitnessApp app = new FitnessApp();

        // Create workouts
        Workout workout1 = new Workout("Workout 1", "Description 1");
        Workout workout2 = new Workout("Workout 2", "Description 2");

        // Create exercises
        Exercise exercise1 = new Exercise("Exercise 1", "Description 1", 3, 10, 20.0);
        Exercise exercise2 = new Exercise("Exercise 2", "Description 2", 3, 10, 20.0);

        // Add exercises to workouts
        workout1.addExercise(exercise1);
        workout2.addExercise(exercise2);

        // Create routine
        Routine routine = new Routine("Routine 1", "Description 1");

        // Add workouts to routine
        routine.addWorkout(workout1);
        routine.addWorkout(workout2);

        // Create progress
        Progress progress = new Progress("2022-01-01", "Description 1");

        // Create workout progress
        WorkoutProgress workoutProgress = new WorkoutProgress(workout1, true);

        // Add workout progress to progress
        progress.addWorkoutProgress(workoutProgress);

        // Add routine and progress to app
        app.addRoutine(routine);
        routine.addProgress(progress);

        // Print routines and progress
        for (Routine r : app.getRoutines()) {
            System.out.println("Routine: " + r.getName());
            for (Workout w : r.getWorkouts()) {
                System.out.println("  Workout: " + w.getName());
                for (Exercise e : w.getExercises()) {
                    System.out.println("    Exercise: " + e.getName());
                }
            }
            for (Progress p : r.getProgress()) {
                System.out.println("  Progress: " + p.getDate());
                for (WorkoutProgress wp : p.getWorkoutProgress()) {
                    System.out.println("    Workout Progress: " + wp.getWorkout().getName() + " - " + wp.isCompleted());
                }
            }
        }
    }
}
```