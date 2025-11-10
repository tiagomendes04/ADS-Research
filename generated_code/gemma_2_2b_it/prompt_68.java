```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsAggregator {

    static List<Source> sources = new ArrayList<>();
    static List<Article> articles = new ArrayList<>();

    public static void main(String[] args) {
        sources.add(new Source("BBC", "uk"));
        sources.add(new Source("CNN", "us"));
        sources.add(new Source("The Guardian", "uk"));
        sources.add(new Source("The New York Times", "us"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add source");
            System.out.println("2. Add article");
            System.out.println("3. Filter articles");
            System.out.println("4. Get notifications");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addSource(scanner);
                    break;
                case 2:
                    addArticle(scanner);
                    break;
                case 3:
                    filterArticles(scanner);
                    break;
                case 4:
                    getNotifications();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addSource(Scanner scanner) {
        System.out.println("Enter source name:");
        String name = scanner.next();
        System.out.println("Enter source country:");
        String country = scanner.next();
        sources.add(new Source(name, country));
    }

    static void addArticle(Scanner scanner) {
        System.out.println("Enter article title:");
        String title = scanner.next();
        System.out.println("Enter article source:");
        String source = scanner.next();
        System.out.println("Enter article text:");
        String text = scanner.next();
        articles.add(new Article(title, source, text));
    }

    static void filterArticles(Scanner scanner) {
        System.out.println("Enter filter criteria:");
        System.out.println("1. Source");
        System.out.println("2. Country");
        System.out.println("3. Keywords");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter source name:");
                String sourceName = scanner.next();
                filterBySource(sourceName);
                break;
            case 2:
                System.out.println("Enter country:");
                String country = scanner.next();
                filterByCountry(country);
                break;
            case 3:
                System.out.println("Enter keywords:");
                String keywords = scanner.next();
                filterByKeywords(keywords);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    static void getNotifications() {
        System.out.println("Retrieving notifications...");
    }

    static void filterBySource(String source) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articles) {
            if (article.getSource().equals(source)) {
                filteredArticles.add(article);
            }
        }
        System.out.println("Articles from source " + source + ":");
        for (Article article : filteredArticles) {
            System.out.println(article.getTitle() + " by " + article.getSource() + " - " + article.getText());
        }
    }

    static void filterByCountry(String country) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articles) {
            if (article.getSource().contains(country)) {
                filteredArticles.add(article);
            }
        }
        System.out.println("Articles from country " + country + ":");
        for (Article article : filteredArticles) {
            System.out.println(article.getTitle() + " by " + article.getSource() + " - " + article.getText());
        }
    }

    static void filterByKeywords(String keywords) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articles) {
            if (article.getText().toLowerCase().contains(keywords.toLowerCase())) {
                filteredArticles.add(article);
            }
        }
        System.out.println("Articles containing keywords " + keywords + ":");
        for (Article article : filteredArticles) {
            System.out.println(article.getTitle() + " by " + article.getSource() + " - " + article.getText());
        }
    }
}

class Source {
    String name;
    String country;

    Source(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}

class Article {
    String title;
    Source source;
    String text;

    Article(String title, Source source, String text) {
        this.title = title;
        this.source = source;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public Source getSource() {
        return source;
    }

    public String getText() {
        return text;
    }
}
```