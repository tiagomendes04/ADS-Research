```java
import java.util.ArrayList;
import java.util.List;

public class TextBuffer {

    private List<String> history = new ArrayList<>();
    private String currentContent;

    public void insert(String text) {
        currentContent += text;
        history.add(currentContent);
    }

    public void undo() {
        if (history.size() > 1) {
            currentContent = history.get(history.size() - 2);
            history.remove(history.size() - 1);
        }
    }

    public void redo() {
        if (history.size() > 1) {
            currentContent = history.get(history.size() - 1);
            history.add(history.size() - 2);
        }
    }

    public String getContent() {
        return currentContent;
    }
}
```