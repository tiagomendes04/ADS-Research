```java
// Destination.java
public class Destination {
    private String name;
    private String description;

    public Destination(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

// Activity.java
public class Activity {
    private String name;
    private String description;
    private int duration;

    public Activity(String name, String description, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

// Date.java
import java.time.LocalDate;

public class Date {
    private LocalDate date;

    public Date(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

// TravelItinerary.java
import java.util.ArrayList;
import java.util.List;

public class TravelItinerary {
    private String title;
    private List<Destination> destinations;
    private List<Activity> activities;
    private List<Date> dates;

    public TravelItinerary(String title) {
        this.title = title;
        this.destinations = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.dates = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void addDestination(Destination destination) {
        this.destinations.add(destination);
    }

    public void removeDestination(Destination destination) {
        this.destinations.remove(destination);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

    public List<Date> getDates() {
        return dates;
    }

    public void addDate(Date date) {
        this.dates.add(date);
    }

    public void removeDate(Date date) {
        this.dates.remove(date);
    }
}

// TravelItineraryPlanner.java
import java.util.Scanner;

public class TravelItineraryPlanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TravelItinerary travelItinerary = new TravelItinerary("My Travel Itinerary");

        while (true) {
            System.out.println("1. Add Destination");
            System.out.println("2. Add Activity");
            System.out.println("3. Add Date");
            System.out.println("4. View Destinations");
            System.out.println("5. View Activities");
            System.out.println("6. View Dates");
            System.out.println("7. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter destination name: ");
                    String destinationName = scanner.nextLine();
                    System.out.print("Enter destination description: ");
                    String destinationDescription = scanner.nextLine();
                    Destination destination = new Destination(destinationName, destinationDescription);
                    travelItinerary.addDestination(destination);
                    break;
                case 2:
                    System.out.print("Enter activity name: ");
                    String activityName = scanner.nextLine();
                    System.out.print("Enter activity description: ");
                    String activityDescription = scanner.nextLine();
                    System.out.print("Enter activity duration: ");
                    int activityDuration = scanner.nextInt();
                    scanner.nextLine(); // consume newline left-over
                    Activity activity = new Activity(activityName, activityDescription, activityDuration);
                    travelItinerary.addActivity(activity);
                    break;
                case 3:
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String dateStr = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateStr);
                    Date travelDate = new Date(date);
                    travelItinerary.addDate(travelDate);
                    break;
                case 4:
                    for (Destination destination1 : travelItinerary.getDestinations()) {
                        System.out.println("Destination: " + destination1.getName());
                        System.out.println("Description: " + destination1.getDescription());
                        System.out.println();
                    }
                    break;
                case 5:
                    for (Activity activity1 : travelItinerary.getActivities()) {
                        System.out.println("Activity: " + activity1.getName());
                        System.out.println("Description: " + activity1.getDescription());
                        System.out.println("Duration: " + activity1.getDuration());
                        System.out.println();
                    }
                    break;
                case 6:
                    for (Date date1 : travelItinerary.getDates()) {
                        System.out.println("Date: " + date1.getDate());
                        System.out.println();
                    }
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}
```