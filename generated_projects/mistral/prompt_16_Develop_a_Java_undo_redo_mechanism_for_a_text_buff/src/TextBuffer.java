import java.util.Stack;

public class TextBuffer {
    private StringBuilder buffer;
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public TextBuffer() {
        this.buffer = new StringBuilder();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        undoStack.push(buffer.toString());
    }

    public void insert(int position, String text) {
        String currentState = buffer.toString();
        buffer.insert(position, text);
        undoStack.push(currentState);
        redoStack.clear();
    }

    public void delete(int start, int end) {
        String currentState = buffer.toString();
        buffer.delete(start, end);
        undoStack.push(currentState);
        redoStack.clear();
    }

    public void replace(int start, int end, String text) {
        String currentState = buffer.toString();
        buffer.replace(start, end, text);
        undoStack.push(currentState);
        redoStack.clear();
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

    public void clear() {
        buffer.setLength(0);
        undoStack.clear();
        redoStack.clear();
        undoStack.push(buffer.toString());
    }
}