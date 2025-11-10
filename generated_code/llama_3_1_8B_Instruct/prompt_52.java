```java
// Plugin.java
public interface Plugin {
    void load();
    void unload();
    String getName();
    void execute(String command);
}

// PluginManager.java
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private List<Plugin> plugins = new ArrayList<>();

    public void loadPlugin(Plugin plugin) {
        plugin.load();
        plugins.add(plugin);
    }

    public void unloadPlugin(Plugin plugin) {
        plugins.remove(plugin);
        plugin.unload();
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }
}

// PluginImpl.java
public class PluginImpl implements Plugin {
    private String name;

    public PluginImpl(String name) {
        this.name = name;
    }

    @Override
    public void load() {
        System.out.println("Loading plugin: " + name);
    }

    @Override
    public void unload() {
        System.out.println("Unloading plugin: " + name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String command) {
        System.out.println("Executing command: " + command + " with plugin: " + name);
    }
}

// PluginFactory.java
public class PluginFactory {
    public static Plugin createPlugin(String name) {
        return new PluginImpl(name);
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager();

        Plugin plugin1 = PluginFactory.createPlugin("Plugin1");
        Plugin plugin2 = PluginFactory.createPlugin("Plugin2");

        pluginManager.loadPlugin(plugin1);
        pluginManager.loadPlugin(plugin2);

        pluginManager.getPlugins().forEach(plugin -> {
            plugin.execute("test command");
        });

        pluginManager.unloadPlugin(plugin1);
        pluginManager.unloadPlugin(plugin2);
    }
}
```