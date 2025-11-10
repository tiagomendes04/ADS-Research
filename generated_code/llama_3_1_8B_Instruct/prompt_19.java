```java
import java.util.*;

public class DocumentIndexer {
    private Map<String, Set<String>> index;
    private Map<String, Document> documents;

    public DocumentIndexer() {
        index = new HashMap<>();
        documents = new HashMap<>();
    }

    public void addDocument(String id, String title, String content) {
        Document document = new Document(title, content);
        documents.put(id, document);
        indexWords(id, title, content);
    }

    public void indexWords(String id, String title, String content) {
        String[] words = content.toLowerCase().split("\\s+");
        for (String word : words) {
            index.computeIfAbsent(word, k -> new HashSet<>()).add(id);
        }
        String[] titleWords = title.toLowerCase().split("\\s+");
        for (String word : titleWords) {
            index.computeIfAbsent(word, k -> new HashSet<>()).add(id);
        }
    }

    public List<String> search(String query) {
        Set<String> matchingIds = new HashSet<>();
        String[] words = query.toLowerCase().split("\\s+");
        for (String word : words) {
            matchingIds.addAll(index.getOrDefault(word, Collections.emptySet()));
        }
        return new ArrayList<>(matchingIds);
    }

    public static class Document {
        private String title;
        private String content;

        public Document(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }
}
```

```java
import java.util.*;

public class SearchEngine {
    private DocumentIndexer indexer;

    public SearchEngine() {
        indexer = new DocumentIndexer();
    }

    public void addDocument(String id, String title, String content) {
        indexer.addDocument(id, title, content);
    }

    public List<String> search(String query) {
        return indexer.search(query);
    }

    public Document getDocument(String id) {
        return indexer.documents.get(id);
    }
}
```

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SearchEngine engine = new SearchEngine();

        engine.addDocument("doc1", "Document 1", "This is a sample document.");
        engine.addDocument("doc2", "Document 2", "This is another sample document.");
        engine.addDocument("doc3", "Document 3", "This is yet another sample document.");

        System.out.println("Documents matching query 'sample':");
        List<String> ids = engine.search("sample");
        for (String id : ids) {
            System.out.println(engine.getDocument(id).getTitle());
        }
    }
}
```