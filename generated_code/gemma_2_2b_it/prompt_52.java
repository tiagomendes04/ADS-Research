```java
import java.util.HashMap;
import java.util.Map;

public class PluginManager {

    private Map<String, Plugin> plugins = new HashMap<>();

    public void registerPlugin(Plugin plugin) {
        plugins.put(plugin.getName(), plugin);
    }

    public void activatePlugins() {
        for (Plugin plugin : plugins.values()) {
            plugin.activate();
        }
    }

    public void deactivatePlugins() {
        for (Plugin plugin : plugins.values()) {
            plugin.deactivate();
        }
    }
}

interface Plugin {
    String getName();
    void activate();
    void deactivate();
}

class ExamplePlugin implements Plugin {
    private String name = "Example Plugin";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void activate() {
        System.out.println("Activating " + getName());
    }

    @Override
    public void deactivate() {
        System.out.println("Deactivating " + getName());
    }
}
```