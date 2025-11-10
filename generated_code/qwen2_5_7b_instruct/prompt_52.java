```java
import java.util.ArrayList;
import java.util.List;

public abstract class TextEditorPlugin {
    public abstract void initialize();
}

public class TextEditor {
    private List<TextEditorPlugin> plugins = new ArrayList<>();

    public void addPlugin(TextEditorPlugin plugin) {
        plugins.add(plugin);
    }

    public void removePlugin(TextEditorPlugin plugin) {
        plugins.remove(plugin);
    }

    public void executePlugins() {
        for (TextEditorPlugin plugin : plugins) {
            plugin.initialize();
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.addPlugin(new SpellCheckPlugin());
        editor.addPlugin(new AutoCompletePlugin());
        editor.executePlugins();
    }
}

class SpellCheckPlugin extends TextEditorPlugin {
    @Override
    public void initialize() {
        System.out.println("Spell check plugin initialized.");
    }
}

class AutoCompletePlugin extends TextEditorPlugin {
    @Override
    public void initialize() {
        System.out.println("Auto-complete plugin initialized.");
    }
}
```