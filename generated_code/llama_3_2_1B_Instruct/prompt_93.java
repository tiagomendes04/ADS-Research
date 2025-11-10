```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workout {
    private String name;
    private List<Routine> routines;

    public Workout(String name) {
        this.name = name;
        this.routines = new ArrayList<>();
    }

    public void addRoutine(Routine routine) {
        this.routines.add(routine);
    }

    public void removeRoutine(Routine routine) {
        this.routines.remove(routine);
    }

    public String getName() {
        return name;
    }

    public List<Routine> getRoutines() {
        return routines;
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

    public void removeWorkout(Workout workout) {
        this.workouts.remove(workout);
    }

    public String getName() {
        return name;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }
}

class ProgressTracker {
    private Map<String, Double> progress;

    public ProgressTracker() {
        this.progress = new HashMap<>();
    }

    public void addProgress(String workoutName, double progress) {
        if (progress >= 0 && progress <= 100) {
            this.progress.put(workoutName, progress);
        }
    }

    public double getProgress(String workoutName) {
        if (progress.containsKey(workoutName)) {
            return progress.get(workoutName);
        } else {
            return 0;
        }
    }
}

class WorkoutApp {
    private Map<String, Workout> workouts;
    private Map<String, ProgressTracker> progress;

    public WorkoutApp() {
        this.workouts = new HashMap<>();
        this.progress = new HashMap<>();
    }

    public void addWorkout(Workout workout) {
        workouts.put(workout.getName(), workout);
    }

    public void addProgress(String workoutName, double progress) {
        progress.put(workoutName, progress);
    }

    public void viewWorkoutProgress(String workoutName) {
        if (progress.containsKey(workoutName)) {
            System.out.println("Workout Progress: " + progress.get(workoutName) + "%");
        } else {
            System.out.println("No progress available for " + workoutName);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        WorkoutApp app = new WorkoutApp();

        Workout workout1 = new Workout("Chest Workout");
        Workout workout2 = new Workout("Back Workout");

        app.addWorkout(workout1);
        app.addWorkout(workout2);

        WorkoutRoutine routine1 = new WorkoutRoutine("Barbell Squats", workout1, 3);
        WorkoutRoutine routine2 = new WorkoutRoutine("Lat Pulldowns", workout1, 3);
        WorkoutRoutine routine3 = new WorkoutRoutine("Bench Press", workout2, 3);
        WorkoutRoutine routine4 = new WorkoutRoutine("Incline Dumbbell Press", workout2, 3);

        workout1.addRoutine(routine1);
        workout1.addRoutine(routine2);
        workout2.addRoutine(routine3);
        workout2.addRoutine(routine4);

        ProgressTracker progressTracker = new ProgressTracker();
        progressTracker.addProgress("Chest Workout", 80);
        progressTracker.addProgress("Back Workout", 70);

        System.out.println("Workout Progress:");
        app.viewWorkoutProgress("Chest Workout");
    }
}

class WorkoutRoutine {
    private String name;
    private Workout workout;
    private int reps;

    public WorkoutRoutine(String name, Workout workout, int reps) {
        this.name = name;
        this.workout = workout;
        this.reps = reps;
    }
}
```