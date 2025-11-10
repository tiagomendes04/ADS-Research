```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Workout {
    private String name;
    private List<Exercise> exercises;

    public Workout(String name) {
        this.name = name;
        this.exercises = new ArrayList<>();
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
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
    private String description;
    private int sets;
    private int reps;

    public Exercise(String name, String description, int sets, int reps) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
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
}

class Routine {
    private String name;
    private List<Workout> workouts;

    public Routine(String name) {
        this.name = name;
        this.workouts = new ArrayList<>();
    }

    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

    public String getName() {
        return name;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }
}

class Progress {
    private String name;
    private Map<String, Double> progress;

    public Progress(String name) {
        this.name = name;
        this.progress = new HashMap<>();
    }

    public void updateProgress(String workoutName, double progressValue) {
        progress.put(workoutName, progressValue);
    }

    public Map<String, Double> getProgress() {
        return progress;
    }
}
```