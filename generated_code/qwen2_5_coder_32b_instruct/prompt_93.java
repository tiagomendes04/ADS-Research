```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Workout {
    private String name;
    private int duration; // in minutes

    public Workout(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
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
    private Date date;
    private int weight; // in kg
    private int repetitions; // total reps completed

    public Progress(Date date, int weight, int repetitions) {
        this.date = date;
        this.weight = weight;
        this.repetitions = repetitions;
    }

    public Date getDate() {
        return date;
    }

    public int getWeight() {
        return weight;
    }

    public int getRepetitions() {
        return repetitions;
    }
}

class FitnessApp {
    private List<Routine> routines;
    private List<Progress> progressList;

    public FitnessApp() {
        this.routines = new ArrayList<>();
        this.progressList = new ArrayList<>();
    }

    public void addRoutine(Routine routine) {
        routines.add(routine);
    }

    public void trackProgress(Progress progress) {
        progressList.add(progress);
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public List<Progress> getProgressList() {
        return progressList;
    }
}
```