```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CodeSnippet {
    private String id;
    private String code;
    private List<String> tags;

    public CodeSnippet(String id, String code, List<String> tags) {
        this.id = id;
        this.code = code;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public List<String> getTags() {
        return tags;
    }
}

public class CodeSnippetManager {
    private List<CodeSnippet> snippets;

    public CodeSnippetManager() {
        this.snippets = new ArrayList<>();
    }

    public void addSnippet(String id, String code, List<String> tags) {
        CodeSnippet snippet = new CodeSnippet(id, code, tags);
        snippets.add(snippet);
    }

    public void deleteSnippet(String id) {
        snippets.removeIf(snippet -> snippet.getId().equals(id));
    }

    public void updateSnippet(String id, String newCode, List<String> newTags) {
        for (CodeSnippet snippet : snippets) {
            if (snippet.getId().equals(id)) {
                snippet.setCode(newCode);
                snippet.setTags(newTags);
                return;
            }
        }
    }

    public List<CodeSnippet> searchByTag(String tag) {
        List<CodeSnippet> result = new ArrayList<>();
        for (CodeSnippet snippet : snippets) {
            if (snippet.getTags().contains(tag)) {
                result.add(snippet);
            }
        }
        return result;
    }

    public List<CodeSnippet> searchByCode(String query) {
        List<CodeSnippet> result = new ArrayList<>();
        for (CodeSnippet snippet : snippets) {
            if (snippet.getCode().contains(query)) {
                result.add(snippet);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CodeSnippetManager manager = new CodeSnippetManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add snippet");
            System.out.println("2. Delete snippet");
            System.out.println("3. Update snippet");
            System.out.println("4. Search by tag");
            System.out.println("5. Search by code");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1: {
                    System.out.print("Enter snippet ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter code: ");
                    String code = scanner.nextLine();
                    System.out.print("Enter tags (comma-separated): ");
                    String[] tags = scanner.nextLine().split(",");
                    List<String> tagList = new ArrayList<>();
                    for (String tag : tags) {
                        tagList.add(tag.trim());
                    }
                    manager.addSnippet(id, code, tagList);
                    break;
                }
                case 2: {
                    System.out.print("Enter snippet ID to delete: ");
                    String id = scanner.nextLine();
                    manager.deleteSnippet(id);
                    break;
                }
                case 3: {
                    System.out.print("Enter snippet ID to update: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter new code: ");
                    String newCode = scanner.nextLine();
                    System.out.print("Enter new tags (comma-separated): ");
                    String[] newTags = scanner.nextLine().split(",");
                    List<String> newTagList = new ArrayList<>();
                    for (String tag : newTags) {
                        newTagList.add(tag.trim());
                    }
                    manager.updateSnippet(id, newCode, newTagList);
                    break;
                }
                case 4: {
                    System.out.print("Enter tag to search: ");
                    String tag = scanner.nextLine();
                    List<CodeSnippet> result = manager.searchByTag(tag);
                    for (CodeSnippet snippet : result) {
                        System.out.println("ID: " + snippet.getId() + ", Code: " + snippet.getCode() + ", Tags: " + snippet.getTags());
                    }
                    break;
                }
                case 5: {
                    System.out.print("Enter code to search: ");
                    String query = scanner.nextLine();
                    List<CodeSnippet> result = manager.searchByCode(query);
                    for (CodeSnippet snippet : result) {
                        System.out.println("ID: " + snippet.getId() + ", Code: " + snippet.getCode() + ", Tags: " + snippet.getTags());
                    }
                    break;
                }
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
```