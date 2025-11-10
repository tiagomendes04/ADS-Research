```java
import java.io.*;
import java.util.*;

public class BasicSearchEngine {
    private Map<String, List<Document>> index;

    public BasicSearchEngine() {
        index = new HashMap<>();
    }

    public void addDocument(String id, String content) {
        String[] words = content.toLowerCase().split("\\W+");
        for (String word : words) {
            if (!word.isEmpty()) {
                if (!index.containsKey(word)) {
                    index.put(word, new ArrayList<>());
                }
                index.get(word).add(new Document(id, content));
            }
        }
    }

    public List<Document> query(String keyword) {
        if (!index.containsKey(keyword.toLowerCase())) {
            return new ArrayList<>();
        }
        return index.get(keyword.toLowerCase());
    }

    public static void main(String[] args) {
        BasicSearchEngine engine = new BasicSearchEngine();
        engine.addDocument("doc1", "This is the first document.");
        engine.addDocument("doc2", "This document is the second document.");
        engine.addDocument("doc3", "And this is the third one.");
        engine.addDocument("doc4", "Is this the first document?");
        
        System.out.println("Querying for 'document': " + engine.query("document"));
        System.out.println("Querying for 'first': " + engine.query("first"));
    }
    
    static class Document {
        private String id;
        private String content;

        public Document(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Document ID: " + id + ", Content: " + content;
        }
    }
}
```