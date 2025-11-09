```java
import java.util.ArrayList;
import java.util.List;

class TextBuffer {
    private List<String> history;
    private List<String> redoHistory;
    private String currentText;
    private int currentIndex;

    public TextBuffer() {
        this.history = new ArrayList<>();
        this.redoHistory = new ArrayList<>();
        this.currentText = "";
        this.currentIndex = -1;
    }

    public void insertText(String text) {
        currentText += text;
        history.add(currentText);
        redoHistory.clear();
        currentIndex++;
    }

    public void deleteText(int start, int end) {
        String deletedText = currentText.substring(start, end);
        currentText = currentText.substring(0, start) + currentText.substring(end);
        history.add(currentText);
        redoHistory.clear();
        currentIndex++;
    }

    public void undo() {
        if (currentIndex > 0) {
            currentIndex--;
            currentText = history.get(currentIndex);
            redoHistory.add(currentText);
        }
    }

    public void redo() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            currentText = history.get(currentIndex);
            redoHistory.remove(redoHistory.size() - 1);
        }
    }

    public String getText() {
        return currentText;
    }
}

public class Main {
    public static void main(String[] args) {
        TextBuffer buffer = new TextBuffer();
        buffer.insertText("Hello, ");
        buffer.insertText("world!");
        System.out.println(buffer.getText()); // prints "Hello, world!"

        buffer.deleteText(7, 12);
        System.out.println(buffer.getText()); // prints "Hello, "

        buffer.undo();
        System.out.println(buffer.getText()); // prints "Hello, world!"

        buffer.redo();
        System.out.println(buffer.getText()); // prints "Hello, "
    }
}
```