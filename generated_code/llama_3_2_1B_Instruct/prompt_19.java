```java
import java.util.*;

class Index implements Comparable<Index> {
    String word;
    int frequency;
    String index;

    public Index(String word, int frequency, String index) {
        this.word = word;
        this.frequency = frequency;
        this.index = index;
    }

    @Override
    public int compareTo(Index other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}

class Document {
    String text;
    List<Index> indices;

    public Document(String text) {
        this.text = text;
        this.indices = new ArrayList<>();
    }

    public void addIndex(Index index) {
        indices.add(index);
    }

    public void query(String query) {
        List<Index> result = new ArrayList<>();
        for (Index index : indices) {
            if (index.word.contains(query)) {
                result.add(index);
            }
        }
        Collections.sort(result);
        for (Index index : result) {
            System.out.println(index.word + ": " + index.frequency);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Document doc = new Document("This is a sample document.");
        doc.addIndex(new Index("this", 2, "doc1"));
        doc.addIndex(new Index("is", 1, "doc1"));
        doc.addIndex(new Index("a", 3, "doc1"));
        doc.addIndex(new Index("sample", 2, "doc1"));

        doc.query("this");
    }
}
```