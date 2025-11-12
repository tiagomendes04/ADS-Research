```java
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Destination {
    private String name;
    private String location;

    public Destination(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}

class Activity {
    private String description;
    private Date time;

    public Activity(String description, Date time) {
        this.description = description;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public Date getTime() {
        return time;
    }
}

class Itinerary {
    private List<Destination> destinations;
    private List<Activity> activities;

    public Itinerary() {
        this.destinations = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}

class TravelItineraryPlanner {
    private Itinerary itinerary;

    public TravelItineraryPlanner() {
        this.itinerary = new Itinerary();
    }

    public void planTrip(List<Destination> destinations, List<Activity> activities) {
        for (Destination destination : destinations) {
            itinerary.addDestination(destination);
        }
        for (Activity activity : activities) {
            itinerary.addActivity(activity);
        }
    }

    public Itinerary getItinerary() {
        return itinerary;
    }
}
```