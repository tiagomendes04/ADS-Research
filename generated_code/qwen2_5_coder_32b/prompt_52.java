```java
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public interface TextEditorPlugin {
    void apply(StringBuilder text);
}

public class TextEditor {
    private final List<TextEditorPlugin> plugins = new ArrayList<>();

    public TextEditor() {
        ServiceLoader.load(TextEditorPlugin.class).forEach(plugins::add);
    }

    public void editText(StringBuilder text) {
        for (TextEditorPlugin plugin : plugins) {
            plugin.apply(text);
        }
    }

    public static void main(String[] args) {
        StringBuilder text = new StringBuilder("Sample text.");
        TextEditor editor = new TextEditor();
        editor.editText(text);
        System.out.println(text.toString());
    }
}

public class UppercasePlugin implements TextEditorPlugin {
    @Override
    public void apply(StringBuilder text) {
        for (int i = 0; i < text.length(); i++) {
            text.setCharAt(i, Character.toUpperCase(text.charAt(i)));
        }
    }
}

public class AddSignaturePlugin implements TextEditorPlugin {
    @Override
    public void apply(StringBuilder text) {
        text.append("\nBest regards,\nYour Name");
    }
}
```