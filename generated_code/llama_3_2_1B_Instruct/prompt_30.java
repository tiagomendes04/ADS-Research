```java
import java.util.HashMap;
import java.util.Map;

public class CMS {
    public static void main(String[] args) {
        Map<String, String> pages = new HashMap<>();
        Map<String, Module> modules = new HashMap<>();

        addPage(pages, "Home", "A simple home page");
        addPage(pages, "About", "About us");
        addPage(pages, "Contact", "Get in touch");

        addModule(pages, "Blog", ModuleType.BLOG, new BlogModule(pages));
        addModule(pages, "News", ModuleType.BLOG, new NewsModule(pages));
        addModule(pages, "FAQ", ModuleType.BLOG, new FAQModule(pages));

        System.out.println(pages);
        System.out.println(modules);
    }

    public static void addPage(Map<String, String> pages, String name, String description) {
        pages.put(name, description);
    }

    public static void addModule(Map<String, String> pages, String name, ModuleType type, Module module) {
        if (pages.containsKey(name)) {
            System.out.println("Module already exists for " + name);
        } else {
            pages.put(name, module.getDescription());
        }
    }

    public static class Module {
        public ModuleType type;
        public String description;

        public Module(String description) {
            this.description = description;
            this.type = ModuleType.DEFAULT;
        }

        public String getDescription() {
            return description;
        }
    }

    public static enum ModuleType {
        BLOG,
        NEWS,
        FAQ
    }
}
```