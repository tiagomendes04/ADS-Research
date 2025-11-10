```java
import java.util.*;

class CodeSnippet {
    private String title;
    private String content;
    private Set<String> tags;

    public CodeSnippet(String title, String content) {
        this.title = title;
        this.content = content;
        this.tags = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                '}';
    }
}

class CodeSnippetManager {
    private Map<String, CodeSnippet> snippets = new HashMap<>();

    public void addSnippet(CodeSnippet snippet) {
        snippets.put(snippet.getTitle(), snippet);
    }

    public void addTagToSnippet(String title, String tag) {
        CodeSnippet snippet = snippets.get(title);
        if (snippet != null) {
            snippet.addTag(tag);
        }
    }

    public List<CodeSnippet> searchByTag(String tag) {
        List<CodeSnippet> result = new ArrayList<>();
        for (CodeSnippet snippet : snippets.values()) {
            if (snippet.getTags().contains(tag)) {
                result.add(snippet);
            }
        }
        return result;
    }

    public List<CodeSnippet> searchByTitle(String title) {
        return new ArrayList<>(Collections.singleton(snippets.get(title)));
    }
}

public class Main {
    public static void main(String[] args) {
        CodeSnippetManager manager = new CodeSnippetManager();

        CodeSnippet snippet1 = new CodeSnippet("Java Basics", "System.out.println('Hello, World!');");
        CodeSnippet snippet2 = new CodeSnippet("Array Manipulation", "int[] arr = new int[5];");
        CodeSnippet snippet3 = new CodeSnippet("Loop Example", "for (int i = 0; i < 10; i++) {}");

        manager.addSnippet(snippet1);
        manager.addSnippet(snippet2);
        manager.addSnippet(snippet3);

        manager.addTagToSnippet("Java Basics", "java");
        manager.addTagToSnippet("Array Manipulation", "arrays");
        manager.addTagToSnippet("Loop Example", "loops");

        System.out.println(manager.searchByTag("java"));
        System.out.println(manager.searchByTitle("Array Manipulation"));
    }
}
```