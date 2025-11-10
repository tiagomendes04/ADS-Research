```java
// src/com/example/plugin/Plugin.java
package com.example.plugin;

public interface Plugin {
    String getName();
    void initialize();
    void execute();
    void shutdown();
}
```

```java
// src/com/example/core/PluginManager.java
package com.example.core;

import com.example.plugin.Plugin;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginManager {
    private final Map<String, Plugin> plugins = new HashMap<>();

    public void loadPlugins(File pluginsDir) throws Exception {
        if (!pluginsDir.isDirectory()) {
            throw new IllegalArgumentException("Plugins directory not found: " + pluginsDir);
        }
        File[] jars = pluginsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
        if (jars == null) return;

        for (File jar : jars) {
            URL jarUrl = jar.toURI().toURL();
            try (URLClassLoader cl = new URLClassLoader(new URL[]{jarUrl}, this.getClass().getClassLoader())) {
                ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class, cl);
                for (Plugin plugin : loader) {
                    plugin.initialize();
                    plugins.put(plugin.getName(), plugin);
                    System.out.println("Loaded plugin: " + plugin.getName());
                }
            }
        }
    }

    public void executeAll() {
        plugins.values().forEach(Plugin::execute);
    }

    public void shutdownAll() {
        plugins.values().forEach(Plugin::shutdown);
    }

    public Optional<Plugin> getPlugin(String name) {
        return Optional.ofNullable(plugins.get(name));
    }
}
```

```java
// src/com/example/core/CoreApplication.java
package com.example.core;

import java.io.File;

public class CoreApplication {
    public static void main(String[] args) throws Exception {
        PluginManager manager = new PluginManager();
        File pluginsDir = new File("plugins"); // directory containing plugin JARs
        manager.loadPlugins(pluginsDir);
        manager.executeAll();

        Runtime.getRuntime().addShutdownHook(new Thread(manager::shutdownAll));
    }
}
```

```java
// sample-plugin/src/com/example/sample/SamplePlugin.java
package com.example.sample;

import com.example.plugin.Plugin;

public class SamplePlugin implements Plugin {
    @Override
    public String getName() {
        return "SamplePlugin";
    }

    @Override
    public void initialize() {
        System.out.println("SamplePlugin initialized");
    }

    @Override
    public void execute() {
        System.out.println("SamplePlugin executed");
    }

    @Override
    public void shutdown() {
        System.out.println("SamplePlugin shut down");
    }
}
```

```text
# sample-plugin/src/META-INF/services/com.example.plugin.Plugin
com.example.sample.SamplePlugin
```