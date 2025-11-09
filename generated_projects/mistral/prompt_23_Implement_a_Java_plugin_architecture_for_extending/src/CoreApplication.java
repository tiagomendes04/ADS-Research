// CoreApplication.java
public interface CoreApplication {
    void initialize();
    void execute();
    void shutdown();
}

// Plugin.java
public interface Plugin {
    String getName();
    void initialize();
    void execute();
    void shutdown();
}

// PluginManager.java
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginManager {
    private final List<Plugin> plugins = new ArrayList<>();

    public void loadPlugins() {
        ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);
        for (Plugin plugin : loader) {
            plugins.add(plugin);
        }
    }

    public void initializePlugins() {
        for (Plugin plugin : plugins) {
            plugin.initialize();
        }
    }

    public void executePlugins() {
        for (Plugin plugin : plugins) {
            plugin.execute();
        }
    }

    public void shutdownPlugins() {
        for (Plugin plugin : plugins) {
            plugin.shutdown();
        }
    }
}

// ExamplePlugin.java
public class ExamplePlugin implements Plugin {
    @Override
    public String getName() {
        return "ExamplePlugin";
    }

    @Override
    public void initialize() {
        System.out.println("Initializing " + getName());
    }

    @Override
    public void execute() {
        System.out.println("Executing " + getName());
    }

    @Override
    public void shutdown() {
        System.out.println("Shutting down " + getName());
    }
}

// MainApplication.java
public class MainApplication implements CoreApplication {
    private final PluginManager pluginManager = new PluginManager();

    @Override
    public void initialize() {
        System.out.println("Initializing Core Application");
        pluginManager.loadPlugins();
        pluginManager.initializePlugins();
    }

    @Override
    public void execute() {
        System.out.println("Executing Core Application");
        pluginManager.executePlugins();
    }

    @Override
    public void shutdown() {
        System.out.println("Shutting down Core Application");
        pluginManager.shutdownPlugins();
    }

    public static void main(String[] args) {
        CoreApplication app = new MainApplication();
        app.initialize();
        app.execute();
        app.shutdown();
    }
}

// META-INF/services/com.example.Plugin
// (This would be a file in the resources directory containing the fully qualified name of the plugin class)
// com.example.ExamplePlugin