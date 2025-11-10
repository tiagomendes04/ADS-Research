```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Itinerary {

    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Activity> activities;

    public Itinerary(String destination, LocalDate startDate, LocalDate endDate) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = new ArrayList<>();
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", activities=" + activities +
                '}';
    }
}

class Activity {
    private String name;
    private LocalDate date;

    public Activity(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
```



```java
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Itinerary itinerary = new Itinerary("Paris", LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 25));
        Activity activity = new Activity("Eiffel Tower Visit", LocalDate.of(2024, 3, 18));
        itinerary.addActivity(activity);
        System.out.println(itinerary);
    }
}
```