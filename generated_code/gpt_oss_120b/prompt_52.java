```java
// ------------------------------------------------------------
// core/Plugin.java
package core;

public interface Plugin {
    /** Called once when the plugin is loaded. */
    void init(PluginContext context);

    /** Called when the editor is shutting down. */
    void shutdown();

    /** Returns the unique identifier of the plugin. */
    String getId();

    /** Returns a short description of the plugin. */
    String getDescription();
}
```

```java
// ------------------------------------------------------------
// core/PluginContext.java
package core;

public interface PluginContext {
    /** Register a command that can be invoked from the editor UI. */
    void registerCommand(String name, Command command);

    /** Register a listener for editor events. */
    void registerEventListener(EditorEvent.Type type, EditorEventListener listener);

    /** Access the underlying editor instance. */
    TextEditor getEditor();
}
```

```java
// ------------------------------------------------------------
// core/Command.java
package core;

@FunctionalInterface
public interface Command {
    /** Execute the command with optional arguments. */
    void execute(String... args);
}
```

```java
// ------------------------------------------------------------
// core/EditorEvent.java
package core;

public abstract class EditorEvent {
    public enum Type { TEXT_CHANGED, CURSOR_MOVED, FILE_OPENED, FILE_SAVED }

    private final Type type;
    public EditorEvent(Type type) { this.type = type; }
    public Type getType() { return type; }
}
```

```java
// ------------------------------------------------------------
// core/EditorEventListener.java
package core;

@FunctionalInterface
public interface EditorEventListener {
    void onEvent(EditorEvent event);
}
```

```java
// ------------------------------------------------------------
// core/PluginManager.java
package core;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginManager implements PluginContext {
    private final TextEditor editor;
    private final Map<String, Plugin> loaded = new HashMap<>();
    private final Map<EditorEvent.Type, List<EditorEventListener>> listeners = new EnumMap<>(EditorEvent.Type.class);
    private final Map<String, Command> commands = new HashMap<>();

    public PluginManager(TextEditor editor) {
        this.editor = editor;
        for (EditorEvent.Type t : EditorEvent.Type.values()) listeners.put(t, new ArrayList<>());
    }

    public void loadPlugin(File jarFile, String pluginClassName) throws Exception {
        URL url = jarFile.toURI().toURL();
        try (URLClassLoader cl = new URLClassLoader(new URL[]{url}, getClass().getClassLoader())) {
            Class<?> clazz = Class.forName(pluginClassName, true, cl);
            if (!Plugin.class.isAssignableFrom(clazz)) throw new IllegalArgumentException("Not a Plugin");
            Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
            plugin.init(this);
            loaded.put(plugin.getId(), plugin);
        }
    }

    public void unloadPlugin(String pluginId) {
        Plugin p = loaded.remove(pluginId);
        if (p != null) p.shutdown();
        // Note: command and listener cleanup is left to plugin implementation
    }

    public Set<String> getLoadedPluginIds() {
        return Collections.unmodifiableSet(loaded.keySet());
    }

    // ----- PluginContext implementation -----
    @Override public void registerCommand(String name, Command command) { commands.put(name, command); }

    @Override public void registerEventListener(EditorEvent.Type type, EditorEventListener listener) {
        listeners.get(type).add(listener);
    }

    @Override public TextEditor getEditor() { return editor; }

    // ----- Runtime usage -----
    public void executeCommand(String name, String... args) {
        Command cmd = commands.get(name);
        if (cmd != null) cmd.execute(args);
        else System.out.println("Command not found: " + name);
    }

    public void fireEvent(EditorEvent event) {
        for (EditorEventListener l : listeners.get(event.getType())) {
            l.onEvent(event);
        }
    }
}
```

```java
// ------------------------------------------------------------
// core/TextEditor.java
package core;

import java.util.*;

public class TextEditor {
    private final StringBuilder buffer = new StringBuilder();
    private final PluginManager pluginManager;

    public TextEditor() {
        this.pluginManager = new PluginManager(this);
    }

    public PluginManager getPluginManager() { return pluginManager; }

    // Basic editor operations
    public void insert(String text, int position) {
        buffer.insert(position, text);
        pluginManager.fireEvent(new TextChangedEvent(this.getText()));
    }

    public void delete(int start, int end) {
        buffer.delete(start, end);
        pluginManager.fireEvent(new TextChangedEvent(this.getText()));
    }

    public String getText() { return buffer.toString(); }

    public void moveCursor(int position) {
        pluginManager.fireEvent(new CursorMovedEvent(position));
    }

    // Simple command line loop (for demo)
    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("SimpleTextEditor. Type 'exit' to quit.");
        while (true) {
            System.out.print("> ");
            String line = sc.nextLine();
            if (line.equals("exit")) break;
            String[] parts = line.split("\\s+", 2);
            String cmd = parts[0];
            String args = parts.length > 1 ? parts[1] : "";
            pluginManager.executeCommand(cmd, args);
        }
        sc.close();
    }
}
```

```java
// ------------------------------------------------------------
// core/TextChangedEvent.java
package core;

public class TextChangedEvent extends EditorEvent {
    private final String newContent;
    public TextChangedEvent(String newContent) {
        super(Type.TEXT_CHANGED);
        this.newContent = newContent;
    }
    public String getNewContent() { return newContent; }
}
```

```java
// ------------------------------------------------------------
// core/CursorMovedEvent.java
package core;

public class CursorMovedEvent extends EditorEvent {
    private final int position;
    public CursorMovedEvent(int position) {
        super(Type.CURSOR_MOVED);
        this.position = position;
    }
    public int getPosition() { return position; }
}
```

```java
// ------------------------------------------------------------
// plugins/WordCountPlugin.java
package plugins;

import core.*;

public class WordCountPlugin implements Plugin {
    private PluginContext ctx;

    @Override public void init(PluginContext context) {
        this.ctx = context;
        ctx.registerCommand("wordcount", this::wordCount);
        ctx.registerEventListener(EditorEvent.Type.TEXT_CHANGED, e -> {
            // optional: auto-update count on each change
        });
    }

    private void wordCount(String... args) {
        String text = ctx.getEditor().getText();
        long count = text.isBlank() ? 0 : Arrays.stream(text.trim().split("\\s+")).count();
        System.out.println("[WordCount] Words: " + count);
    }

    @Override public void shutdown() { /* nothing */ }

    @Override public String getId() { return "WordCount"; }

    @Override public String getDescription() { return "Counts words in the document."; }
}
```

```java
// ------------------------------------------------------------
// plugins/UpperCasePlugin.java
package plugins;

import core.*;

public class UpperCasePlugin implements Plugin {
    private PluginContext ctx;

    @Override public void init(PluginContext context) {
        this.ctx = context;
        ctx.registerCommand("uppercase", this::toUpper);
    }

    private void toUpper(String... args) {
        TextEditor editor = ctx.getEditor();
        String upper = editor.getText().toUpperCase();
        editor.delete(0, editor.getText().length());
        editor.insert(upper, 0);
        System.out.println("[UpperCase] Converted text to uppercase.");
    }

    @Override public void shutdown() { }

    @Override public String getId() { return "UpperCase"; }

    @Override public String getDescription() { return "Converts all text to uppercase."; }
}
```

```java
// ------------------------------------------------------------
// Main.java
import core.*;

public class Main {
    public static void main(String[] args) throws Exception {
        TextEditor editor = new TextEditor();

        // Load plugins from JARs (for demo we load directly from classpath)
        PluginManager pm = editor.getPluginManager();
        pm.loadPlugin(new java.io.File("plugins/WordCountPlugin.jar"), "plugins.WordCountPlugin");
        pm.loadPlugin(new java.io.File("plugins/UpperCasePlugin.jar"), "plugins.UpperCasePlugin");

        // Simple usage demo
        editor.insert("Hello world from the editor.", 0);
        editor.start();

        // Unload on exit
        pm.unloadPlugin("WordCount");
        pm.unloadPlugin("UpperCase");
    }
}
```