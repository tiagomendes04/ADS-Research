```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentManagementSystem {

    private Map<String, Page> pages = new HashMap<>();
    private Map<String, Module> modules = new HashMap<>();

    public void addPage(Page page) {
        pages.put(page.getId(), page);
    }

    public void addModule(Module module) {
        modules.put(module.getId(), module);
    }

    public Page getPageById(String id) {
        return pages.get(id);
    }

    public Module getModuleById(String id) {
        return modules.get(id);
    }

    public void displayPage(String id) {
        Page page = getPageById(id);
        if (page != null) {
            page.display();
        }
    }

    public void displayModule(String id) {
        Module module = getModuleById(id);
        if (module != null) {
            module.display();
        }
    }
}
```

```java
import java.util.HashMap;
import java.util.Map;

public class Page {

    private String id;
    private String title;
    private String content;

    public Page(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void display() {
        System.out.println("Page: " + title);
        System.out.println("Content: " + content);
    }
}

```

```java
import java.util.HashMap;
import java.util.Map;

public class Module {

    private String id;
    private String name;
    private Map<String, List<String>> dependencies;

    public Module(String id, String name) {
        this.id = id;
        this.name = name;
        this.dependencies = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, List<String>> getDependencies() {
        return dependencies;
    }

    public void display() {
        System.out.println("Module: " + name);
        System.out.println("Dependencies: " + dependencies);
    }
}
```

```java
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ContentManagementSystem cms = new ContentManagementSystem();
        cms.addPage(new Page("1", "Home", "Welcome to the CMS!"));
        cms.addModule(new Module("1", "Core Module"));
        cms.displayPage("1");
        cms.displayModule("1");
    }
}
```