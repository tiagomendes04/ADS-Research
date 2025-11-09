```java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SearchEngine {

    private Map<String, Set<String>> index = new HashMap<>();
    private List<String> documentNames = new ArrayList<>();

    public void indexDocument(String documentPath) throws IOException {
        String documentName = new File(documentPath).getName();
        documentNames.add(documentName);
        int documentId = documentNames.size() - 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(documentPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z0-9]", "");
                    if (!word.isEmpty()) {
                        if (!index.containsKey(word)) {
                            index.put(word, new HashSet<>());
                        }
                        index.get(word).add(documentName);
                    }
                }
            }
        }
    }

    public List<String> search(String query) {
        String[] queryTerms = query.toLowerCase().split("\\s+");
        Set<String> results = null;

        for (String term : queryTerms) {
            term = term.replaceAll("[^a-zA-Z0-9]", "");
            if (!term.isEmpty()) {
                Set<String> documents = index.get(term);
                if (documents != null) {
                    if (results == null) {
                        results = new HashSet<>(documents);
                    } else {
                        results.retainAll(documents); // Intersection for AND search
                    }
                } else {
                    return new ArrayList<>(); // If any term not found, return empty list for AND search
                }
            }
        }

        if (results == null) {
            return new ArrayList<>(); // No matching documents
        }

        return new ArrayList<>(results);
    }

    public static void main(String[] args) throws IOException {
        SearchEngine engine = new SearchEngine();

        // Create some dummy documents
        String doc1 = "document1.txt";
        String doc2 = "document2.txt";
        String doc3 = "document3.txt";

        try {
            new File(doc1).createNewFile();
            new File(doc2).createNewFile();
            new File(doc3).createNewFile();

            java.nio.file.Files.write(java.nio.file.Paths.get(doc1), "This is document one. It contains words".getBytes());
            java.nio.file.Files.write(java.nio.file.Paths.get(doc2), "This is another document. It has different words".getBytes());
            java.nio.file.Files.write(java.nio.file.Paths.get(doc3), "Document three is here with some words from one".getBytes());

        } catch (IOException e) {
            System.err.println("Error creating dummy documents: " + e.getMessage());
            return;
        }


        engine.indexDocument(doc1);
        engine.indexDocument(doc2);
        engine.indexDocument(doc3);

        String query = "document words";
        List<String> searchResults = engine.search(query);

        System.out.println("Search results for '" + query + "': " + searchResults);
    }
}
```