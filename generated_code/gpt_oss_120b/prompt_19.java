```java
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class SimpleSearchEngine {

    // Inverted index: term -> set of document IDs
    private final Map<String, Set<Integer>> index = new HashMap<>();
    // Document ID -> document content
    private final Map<Integer, String> documents = new HashMap<>();

    // Index all text files in a directory
    public void indexDirectory(String dirPath) throws IOException {
        List<Path> files = Files.walk(Paths.get(dirPath))
                .filter(p -> !Files.isDirectory(p) && p.toString().endsWith(".txt"))
                .collect(Collectors.toList());

        int docId = 0;
        for (Path file : files) {
            String content = new String(Files.readAllBytes(file));
            documents.put(docId, content);
            indexDocument(docId, content);
            docId++;
        }
    }

    // Tokenize and add terms to the inverted index
    private void indexDocument(int docId, String content) {
        String[] tokens = content.toLowerCase().split("\\W+");
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            index.computeIfAbsent(token, k -> new HashSet<>()).add(docId);
        }
    }

    // Simple AND query (all terms must appear)
    public List<String> query(String queryString) {
        String[] terms = queryString.toLowerCase().split("\\W+");
        Set<Integer> resultIds = null;

        for (String term : terms) {
            if (term.isEmpty()) continue;
            Set<Integer> ids = index.getOrDefault(term, Collections.emptySet());
            if (resultIds == null) {
                resultIds = new HashSet<>(ids);
            } else {
                resultIds.retainAll(ids);
            }
            if (resultIds.isEmpty()) break;
        }

        if (resultIds == null || resultIds.isEmpty()) {
            return Collections.emptyList();
        }

        return resultIds.stream()
                .map(documents::get)
                .collect(Collectors.toList());
    }

    // Demo usage
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java SimpleSearchEngine <docs_dir> <query>");
            return;
        }

        String docsDir = args[0];
        String query = args[1];

        SimpleSearchEngine engine = new SimpleSearchEngine();
        try {
            engine.indexDirectory(docsDir);
            List<String> results = engine.query(query);
            if (results.isEmpty()) {
                System.out.println("No documents found.");
            } else {
                System.out.println("Found " + results.size() + " document(s):");
                for (String doc : results) {
                    System.out.println("-----");
                    System.out.println(doc);
                }
            }
        } catch (IOException e) {
            System.err.println("Error indexing documents: " + e.getMessage());
        }
    }
}
```