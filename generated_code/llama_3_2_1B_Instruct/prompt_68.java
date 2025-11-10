```java
import java.util.*;
import java.util.stream.*;

public class NewsAggregator {
    public static void main(String[] args) {
        NewsSource[] sources = {
            new NewsSource("BBC", "https://www.bbc.com/news"),
            new NewsSource("NYTimes", "https://www.nytimes.com"),
            new NewsSource("CNN", "https://www.cnn.com")
        };

        List<NewsArticle> articles = new ArrayList<>();
        for (NewsSource source : sources) {
            for (String url : source.getUrl().split("/")) {
                articles.add(new NewsArticle(url, source.getName()));
            }
        }

        List<NewsArticle> filteredArticles = articles.stream()
                .filter(article -> article.getPublisher().equals("BBC") || article.getPublisher().equals("NYTimes"))
                .collect(Collectors.toList());

        List<NewsArticle> filteredArticlesWithNotifications = articles.stream()
                .filter(article -> article.getPublisher().equals("CNN") || article.getPublisher().equals("BBC") || article.getPublisher().equals("NYTimes"))
                .collect(Collectors.toList());

        List<NewsArticle> filteredArticlesWithNotificationsAndSources = filteredArticlesWithNotifications.stream()
                .collect(Collectors.toList());

        for (NewsArticle article : filteredArticlesWithNotifications) {
            System.out.println(article.toString());
        }
    }
}

class NewsSource {
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
}

class NewsArticle {
    private String url;
    private String publisher;

    public NewsArticle(String url, String publisher) {
        this.url = url;
        this.publisher = publisher;
    }

    public String getUrl() {
        return url;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "url='" + url + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
```