```java
import java.util.ArrayList;
import java.util.List;

class NewsArticle {
    private String title;
    private String content;
    private String source;
    private String publishDate;

    public NewsArticle(String title, String content, String source, String publishDate) {
        this.title = title;
        this.content = content;
        this.source = source;
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }

    public String getPublishDate() {
        return publishDate;
    }
}

class NewsSource {
    private String name;
    private List<NewsArticle> articles;

    public NewsSource(String name) {
        this.name = name;
        this.articles = new ArrayList<>();
    }

    public void addArticle(NewsArticle article) {
        articles.add(article);
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }

    public String getName() {
        return name;
    }
}

class NewsAggregator {
    private List<NewsSource> sources;
    private List<String> filters;

    public NewsAggregator() {
        this.sources = new ArrayList<>();
        this.filters = new ArrayList<>();
    }

    public void addSource(NewsSource source) {
        sources.add(source);
    }

    public void addFilter(String filter) {
        filters.add(filter);
    }

    public List<NewsArticle> getFilteredArticles() {
        List<NewsArticle> filteredArticles = new ArrayList<>();
        for (NewsSource source : sources) {
            for (NewsArticle article : source.getArticles()) {
                if (matchesFilters(article)) {
                    filteredArticles.add(article);
                }
            }
        }
        return filteredArticles;
    }

    private boolean matchesFilters(NewsArticle article) {
        for (String filter : filters) {
            if (!article.getSource().toLowerCase().contains(filter.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}

import java.util.Scanner;

public class NewsAggregatorApp {
    public static void main(String[] args) {
        NewsAggregator aggregator = new NewsAggregator();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter news source name (type 'done' to finish):");
        String sourceName = scanner.nextLine();
        while (!sourceName.equalsIgnoreCase("done")) {
            NewsSource source = new NewsSource(sourceName);
            aggregator.addSource(source);

            System.out.println("Enter article title (type 'done' to finish):");
            String title = scanner.nextLine();
            while (!title.equalsIgnoreCase("done")) {
                System.out.println("Enter article content:");
                String content = scanner.nextLine();
                System.out.println("Enter article source:");
                String sourceStr = scanner.nextLine();
                System.out.println("Enter article publish date:");
                String publishDate = scanner.nextLine();

                source.addArticle(new NewsArticle(title, content, sourceStr, publishDate));

                title = scanner.nextLine();
            }

            System.out.println("Enter news source name (type 'done' to finish):");
            sourceName = scanner.nextLine();
        }

        System.out.println("Enter filter (type 'done' to finish):");
        String filter = scanner.nextLine();
        while (!filter.equalsIgnoreCase("done")) {
            aggregator.addFilter(filter);
            filter = scanner.nextLine();
        }

        List<NewsArticle> filteredArticles = aggregator.getFilteredArticles();
        for (NewsArticle article : filteredArticles) {
            System.out.println("Title: " + article.getTitle());
            System.out.println("Content: " + article.getContent());
            System.out.println("Source: " + article.getSource());
            System.out.println("Publish Date: " + article.getPublishDate());
            System.out.println("------------------------");
        }
    }
}
```