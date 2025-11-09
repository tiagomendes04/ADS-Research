import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DocumentSearchEngine {
    private Map<String, List<Integer>> index;
    private List<String> documents;

    public DocumentSearchEngine() {
        index = new HashMap<>();
        documents = new ArrayList<>();
    }

    public void addDocument(String document) {
        documents.add(document);
        String[] words = document.toLowerCase().split("\\W+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                index.computeIfAbsent(word, k -> new ArrayList<>()).add(documents.size() - 1);
            }
        }
    }

    public List<Integer> search(String query) {
        String[] queryWords = query.toLowerCase().split("\\W+");
        Set<Integer> result = new HashSet<>(documents.size());
        if (queryWords.length == 0) {
            return new ArrayList<>();
        }

        // Get documents containing all query words
        for (String word : queryWords) {
            if (index.containsKey(word)) {
                if (result.isEmpty()) {
                    result.addAll(index.get(word));
                } else {
                    result.retainAll(index.get(word));
                }
            } else {
                return new ArrayList<>();
            }
        }

        return new ArrayList<>(result);
    }

    public String getDocument(int docId) {
        if (docId >= 0 && docId < documents.size()) {
            return documents.get(docId);
        }
        return null;
    }

    public static void main(String[] args) {
        DocumentSearchEngine engine = new DocumentSearchEngine();

        // Add some documents
        engine.addDocument("The quick brown fox jumps over the lazy dog");
        engine.addDocument("A quick brown dog outpaces a fast fox");
        engine.addDocument("The lazy dog sleeps all day");

        // Search for documents
        List<Integer> results = engine.search("quick fox");
        System.out.println("Documents containing 'quick fox':");
        for (int docId : results) {
            System.out.println(engine.getDocument(docId));
        }

        results = engine.search("lazy dog");
        System.out.println("\nDocuments containing 'lazy dog':");
        for (int docId : results) {
            System.out.println(engine.getDocument(docId));
        }
    }
}