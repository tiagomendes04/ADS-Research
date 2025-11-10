```java
import java.util.Date;

class Destination {
    private String name;
    private String description;

    public Destination(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class Activity {
    private String name;
    private String description;

    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class ItineraryPlanner {
    private Destination destination;
    private Date startDate;
    private Date endDate;
    private Activity[] activities;

    public ItineraryPlanner(Destination destination, Date startDate, Date endDate, Activity[] activities) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = activities;
    }

    public Destination getDestination() {
        return destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Activity[] getActivities() {
        return activities;
    }

    @Override
    public String toString() {
        return "Itinerary for " + destination.getName() + " from " + startDate + " to " + endDate +
               "\nActivities: " + String.join(", ", activities.stream().map(Activity::getName).toArray(String[]::new));
    }
}

class ItineraryExample {
    public static void main(String[] args) {
        Destination paris = new Destination("Paris", "The City of Light");
        Date startDate = new Date();
        Date endDate = new Date();
        // Set dates for demonstration
        startDate.setYear(startDate.getYear() + 1); // Example: Next year
        endDate.setYear(endDate.getYear() + 1); // Example: Next year

        Activity visitEiffelTower = new Activity("Visit Eiffel Tower", "Experience the iconic tower");
        Activity seeNotreDame = new Activity("See Notre Dame", "Explore the historic cathedral");

        ItineraryPlanner itinerary = new ItineraryPlanner(paris, startDate, endDate, new Activity[]{visitEiffelTower, seeNotreDame});

        System.out.println(itinerary);
    }
}
```