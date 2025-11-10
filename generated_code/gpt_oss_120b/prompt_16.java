```java
import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditor {
    public static void main(String[] args) {
        TextBuffer buffer = new TextBuffer();
        UndoManager undoManager = new UndoManager();

        undoManager.execute(new InsertCommand(buffer, 0, "Hello"));
        undoManager.execute(new InsertCommand(buffer, 5, " World"));
        undoManager.execute(new DeleteCommand(buffer, 5, 1)); // delete space

        System.out.println(buffer.getText()); // HelloWorld

        undoManager.undo();
        System.out.println(buffer.getText()); // Hello World

        undoManager.undo();
        System.out.println(buffer.getText()); // Hello

        undoManager.redo();
        System.out.println(buffer.getText()); // Hello World

        undoManager.redo();
        System.out.println(buffer.getText()); // HelloWorld
    }
}

/* ----------------------- Core Buffer ----------------------- */
class TextBuffer {
    private final StringBuilder sb = new StringBuilder();

    public void insert(int pos, String text) {
        sb.insert(pos, text);
    }

    public String delete(int pos, int length) {
        String removed = sb.substring(pos, pos + length);
        sb.delete(pos, pos + length);
        return removed;
    }

    public String getText() {
        return sb.toString();
    }

    public int length() {
        return sb.length();
    }
}

/* ----------------------- Command Pattern ----------------------- */
interface Command {
    void execute();
    void undo();
}

/* Insert command */
class InsertCommand implements Command {
    private final TextBuffer buffer;
    private final int position;
    private final String text;

    public InsertCommand(TextBuffer buffer, int position, String text) {
        this.buffer = buffer;
        this.position = position;
        this.text = text;
    }

    @Override
    public void execute() {
        buffer.insert(position, text);
    }

    @Override
    public void undo() {
        buffer.delete(position, text.length());
    }
}

/* Delete command */
class DeleteCommand implements Command {
    private final TextBuffer buffer;
    private final int position;
    private final int length;
    private String deletedText; // captured on execute

    public DeleteCommand(TextBuffer buffer, int position, int length) {
        this.buffer = buffer;
        this.position = position;
        this.length = length;
    }

    @Override
    public void execute() {
        deletedText = buffer.delete(position, length);
    }

    @Override
    public void undo() {
        buffer.insert(position, deletedText);
    }
}

/* ----------------------- Undo/Redo Manager ----------------------- */
class UndoManager {
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public void execute(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
```