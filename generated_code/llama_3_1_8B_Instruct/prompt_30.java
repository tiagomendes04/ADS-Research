```java
public class Page {
    private String id;
    private String title;
    private String content;
    private List<Module> modules;

    public Page(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modules = new ArrayList<>();
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

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }
}
```

```java
public abstract class Module {
    private String id;
    private String title;
    private String content;

    public Module(String id, String title, String content) {
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
}
```

```java
public class TextModule extends Module {
    public TextModule(String id, String title, String content) {
        super(id, title, content);
    }
}
```

```java
public class ImageModule extends Module {
    private String src;

    public ImageModule(String id, String title, String content, String src) {
        super(id, title, content);
        this.src = src;
    }

    public String getSrc() {
        return src;
    }
}
```

```java
public class CMS {
    private Map<String, Page> pages;

    public CMS() {
        this.pages = new HashMap<>();
    }

    public void addPage(Page page) {
        this.pages.put(page.getId(), page);
    }

    public Page getPage(String id) {
        return this.pages.get(id);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        CMS cms = new CMS();

        Page page = new Page("home", "Home Page", "Welcome to our website!");
        cms.addPage(page);

        page.addModule(new TextModule("text1", "Title 1", "This is a text module."));
        page.addModule(new TextModule("text2", "Title 2", "This is another text module."));

        page.addModule(new ImageModule("image1", "Image 1", "This is an image module.", "image1.jpg"));

        System.out.println(cms.getPage("home").getTitle());
        System.out.println(cms.getPage("home").getContent());

        for (Module module : cms.getPage("home").getModules()) {
            System.out.println(module.getTitle());
            System.out.println(module.getContent());
        }
    }
}
```