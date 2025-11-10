```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CodeSnippetManager {

    static HashMap<String, ArrayList<String>> snippets = new HashMap<>();
    static HashMap<String, Integer> tagCounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a code snippet");
            System.out.println("2. Search for code snippets");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addSnippet(scanner);
                    break;
                case 2:
                    searchSnippet(scanner);
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addSnippet(Scanner scanner) {
        System.out.println("Enter snippet title:");
        String title = scanner.nextLine();

        System.out.println("Enter code snippet:");
        String code = scanner.nextLine();

        System.out.println("Enter tags (separated by commas):");
        String tags = scanner.nextLine();

        String[] tagArray = tags.split(",");

        for (String tag : tagArray) {
            if (tagCounts.containsKey(tag)) {
                tagCounts.put(tag, tagCounts.get(tag) + 1);
            } else {
                tagCounts.put(tag, 1);
            }
        }

        snippets.put(title, new ArrayList<>(Arrays.asList(code)));
    }

    static void searchSnippet(Scanner scanner) {
        System.out.println("Enter keywords (separated by commas):");
        String keywords = scanner.nextLine();

        String[] keywordArray = keywords.split(",");

        for (String keyword : keywordArray) {
            ArrayList<String> snippetsWithKeyword = snippets.get(keyword);

            if (snippetsWithKeyword != null) {
                System.out.println("Snippets with the keyword " + keyword + ":");
                for (String snippet : snippetsWithKeyword) {
                    System.out.println(snippet);
                }
            }
        }
    }
}
```