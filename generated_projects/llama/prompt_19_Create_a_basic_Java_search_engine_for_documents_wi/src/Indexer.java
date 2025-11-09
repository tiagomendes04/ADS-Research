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