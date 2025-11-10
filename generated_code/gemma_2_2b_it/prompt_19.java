```java
import java.util.*;

class Document {
    String text;
    int id;

    public Document(String text, int id) {
        this.text = text;
        this.id = id;
    }
}

class Index {
    Map<String, List<Document>> invertedIndex = new HashMap<>();
    
    public void index(Document document) {
        String[] words = document.text.toLowerCase().split("\\s+");
        for (String word : words) {
            List<Document> docs = invertedIndex.getOrDefault(word, new ArrayList<>());
            docs.add(document);
            invertedIndex.put(word, docs);
        }
    }

    public List<Document> search(String query) {
        List<Document> results = new ArrayList<>();
        for (String word : query.toLowerCase().split("\\s+")) {
            List<Document> docs = invertedIndex.get(word);
            if (docs != null) {
                results.addAll(docs);
            }
        }
        return results;
    }
}

public class SearchEngine {
    public static void main(String[] args) {
        Index index = new Index();
        index.index(new Document("This is a sample document.", 1));
        index.index(new Document("This is another sample document.", 2));

        List<Document> results = index.search("sample");
        System.out.println(results);
    }
}
```