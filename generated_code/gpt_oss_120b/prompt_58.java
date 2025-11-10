```java
// File: Snippet.java
import java.util.HashSet;
import java.util.Set;

public class Snippet {
    private static long counter = 0;
    private final long id;
    private String title;
    private String description;
    private String code;
    private final Set<String> tags = new HashSet<>();

    public Snippet(String title, String description, String code, Set<String> tags) {
        this.id = ++counter;
        this.title = title;
        this.description = description;
        this.code = code;
        if (tags != null) this.tags.addAll(tags);
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCode() { return code; }
    public Set<String> getTags() { return tags; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCode(String code) { this.code = code; }

    public void addTag(String tag) { tags.add(tag.toLowerCase()); }
    public void removeTag(String tag) { tags.remove(tag.toLowerCase()); }

    @Override
    public String toString() {
        return String.format(
                "ID: %d%nTitle: %s%nDescription: %s%nTags: %s%nCode:%n%s%n",
                id, title, description, tags, code);
    }
}
```

```java
// File: SnippetManager.java
import java.util.*;
import java.util.stream.Collectors;

public class SnippetManager {
    private final Map<Long, Snippet> snippets = new HashMap<>();

    public Snippet addSnippet(String title, String description, String code, Set<String> tags) {
        Snippet s = new Snippet(title, description, code, tags);
        snippets.put(s.getId(), s);
        return s;
    }

    public boolean deleteSnippet(long id) {
        return snippets.remove(id) != null;
    }

    public Snippet getSnippet(long id) {
        return snippets.get(id);
    }

    public List<Snippet> searchByKeyword(String keyword) {
        String kw = keyword.toLowerCase();
        return snippets.values().stream()
                .filter(s -> s.getTitle().toLowerCase().contains(kw) ||
                             s.getDescription().toLowerCase().contains(kw) ||
                             s.getCode().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    public List<Snippet> searchByTag(String tag) {
        String t = tag.toLowerCase();
        return snippets.values().stream()
                .filter(s -> s.getTags().contains(t))
                .collect(Collectors.toList());
    }

    public Collection<Snippet> getAllSnippets() {
        return snippets.values();
    }
}
```

```java
// File: Main.java
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SnippetManager manager = new SnippetManager();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addSnippet(); break;
                case "2": listAll(); break;
                case "3": viewSnippet(); break;
                case "4": deleteSnippet(); break;
                case "5": searchByKeyword(); break;
                case "6": searchByTag(); break;
                case "0": System.out.println("Bye!"); return;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Code Snippet Manager ---");
        System.out.println("1. Add Snippet");
        System.out.println("2. List All Snippets");
        System.out.println("3. View Snippet Details");
        System.out.println("4. Delete Snippet");
        System.out.println("5. Search by Keyword");
        System.out.println("6. Search by Tag");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private static void addSnippet() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.println("Enter code (end with a single line containing only 'END'):");
        StringBuilder codeBuilder = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("END")) break;
            codeBuilder.append(line).append(System.lineSeparator());
        }
        System.out.print("Tags (comma separated): ");
        String tagsLine = scanner.nextLine();
        Set<String> tags = new HashSet<>();
        if (!tagsLine.trim().isEmpty()) {
            for (String t : tagsLine.split(",")) tags.add(t.trim().toLowerCase());
        }
        Snippet s = manager.addSnippet(title, desc, codeBuilder.toString(), tags);
        System.out.println("Snippet added with ID: " + s.getId());
    }

    private static void listAll() {
        Collection<Snippet> all = manager.getAllSnippets();
        if (all.isEmpty()) {
            System.out.println("No snippets stored.");
            return;
        }
        System.out.println("\n--- All Snippets ---");
        for (Snippet s : all) {
            System.out.printf("ID: %d | Title: %s | Tags: %s%n",
                    s.getId(), s.getTitle(), s.getTags());
        }
    }

    private static void viewSnippet() {
        System.out.print("Enter snippet ID: ");
        long id = Long.parseLong(scanner.nextLine());
        Snippet s = manager.getSnippet(id);
        if (s == null) {
            System.out.println("Snippet not found.");
        } else {
            System.out.println("\n--- Snippet Details ---");
            System.out.println(s);
        }
    }

    private static void deleteSnippet() {
        System.out.print("Enter snippet ID to delete: ");
        long id = Long.parseLong(scanner.nextLine());
        if (manager.deleteSnippet(id)) {
            System.out.println("Snippet deleted.");
        } else {
            System.out.println("Snippet not found.");
        }
    }

    private static void searchByKeyword() {
        System.out.print("Enter keyword: ");
        String kw = scanner.nextLine();
        List<Snippet> results = manager.searchByKeyword(kw);
        displaySearchResults(results);
    }

    private static void searchByTag() {
        System.out.print("Enter tag: ");
        String tag = scanner.nextLine();
        List<Snippet> results = manager.searchByTag(tag);
        displaySearchResults(results);
    }

    private static void displaySearchResults(List<Snippet> results) {
        if (results.isEmpty()) {
            System.out.println("No matching snippets.");
            return;
        }
        System.out.println("\n--- Search Results ---");
        for (Snippet s : results) {
            System.out.printf("ID: %d | Title: %s | Tags: %s%n",
                    s.getId(), s.getTitle(), s.getTags());
        }
    }
}
```