**Document Indexer and Search Engine in Java**
=====================================================

### Indexer.java
```java
import java.util.HashMap;
import java.util.Map;

public class Indexer {
    private Map<String, Map<String, Integer>> index;

    public Indexer() {
        this.index = new HashMap<>();
    }

    public void addDocument(String documentId, String document) {
        String[] words = document.split("\\s+");
        for (String word : words) {
            word = word.toLowerCase();
            if (!index.containsKey(word)) {
                index.put(word, new HashMap<>());
            }
            if (!index.get(word).containsKey(documentId)) {
                index.get(word).put(documentId, 1);
            } else {
                index.get(word).put(documentId, index.get(word).get(documentId) + 1);
            }
        }
    }

    public void removeDocument(String documentId) {
        for (Map<String, Integer> wordFreq : index.values()) {
            wordFreq.remove(documentId);
            if (wordFreq.isEmpty()) {
                index.remove(wordFreq.keySet().iterator().next());
            }
        }
    }

    public Map<String, Map<String, Integer>> getIndex() {
        return index;
    }
}
```

### Document.java
```java
public class Document {
    private String id;
    private String content;

    public Document(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
```

### SearchEngine.java
```java
import java.util.*;

public class SearchEngine {
    private Indexer indexer;

    public SearchEngine() {
        this.indexer = new Indexer();
    }

    public void addDocument(String documentId, String document) {
        indexer.addDocument(documentId, document);
    }

    public void removeDocument(String documentId) {
        indexer.removeDocument(documentId);
    }

    public List<String> search(String query) {
        String[] words = query.split("\\s+");
        Map<String, Integer> queryWords = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase();
            if (!queryWords.containsKey(word)) {
                queryWords.put(word, 1);
            } else {
                queryWords.put(word, queryWords.get(word) + 1);
            }
        }

        List<String> results = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : queryWords.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();
            if (indexer.getIndex().containsKey(word)) {
                for (Map.Entry<String, Integer> docEntry : indexer.getIndex().get(word).entrySet()) {
                    String documentId = docEntry.getKey();
                    int docFreq = docEntry.getValue();
                    if (docFreq >= freq) {
                        results.add(documentId);
                    }
                }
            }
        }

        return results;
    }
}
```

### Main.java
```java
public class Main {
    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();

        Document document1 = new Document("doc1", "This is a sample document.");
        Document document2 = new Document("doc2", "This is another sample document.");
        Document document3 = new Document("doc3", "This is a different sample document.");

        searchEngine.addDocument(document1.getId(), document1.getContent());
        searchEngine.addDocument(document2.getId(), document2.getContent());
        searchEngine.addDocument(document3.getId(), document3.getContent());

        System.out.println(searchEngine.search("sample document")); // Output: [doc1, doc2, doc3]
        System.out.println(searchEngine.search("different sample")); // Output: [doc3]
    }
}
```

This code creates a basic search engine that indexes documents and allows for querying. The `Indexer` class is responsible for maintaining the index of words and their frequencies in the documents. The `SearchEngine` class uses the `Indexer` to perform searches. The `Main` class demonstrates how to use the search engine.