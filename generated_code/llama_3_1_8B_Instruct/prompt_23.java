```java
public interface Plugin {
    void init();
    void start();
    void stop();
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private List<Plugin> plugins;
    private boolean running;

    public PluginManager() {
        this.plugins = new ArrayList<>();
        this.running = false;
    }

    public void addPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    public void removePlugin(Plugin plugin) {
        plugins.remove(plugin);
    }

    public void initPlugins() {
        for (Plugin plugin : plugins) {
            plugin.init();
        }
    }

    public void startPlugins() {
        if (!running) {
            for (Plugin plugin : plugins) {
                plugin.start();
            }
            running = true;
        }
    }

    public void stopPlugins() {
        if (running) {
            for (Plugin plugin : plugins) {
                plugin.stop();
            }
            running = false;
        }
    }
}
```

```java
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {
    public static Class<?> loadClass(String className, File jarFile) throws IOException, ClassNotFoundException {
        URL url = jarFile.toURI().toURL();
        URLClassLoader loader = new URLClassLoader(new URL[]{url});
        return loader.loadClass(className);
    }

    public static Class<?> loadClass(String className, String jarPath) throws IOException, ClassNotFoundException {
        File jarFile = new File(jarPath);
        return loadClass(className, jarFile);
    }
}
```

```java
public class ExamplePlugin implements Plugin {
    @Override
    public void init() {
        System.out.println("Example plugin initialized");
    }

    @Override
    public void start() {
        System.out.println("Example plugin started");
    }

    @Override
    public void stop() {
        System.out.println("Example plugin stopped");
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        PluginManager manager = new PluginManager();

        File jarFile = new File("path/to/plugin.jar");
        try {
            Class<?> pluginClass = PluginLoader.loadClass("com.example.plugin.ExamplePlugin", jarFile);
            Plugin plugin = (Plugin) pluginClass.newInstance();
            manager.addPlugin(plugin);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        manager.initPlugins();
        manager.startPlugins();

        // Stop plugins
        manager.stopPlugins();
    }
}
```