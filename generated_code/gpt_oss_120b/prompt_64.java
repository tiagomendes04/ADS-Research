```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// ------------------------------------------------------------
// Activity.java
// ------------------------------------------------------------
public class Activity {
    private String name;
    private String description;
    private LocalDate date;

    public Activity(String name, String description, LocalDate date) {
        this.name = Objects.requireNonNull(name);
        this.description = description;
        this.date = Objects.requireNonNull(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}

// ------------------------------------------------------------
// Destination.java
// ------------------------------------------------------------
public class Destination {
    private String city;
    private String country;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private final List<Activity> activities = new ArrayList<>();

    public Destination(String city, String country, LocalDate arrivalDate, LocalDate departureDate) {
        this.city = Objects.requireNonNull(city);
        this.country = Objects.requireNonNull(country);
        this.arrivalDate = Objects.requireNonNull(arrivalDate);
        this.departureDate = Objects.requireNonNull(departureDate);
        if (departureDate.isBefore(arrivalDate)) {
            throw new IllegalArgumentException("Departure date cannot be before arrival date");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = Objects.requireNonNull(city);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = Objects.requireNonNull(country);
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = Objects.requireNonNull(arrivalDate);
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = Objects.requireNonNull(departureDate);
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    public void addActivity(Activity activity) {
        Objects.requireNonNull(activity);
        if (activity.getDate().isBefore(arrivalDate) || activity.getDate().isAfter(departureDate)) {
            throw new IllegalArgumentException("Activity date must be within the stay period");
        }
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    @Override
    public String toString() {
        return "Destination{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", activities=" + activities +
                '}';
    }
}

// ------------------------------------------------------------
// Itinerary.java
// ------------------------------------------------------------
public class Itinerary {
    private String title;
    private final List<Destination> destinations = new ArrayList<>();

    public Itinerary(String title) {
        this.title = Objects.requireNonNull(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title);
    }

    public List<Destination> getDestinations() {
        return Collections.unmodifiableList(destinations);
    }

    public void addDestination(Destination destination) {
        Objects.requireNonNull(destination);
        // Ensure chronological order and no overlapping stays
        for (Destination d : destinations) {
            boolean overlaps = !(destination.getDepartureDate().isBefore(d.getArrivalDate()) ||
                                 destination.getArrivalDate().isAfter(d.getDepartureDate()));
            if (overlaps) {
                throw new IllegalArgumentException("Destination dates overlap with existing destination");
            }
        }
        destinations.add(destination);
        destinations.sort((d1, d2) -> d1.getArrivalDate().compareTo(d2.getArrivalDate()));
    }

    public void removeDestination(Destination destination) {
        destinations.remove(destination);
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "title='" + title + '\'' +
                ", destinations=" + destinations +
                '}';
    }
}

// ------------------------------------------------------------
// Example usage (optional, can be removed)
// ------------------------------------------------------------
class Main {
    public static void main(String[] args) {
        Itinerary itinerary = new Itinerary("European Adventure");

        Destination paris = new Destination(
                "Paris", "France",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 5));
        paris.addActivity(new Activity("Eiffel Tower visit", "Guided tour", LocalDate.of(2025, 6, 2)));
        paris.addActivity(new Activity("Louvre Museum", "See the Mona Lisa", LocalDate.of(2025, 6, 3)));

        Destination rome = new Destination(
                "Rome", "Italy",
                LocalDate.of(2025, 6, 6),
                LocalDate.of(2025, 6, 10));
        rome.addActivity(new Activity("Colosseum", "Historical tour", LocalDate.of(2025, 6, 7)));

        itinerary.addDestination(paris);
        itinerary.addDestination(rome);

        System.out.println(itinerary);
    }
}
```