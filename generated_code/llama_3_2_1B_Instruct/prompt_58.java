```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CodeSnippetManager {

    public static void main(String[] args) {
        Map<String, Map<String, String>> codeSnippets = new HashMap<>();
        codeSnippets.put("snippet1", Map.of("tags", "java", "description", "This is a snippet of Java code"));
        codeSnippets.put("snippet2", Map.of("tags", "python", "description", "This is another snippet of Python code"));
        codeSnippets.put("snippet3", Map.of("tags", "java", "description", "This is yet another snippet of Java code"));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add a snippet");
            System.out.println("2. Search snippets by tags");
            System.out.println("3. Search snippets by description");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter the snippet name: ");
                    String snippetName = scanner.nextLine();
                    codeSnippets.get(snippetName).put("tags", scanner.nextLine());
                    codeSnippets.get(snippetName).put("description", scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter the tags to search: ");
                    String tags = scanner.nextLine();
                    Map<String, String> result = new HashMap<>();
                    for (Map.Entry<String, Map<String, String>> entry : codeSnippets.entrySet()) {
                        if (entry.getValue().containsKey(tags)) {
                            result.put(entry.getKey(), entry.getValue().get(tags));
                        }
                    }
                    System.out.println(result);
                    break;
                case 3:
                    System.out.print("Enter the description to search: ");
                    String description = scanner.nextLine();
                    Map<String, String> result = new HashMap<>();
                    for (Map.Entry<String, Map<String, String>> entry : codeSnippets.entrySet()) {
                        if (entry.getValue().containsKey(description)) {
                            result.put(entry.getKey(), entry.getValue().get(description));
                        }
                    }
                    System.out.println(result);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
```