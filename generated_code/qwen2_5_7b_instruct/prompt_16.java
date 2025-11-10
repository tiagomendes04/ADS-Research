```java
import java.util.Stack;

public class TextBuffer {
    private String text;
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public TextBuffer() {
        text = "";
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void insertText(int position, String text) {
        this.text = insertAt(text, position, this.text);
        undoStack.push(this.text);
        redoStack.clear();
    }

    public void deleteText(int start, int end) {
        String deletedText = this.text.substring(start, end);
        this.text = deleteRange(start, end, this.text);
        undoStack.push(this.text);
        redoStack.clear();
    }

    public String getText() {
        return text;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(this.text);
            this.text = undoStack.pop();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(this.text);
            this.text = redoStack.pop();
        }
    }

    private String insertAt(String textToInsert, int position, String originalText) {
        return originalText.substring(0, position) + textToInsert + originalText.substring(position);
    }

    private String deleteRange(int start, int end, String originalText) {
        return originalText.substring(0, start) + originalText.substring(end);
    }
}
```