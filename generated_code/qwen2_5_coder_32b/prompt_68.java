```java
import java.util.*;
import java.util.stream.Collectors;

class NewsArticle {
    private String source;
    private String category;
    private String title;
    private String content;

    public NewsArticle(String source, String category, String title, String content) {
        this.source = source;
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "source='" + source + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

class NewsAggregator {
    private List<NewsArticle> articles;
    private Set<String> subscribedCategories;

    public NewsAggregator() {
        this.articles = new ArrayList<>();
        this.subscribedCategories = new HashSet<>();
    }

    public void addArticle(NewsArticle article) {
        articles.add(article);
    }

    public void subscribeCategory(String category) {
        subscribedCategories.add(category.toLowerCase());
    }

    public List<NewsArticle> getFilteredArticles() {
        return articles.stream()
                .filter(article -> subscribedCategories.contains(article.getCategory().toLowerCase()))
                .collect(Collectors.toList());
    }

    public void notifySubscribers() {
        List<NewsArticle> filteredArticles = getFilteredArticles();
        if (!filteredArticles.isEmpty()) {
            System.out.println("New articles in subscribed categories:");
            filteredArticles.forEach(System.out::println);
        } else {
            System.out.println("No new articles in subscribed categories.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NewsAggregator aggregator = new NewsAggregator();
        aggregator.addArticle(new NewsArticle("BBC", "Technology", "New AI Breakthrough", "Artificial Intelligence has made significant strides..."));
        aggregator.addArticle(new NewsArticle("CNN", "Health", "Vaccine Update", "Latest updates on vaccine distribution and efficacy..."));
        aggregator.addArticle(new NewsArticle("Reuters", "Sports", "World Cup Results", "Final results from the World Cup tournament..."));
        aggregator.addArticle(new NewsArticle("Al Jazeera", "Politics", "Election Results", "Results from the recent national election..."));

        aggregator.subscribeCategory("Technology");
        aggregator.subscribeCategory("Health");

        aggregator.notifySubscribers();
    }
}
```