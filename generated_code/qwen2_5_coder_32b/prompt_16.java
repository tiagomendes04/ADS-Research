```java
import java.util.ArrayDeque;
import java.util.Deque;

public class TextBuffer {
    private StringBuilder buffer;
    private Deque<String> undoStack;
    private Deque<String> redoStack;

    public TextBuffer() {
        this.buffer = new StringBuilder();
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }

    public void insert(int position, String text) {
        undoStack.push(buffer.toString());
        redoStack.clear();
        buffer.insert(position, text);
    }

    public void delete(int start, int end) {
        undoStack.push(buffer.toString());
        redoStack.clear();
        buffer.delete(start, end);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(buffer.toString());
            buffer.replace(0, buffer.length(), undoStack.pop());
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(buffer.toString());
            buffer.replace(0, buffer.length(), redoStack.pop());
        }
    }

    public String getText() {
        return buffer.toString();
    }

    public static void main(String[] args) {
        TextBuffer textBuffer = new TextBuffer();
        textBuffer.insert(0, "Hello");
        textBuffer.insert(5, " World");
        System.out.println(textBuffer.getText()); // Hello World
        textBuffer.undo();
        System.out.println(textBuffer.getText()); // Hello
        textBuffer.redo();
        System.out.println(textBuffer.getText()); // Hello World
        textBuffer.delete(5, 11);
        System.out.println(textBuffer.getText()); // Hello
        textBuffer.undo();
        System.out.println(textBuffer.getText()); // Hello World
        textBuffer.redo();
        System.out.println(textBuffer.getText()); // Hello
    }
}
```