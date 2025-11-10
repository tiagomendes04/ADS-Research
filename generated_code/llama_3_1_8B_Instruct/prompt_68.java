```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsAggregator {
    private List<NewsSource> sources;
    private List<Notification> notifications;
    private Scanner scanner;

    public NewsAggregator() {
        sources = new ArrayList<>();
        notifications = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addSource(NewsSource source) {
        sources.add(source);
    }

    public void removeSource(NewsSource source) {
        sources.remove(source);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }

    public void displaySources() {
        System.out.println("Available Sources:");
        for (int i = 0; i < sources.size(); i++) {
            System.out.println((i + 1) + ". " + sources.get(i).getName());
        }
    }

    public void displayNotifications() {
        System.out.println("Available Notifications:");
        for (int i = 0; i < notifications.size(); i++) {
            System.out.println((i + 1) + ". " + notifications.get(i).getTitle());
        }
    }

    public void filterSources() {
        System.out.println("Enter the name of the source to filter by:");
        String sourceName = scanner.nextLine();
        List<NewsSource> filteredSources = new ArrayList<>();
        for (NewsSource source : sources) {
            if (source.getName().toLowerCase().contains(sourceName.toLowerCase())) {
                filteredSources.add(source);
            }
        }
        System.out.println("Filtered Sources:");
        for (int i = 0; i < filteredSources.size(); i++) {
            System.out.println((i + 1) + ". " + filteredSources.get(i).getName());
        }
    }

    public void start() {
        while (true) {
            System.out.println("1. Add Source");
            System.out.println("2. Remove Source");
            System.out.println("3. Add Notification");
            System.out.println("4. Remove Notification");
            System.out.println("5. Display Sources");
            System.out.println("6. Display Notifications");
            System.out.println("7. Filter Sources");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    addSource(createSource());
                    break;
                case 2:
                    removeSource(createSource());
                    break;
                case 3:
                    addNotification(createNotification());
                    break;
                case 4:
                    removeNotification(createNotification());
                    break;
                case 5:
                    displaySources();
                    break;
                case 6:
                    displayNotifications();
                    break;
                case 7:
                    filterSources();
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private NewsSource createSource() {
        System.out.print("Enter source name: ");
        String name = scanner.nextLine();
        System.out.print("Enter source URL: ");
        String url = scanner.nextLine();
        return new NewsSource(name, url);
    }

    private Notification createNotification() {
        System.out.print("Enter notification title: ");
        String title = scanner.nextLine();
        System.out.print("Enter notification message: ");
        String message = scanner.nextLine();
        return new Notification(title, message);
    }

    public static void main(String[] args) {
        NewsAggregator aggregator = new NewsAggregator();
        aggregator.start();
    }
}
```

```java
public class NewsSource {
    private String name;
    private String url;

    public NewsSource(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NewsSource other = (NewsSource) obj;
        return name.equals(other.name) && url.equals(other.url);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + url.hashCode();
    }

    @Override
    public String toString() {
        return name + " (" + url + ")";
    }
}
```

```java
public class Notification {
    private String title;
    private String message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Notification other = (Notification) obj;
        return title.equals(other.title) && message.equals(other.message);
    }

    @Override
    public int hashCode() {
        return title.hashCode() + message.hashCode();
    }

    @Override
    public String toString() {
        return title + ": " + message;
    }
}
```