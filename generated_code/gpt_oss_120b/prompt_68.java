// NewsItem.java
import java.time.LocalDateTime;
import java.util.List;

public class NewsItem {
    private final String title;
    private final String content;
    private final String source;
    private final LocalDateTime timestamp;
    private final List<String> categories;

    public NewsItem(String title, String content, String source, LocalDateTime timestamp, List<String> categories) {
        this.title = title;
        this.content = content;
        this.source = source;
        this.timestamp = timestamp;
        this.categories = categories;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSource() { return source; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public List<String> getCategories() { return categories; }

    @Override
    public String toString() {
        return "[" + timestamp + "] (" + source + ") " + title + " - " + String.join(", ", categories);
    }
}

// NewsSource.java
import java.util.List;

public interface NewsSource {
    List<NewsItem> fetch();
}

// RSSSource.java
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RSSSource implements NewsSource {
    private final String name;
    private final String feedUrl;

    public RSSSource(String name, String feedUrl) {
        this.name = name;
        this.feedUrl = feedUrl;
    }

    @Override
    public List<NewsItem> fetch() {
        // Mock implementation – in a real app you'd parse the RSS feed.
        List<NewsItem> items = new ArrayList<>();
        items.add(new NewsItem(
                "Breaking News from " + name,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                name,
                LocalDateTime.now(),
                Arrays.asList("Breaking", "World")
        ));
        return items;
    }
}

// APISource.java
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APISource implements NewsSource {
    private final String name;
    private final String endpoint;

    public APISource(String name, String endpoint) {
        this.name = name;
        this.endpoint = endpoint;
    }

    @Override
    public List<NewsItem> fetch() {
        // Mock implementation – in a real app you'd perform an HTTP request.
        List<NewsItem> items = new ArrayList<>();
        items.add(new NewsItem(
                "API Update from " + name,
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                name,
                LocalDateTime.now(),
                Arrays.asList("Tech", "Update")
        ));
        return items;
    }
}

// Filter.java
public interface Filter {
    boolean test(NewsItem item);
}

// KeywordFilter.java
import java.util.Locale;

public class KeywordFilter implements Filter {
    private final String keyword;

    public KeywordFilter(String keyword) {
        this.keyword = keyword.toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean test(NewsItem item) {
        return item.getTitle().toLowerCase(Locale.ROOT).contains(keyword) ||
               item.getContent().toLowerCase(Locale.ROOT).contains(keyword);
    }
}

// CategoryFilter.java
import java.util.Set;

public class CategoryFilter implements Filter {
    private final Set<String> allowedCategories;

    public CategoryFilter(Set<String> allowedCategories) {
        this.allowedCategories = allowedCategories;
    }

    @Override
    public boolean test(NewsItem item) {
        for (String cat : item.getCategories()) {
            if (allowedCategories.contains(cat)) {
                return true;
            }
        }
        return false;
    }
}

// NotificationService.java
public interface NotificationService {
    void notify(NewsItem item);
}

// ConsoleNotificationService.java
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notify(NewsItem item) {
        System.out.println("NEW ITEM: " + item);
    }
}

// EmailNotificationService.java
public class EmailNotificationService implements NotificationService {
    private final String recipientEmail;

    public EmailNotificationService(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @Override
    public void notify(NewsItem item) {
        // Mock email sending – replace with real SMTP logic.
        System.out.println("Sending email to " + recipientEmail + " about: " + item.getTitle());
    }
}

// NewsAggregator.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class NewsAggregator {
    private final List<NewsSource> sources = new CopyOnWriteArrayList<>();
    private final List<Filter> filters = new CopyOnWriteArrayList<>();
    private final NotificationService notificationService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final long fetchIntervalSeconds;

    public NewsAggregator(NotificationService notificationService, long fetchIntervalSeconds) {
        this.notificationService = notificationService;
        this.fetchIntervalSeconds = fetchIntervalSeconds;
    }

    public void addSource(NewsSource source) {
        sources.add(source);
    }

    public void removeSource(NewsSource source) {
        sources.remove(source);
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::fetchAndProcess, 0, fetchIntervalSeconds, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdownNow();
    }

    private void fetchAndProcess() {
        List<NewsItem> allItems = new ArrayList<>();
        for (NewsSource source : sources) {
            try {
                allItems.addAll(source.fetch());
            } catch (Exception e) {
                System.err.println("Error fetching from source: " + e.getMessage());
            }
        }

        for (NewsItem item : allItems) {
            if (passesFilters(item)) {
                notificationService.notify(item);
            }
        }
    }

    private boolean passesFilters(NewsItem item) {
        for (Filter filter : filters) {
            if (!filter.test(item)) {
                return false;
            }
        }
        return true;
    }

    public List<NewsSource> getSources() {
        return Collections.unmodifiableList(sources);
    }

    public List<Filter> getFilters() {
        return Collections.unmodifiableList(filters);
    }
}

// Main.java
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Choose notification channel
        NotificationService notifier = new ConsoleNotificationService();
        // NotificationService notifier = new EmailNotificationService("user@example.com");

        NewsAggregator aggregator = new NewsAggregator(notifier, 30); // fetch every 30 seconds

        // Add sources
        aggregator.addSource(new RSSSource("TechCrunch", "https://techcrunch.com/feed/"));
        aggregator.addSource(new APISource("NewsAPI", "https://newsapi.org/v2/top-headlines"));

        // Add filters
        aggregator.addFilter(new KeywordFilter("breaking"));
        Set<String> categories = new HashSet<>();
        categories.add("Tech");
        categories.add("World");
        aggregator.addFilter(new CategoryFilter(categories));

        // Start aggregation
        aggregator.start();

        // Run for a limited time for demo purposes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down aggregator...");
            aggregator.stop();
        }));
    }
}