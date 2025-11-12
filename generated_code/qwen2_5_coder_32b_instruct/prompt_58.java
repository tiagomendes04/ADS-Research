```java
import java.util.*;
import java.util.stream.Collectors;

class CodeSnippet {
    private String code;
    private Set<String> tags;

    public CodeSnippet(String code, Set<String> tags) {
        this.code = code;
        this.tags = tags;
    }

    public String getCode() {
        return code;
    }

    public Set<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "code='" + code + '\'' +
                ", tags=" + tags +
                '}';
    }
}

class CodeSnippetManager {
    private List<CodeSnippet> snippets;

    public CodeSnippetManager() {
        this.snippets = new ArrayList<>();
    }

    public void addSnippet(CodeSnippet snippet) {
        snippets.add(snippet);
    }

    public List<CodeSnippet> searchByTag(String tag) {
        return snippets.stream()
                .filter(snippet -> snippet.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public List<CodeSnippet> searchByCode(String keyword) {
        return snippets.stream()
                .filter(snippet -> snippet.getCode().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<CodeSnippet> getAllSnippets() {
        return snippets;
    }
}

public class Main {
    public static void main(String[] args) {
        CodeSnippetManager manager = new CodeSnippetManager();

        Set<String> tags1 = new HashSet<>(Arrays.asList("java", "collections"));
        CodeSnippet snippet1 = new CodeSnippet("List<String> list = new ArrayList<>();", tags1);
        manager.addSnippet(snippet1);

        Set<String> tags2 = new HashSet<>(Arrays.asList("java", "stream"));
        CodeSnippet snippet2 = new CodeSnippet("List<String> filtered = list.stream().filter(s -> s.contains(\"a\")).collect(Collectors.toList());", tags2);
        manager.addSnippet(snippet2);

        Set<String> tags3 = new HashSet<>(Arrays.asList("python", "loop"));
        CodeSnippet snippet3 = new CodeSnippet("for i in range(10):\n    print(i)", tags3);
        manager.addSnippet(snippet3);

        System.out.println("Search by tag 'java':");
        manager.searchByTag("java").forEach(System.out::println);

        System.out.println("\nSearch by code 'stream':");
        manager.searchByCode("stream").forEach(System.out::println);

        System.out.println("\nAll snippets:");
        manager.getAllSnippets().forEach(System.out::println);
    }
}
```