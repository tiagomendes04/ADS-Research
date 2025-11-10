```java
import java.util.Map;

public interface Plugin {
  void execute(Map<String, Object> pluginContext);
}

public class PluginManager {
  private Map<String, Plugin> plugins;

  public PluginManager() {
    this.plugins = new java.util.HashMap<>();
  }

  public void registerPlugin(String pluginId, Plugin plugin) {
    plugins.put(pluginId, plugin);
  }

  public void executePlugins(Map<String, Object> pluginContext) {
    for (Plugin plugin : plugins.values()) {
      plugin.execute(pluginContext);
    }
  }
}

public class Application {
  private PluginManager pluginManager;

  public Application() {
    this.pluginManager = new PluginManager();
  }

  public void execute() {
    // ... application logic ...
  }
}
```