import java.util.Stack;

public class TextBufferUndoRedo {

    private StringBuilder text;
    private Stack<TextMemento> undoStack;
    private Stack<TextMemento> redoStack;

    public TextBufferUndoRedo() {
        this.text = new StringBuilder();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public String getText() {
        return text.toString();
    }

    public void insert(int offset, String str) {
        saveState();
        text.insert(offset, str);
        redoStack.clear();
    }

    public void delete(int start, int end) {
        saveState();
        text.delete(start, end);
        redoStack.clear();
    }

    public void replace(int start, int end, String str) {
        saveState();
        text.replace(start, end, str);
        redoStack.clear();
    }


    public void undo() {
        if (!undoStack.isEmpty()) {
            TextMemento memento = undoStack.pop();
            saveStateForRedo();
            text = new StringBuilder(memento.getText());
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            TextMemento memento = redoStack.pop();
            saveState();
            text = new StringBuilder(memento.getText());
        }
    }


    private void saveState() {
        undoStack.push(new TextMemento(text.toString()));
    }

    private void saveStateForRedo() {
        redoStack.push(new TextMemento(text.toString()));
    }

    private static class TextMemento {
        private final String text;

        public TextMemento(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public static void main(String[] args) {
        TextBufferUndoRedo buffer = new TextBufferUndoRedo();

        buffer.insert(0, "Hello");
        System.out.println("Text: " + buffer.getText()); // Output: Hello

        buffer.insert(5, ", World!");
        System.out.println("Text: " + buffer.getText()); // Output: Hello, World!

        buffer.undo();
        System.out.println("Text: " + buffer.getText()); // Output: Hello

        buffer.redo();
        System.out.println("Text: " + buffer.getText()); // Output: Hello, World!

        buffer.delete(0, 5);
        System.out.println("Text: " + buffer.getText()); // Output: , World!

        buffer.undo();
        System.out.println("Text: " + buffer.getText()); // Output: Hello, World!

        buffer.replace(0, 5, "Goodbye");
        System.out.println("Text: " + buffer.getText()); // Output: Goodbye, World!

        buffer.undo();
        System.out.println("Text: " + buffer.getText()); // Output: Hello, World!

        buffer.undo();
        System.out.println("Text: " + buffer.getText()); // Output: Hello
    }
}