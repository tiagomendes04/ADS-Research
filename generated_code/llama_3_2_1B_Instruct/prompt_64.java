```java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelItineraryPlanner {
    private List<TravelItinerary> travelItineraries;

    public TravelItineraryPlanner() {
        this.travelItineraries = new ArrayList<>();
    }

    public void addTravelItinerary(TravelItinerary travelItinerary) {
        travelItineraries.add(travelItinerary);
    }

    public List<TravelItinerary> getTravelItineraries() {
        return travelItineraries;
    }

    public static class TravelItinerary {
        private String destination;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<Activity> activities;

        public TravelItinerary(String destination, LocalDate startDate, LocalDate endDate, List<Activity> activities) {
            this.destination = destination;
            this.startDate = startDate;
            this.endDate = endDate;
            this.activities = activities;
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
    }

    public static class Activity {
        private String name;

        public Activity(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
```

```java
import java.util.HashMap;
import java.util.Map;

public class Destinations {
    private Map<String, TravelItinerary> travelItineraries;

    public Destinations() {
        this.travelItineraries = new HashMap<>();
    }

    public void addTravelItinerary(TravelItinerary travelItinerary) {
        travelItineraries.put(travelItinerary.getDestination(), travelItinerary);
    }

    public Map<String, TravelItinerary> getTravelItineraries() {
        return travelItineraries;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Activities {
    private List<Activity> activities;

    public Activities() {
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
```