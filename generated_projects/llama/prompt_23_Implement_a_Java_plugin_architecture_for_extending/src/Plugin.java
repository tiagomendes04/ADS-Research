// Plugin Interface
public interface Plugin {
    void init();
    void execute();
    void destroy();
}

// PluginManager Class
public class PluginManager {
    private List<Plugin> plugins = new ArrayList<>();

    public void registerPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    public void unregisterPlugin(Plugin plugin) {
        plugins.remove(plugin);
    }

    public void executePlugins() {
        for (Plugin plugin : plugins) {
            plugin.execute();
        }
    }
}

// CoreApplication Class
public class CoreApplication {
    private PluginManager pluginManager;

    public CoreApplication() {
        pluginManager = new PluginManager();
    }

    public void registerPlugin(Plugin plugin) {
        pluginManager.registerPlugin(plugin);
    }

    public void unregisterPlugin(Plugin plugin) {
        pluginManager.unregisterPlugin(plugin);
    }

    public void executePlugins() {
        pluginManager.executePlugins();
    }
}

// Example Plugin Implementation
public class ExamplePlugin implements Plugin {
    @Override
    public void init() {
        System.out.println("Example plugin initialized.");
    }

    @Override
    public void execute() {
        System.out.println("Example plugin executed.");
    }

    @Override
    public void destroy() {
        System.out.println("Example plugin destroyed.");
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        CoreApplication coreApp = new CoreApplication();

        ExamplePlugin examplePlugin = new ExamplePlugin();
        coreApp.registerPlugin(examplePlugin);

        coreApp.executePlugins();
    }
}