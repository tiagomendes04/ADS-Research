```java
import java.util.ServiceLoader;

public interface Plugin {
    void execute();
}

public class CoreApplication {
    public static void main(String[] args) {
        ServiceLoader<Plugin> plugins = ServiceLoader.load(Plugin.class);
        for (Plugin plugin : plugins) {
            plugin.execute();
        }
    }
}

// Example Plugin Implementation in a separate JAR file
// package com.example.plugins;
// public class MyPlugin implements Plugin {
//     @Override
//     public void execute() {
//         System.out.println("Executing MyPlugin");
//     }
// }

// META-INF/services/com.example.Plugin
// com.example.plugins.MyPlugin
```