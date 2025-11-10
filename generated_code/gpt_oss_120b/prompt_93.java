```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* -------------------- Exercise -------------------- */
public class Exercise {
    private final String name;
    private final int sets;
    private final int reps;
    private final double weightKg; // 0 if not weight based
    private final int durationSeconds; // 0 if not time based

    public Exercise(String name, int sets, int reps, double weightKg, int durationSeconds) {
        this.name = Objects.requireNonNull(name);
        this.sets = sets;
        this.reps = reps;
        this.weightKg = weightKg;
        this.durationSeconds = durationSeconds;
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

    public double getWeightKg() {
        return weightKg;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }
}

/* -------------------- Workout -------------------- */
public class Workout {
    private final UUID id;
    private final String title;
    private final LocalDate date;
    private final List<Exercise> exercises;

    public Workout(String title, LocalDate date) {
        this.id = UUID.randomUUID();
        this.title = Objects.requireNonNull(title);
        this.date = Objects.requireNonNull(date);
        this.exercises = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(Objects.requireNonNull(exercise));
    }

    public int getTotalDurationSeconds() {
        return exercises.stream()
                .mapToInt(Exercise::getDurationSeconds)
                .sum();
    }

    public double getTotalWeightLiftedKg() {
        return exercises.stream()
                .mapToDouble(e -> e.getWeightKg() * e.getSets() * e.getReps())
                .sum();
    }
}

/* -------------------- Routine -------------------- */
public class Routine {
    private final UUID id;
    private final String name;
    private final List<Exercise> templateExercises;

    public Routine(String name) {
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name);
        this.templateExercises = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getTemplateExercises() {
        return Collections.unmodifiableList(templateExercises);
    }

    public void addTemplateExercise(Exercise exercise) {
        templateExercises.add(Objects.requireNonNull(exercise));
    }

    public Workout generateWorkout(String title, LocalDate date) {
        Workout workout = new Workout(title, date);
        for (Exercise e : templateExercises) {
            workout.addExercise(e);
        }
        return workout;
    }
}

/* -------------------- ProgressEntry -------------------- */
public class ProgressEntry {
    private final LocalDate date;
    private final UUID workoutId;
    private final double totalWeightKg;
    private final int totalDurationSeconds;
    private final String notes;

    public ProgressEntry(LocalDate date, UUID workoutId, double totalWeightKg, int totalDurationSeconds, String notes) {
        this.date = Objects.requireNonNull(date);
        this.workoutId = Objects.requireNonNull(workoutId);
        this.totalWeightKg = totalWeightKg;
        this.totalDurationSeconds = totalDurationSeconds;
        this.notes = notes == null ? "" : notes;
    }

    public LocalDate getDate() {
        return date;
    }

    public UUID getWorkoutId() {
        return workoutId;
    }

    public double getTotalWeightKg() {
        return totalWeightKg;
    }

    public int getTotalDurationSeconds() {
        return totalDurationSeconds;
    }

    public String getNotes() {
        return notes;
    }
}

/* -------------------- ProgressTracker -------------------- */
public class ProgressTracker {
    private final List<ProgressEntry> entries = new ArrayList<>();

    public void recordWorkout(Workout workout, String notes) {
        ProgressEntry entry = new ProgressEntry(
                workout.getDate(),
                workout.getId(),
                workout.getTotalWeightLiftedKg(),
                workout.getTotalDurationSeconds(),
                notes
        );
        entries.add(entry);
    }

    public List<ProgressEntry> getAllEntries() {
        return Collections.unmodifiableList(entries);
    }

    public List<ProgressEntry> getEntriesBetween(LocalDate start, LocalDate end) {
        List<ProgressEntry> result = new ArrayList<>();
        for (ProgressEntry e : entries) {
            if ((e.getDate().isEqual(start) || e.getDate().isAfter(start)) &&
                (e.getDate().isEqual(end)   || e.getDate().isBefore(end))) {
                result.add(e);
            }
        }
        return result;
    }
}

/* -------------------- User -------------------- */
public class User {
    private final UUID id;
    private final String username;
    private final List<Routine> routines = new ArrayList<>();
    private final ProgressTracker progressTracker = new ProgressTracker();

    public User(String username) {
        this.id = UUID.randomUUID();
        this.username = Objects.requireNonNull(username);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Routine> getRoutines() {
        return Collections.unmodifiableList(routines);
    }

    public void addRoutine(Routine routine) {
        routines.add(Objects.requireNonNull(routine));
    }

    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }
}
```