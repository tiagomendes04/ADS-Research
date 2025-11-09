// Core Application

// Plugin Interface
public interface Plugin {
    String getName();
    void execute();
}

// Plugin Manager
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    private final List<Plugin> plugins = new ArrayList<>();

    public void loadPlugins(String pluginDirectory) {
        File dir = new File(pluginDirectory);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Plugin directory does not exist: " + pluginDirectory);
            return;
        }

        File[] jarFiles = dir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));

        if (jarFiles == null || jarFiles.length == 0) {
            System.out.println("No plugins found in directory: " + pluginDirectory);
            return;
        }

        for (File jarFile : jarFiles) {
            try {
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
                Class<?>[] classes = classLoader.loadClass("PluginLoader").getMethods()[0].invoke(null).getClass().getInterfaces();

                for (Class<?> iface : classes) {
                    if (iface.isAssignableFrom(Plugin.class)) {
                        Class<?> pluginClass = classLoader.loadClass("PluginLoader").getMethods()[0].invoke(null).getClass();
                        Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                        plugins.add(plugin);
                        System.out.println("Loaded plugin: " + plugin.getName());
                    }
                }
                classLoader.close();

            } catch (Exception e) {
                System.err.println("Error loading plugin from: " + jarFile.getName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void executePlugins() {
        for (Plugin plugin : plugins) {
            System.out.println("Executing plugin: " + plugin.getName());
            plugin.execute();
        }
    }
}

// Core Application Class
public class CoreApplication {

    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager();
        pluginManager.loadPlugins("plugins"); // Assuming plugins are in a "plugins" directory
        pluginManager.executePlugins();
    }
}


// Example Plugin Implementation (Create a JAR file from this)
// Package this into a jar file called plugin1.jar, and put in the "plugins" folder.

//Example Loader Class

public class PluginLoader {
    public static Plugin createPlugin() {
        return new ExamplePlugin();
    }
}

class ExamplePlugin implements Plugin {
    @Override
    public String getName() {
        return "Example Plugin";
    }

    @Override
    public void execute() {
        System.out.println("Example Plugin is executing...");
    }
}