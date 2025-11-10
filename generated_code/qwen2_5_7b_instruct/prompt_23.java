```java
import java.util.ServiceLoader;

public class CoreApplication {

    public static void main(String[] args) {
        ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);
        for (Plugin plugin : loader) {
            plugin.execute();
        }
    }
}

@FunctionalInterface
interface Plugin {
    void execute();
}

@Service
class Plugin1 implements Plugin {
    @Override
    public void execute() {
        System.out.println("Executing Plugin 1");
    }
}

@Service
class Plugin2 implements Plugin {
    @Override
    public void execute() {
        System.out.println("Executing Plugin 2");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Service {
}
```