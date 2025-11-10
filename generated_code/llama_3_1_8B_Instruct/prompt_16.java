```java
import java.util.ArrayList;
import java.util.List;

public class TextBuffer {
    private List<String> history;
    private int currentPosition;

    public TextBuffer() {
        this.history = new ArrayList<>();
        this.currentPosition = -1;
    }

    public void insertText(String text) {
        if (currentPosition == history.size() - 1) {
            history.set(currentPosition + 1, history.get(currentPosition) + text);
        } else {
            history.add(currentPosition + 1, history.get(currentPosition) + text);
        }
        currentPosition++;
    }

    public void deleteText(int length) {
        if (currentPosition == -1) {
            return;
        }
        history.set(currentPosition, history.get(currentPosition).substring(0, history.get(currentPosition).length() - length));
    }

    public void undo() {
        if (currentPosition > 0) {
            currentPosition--;
        }
    }

    public void redo() {
        if (currentPosition < history.size() - 1) {
            currentPosition++;
        }
    }

    public String getText() {
        if (currentPosition == -1) {
            return "";
        }
        return history.get(currentPosition);
    }

    public void clear() {
        history.clear();
        currentPosition = -1;
    }

    public static void main(String[] args) {
        TextBuffer buffer = new TextBuffer();
        buffer.insertText("Hello, ");
        buffer.insertText("world!");
        System.out.println(buffer.getText()); // Hello, world!
        buffer.deleteText(1);
        System.out.println(buffer.getText()); // Hello, orld!
        buffer.undo();
        System.out.println(buffer.getText()); // Hello, world!
        buffer.redo();
        System.out.println(buffer.getText()); // Hello, orld!
        buffer.clear();
        System.out.println(buffer.getText()); // ""
    }
}
```