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