```java
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

class DocumentIndexer {
    private Map<String, Set<Integer>> index = new HashMap<>();

    public void indexDocuments(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String content = new String(Files.readAllBytes(Paths.get(files[i].getPath())));
                String[] words = content.toLowerCase().split("\\W+");
                for (String word : words) {
                    index.computeIfAbsent(word, k -> new HashSet<>()).add(i);
                }
            }
        }
    }

    public Set<Integer> search(String query) {
        String[] words = query.toLowerCase().split("\\W+");
        Set<Integer> result = new HashSet<>();
        if (words.length > 0) {
            result.addAll(index.getOrDefault(words[0], Set.of()));
            for (int i = 1; i < words.length; i++) {
                result.retainAll(index.getOrDefault(words[i], Set.of()));
            }
        }
        return result;
    }
}

public class SearchEngine {
    public static void main(String[] args) {
        DocumentIndexer indexer = new DocumentIndexer();
        try {
            indexer.indexDocuments("documents");
            Set<Integer> results = indexer.search("example query");
            System.out.println("Documents containing all query words: " + results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```