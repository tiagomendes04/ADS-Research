```java
import java.util.ArrayList;
import java.util.List;

public class TextBuffer {
    private List<String> buffer;
    private int currentPos;
    private int redoCount;
    private int undoCount;

    public TextBuffer() {
        buffer = new ArrayList<>();
        currentPos = 0;
        redoCount = 0;
        undoCount = 0;
    }

    public void add(String text) {
        buffer.add(text);
    }

    public String get(int pos) {
        return buffer.get(currentPos + pos);
    }

    public void undo() {
        if (redoCount > 0) {
            redoCount--;
            currentPos = currentPos - 1;
            System.out.println("Undo: " + get(currentPos));
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void redo() {
        if (undoCount > 0) {
            undoCount--;
            currentPos = currentPos + 1;
            System.out.println("Redo: " + get(currentPos));
        } else {
            System.out.println("Nothing to redo.");
        }
    }

    public void add(int pos, String text) {
        if (pos < 0) {
            pos = 0;
        }
        if (pos + text.length() > buffer.size()) {
            text = "..." + text;
        }
        add(text);
        System.out.println("Added text to buffer.");
    }
}
```