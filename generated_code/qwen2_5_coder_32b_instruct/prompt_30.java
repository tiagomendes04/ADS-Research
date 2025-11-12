```java
import java.util.ArrayList;
import java.util.List;

class Module {
    private String name;
    private String content;

    public Module(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

class Page {
    private String title;
    private String description;
    private List<Module> modules;

    public Page(String title, String description) {
        this.title = title;
        this.description = description;
        this.modules = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public void removeModule(Module module) {
        this.modules.remove(module);
    }

    @Override
    public String toString() {
        return "Page{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", modules=" + modules +
                '}';
    }
}

class ContentManagementSystem {
    private List<Page> pages;

    public ContentManagementSystem() {
        this.pages = new ArrayList<>();
    }

    public List<Page> getPages() {
        return pages;
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }

    public void removePage(Page page) {
        this.pages.remove(page);
    }

    @Override
    public String toString() {
        return "ContentManagementSystem{" +
                "pages=" + pages +
                '}';
    }

    public static void main(String[] args) {
        ContentManagementSystem cms = new ContentManagementSystem();

        Page homePage = new Page("Home", "Welcome to our website");
        Module introModule = new Module("Introduction", "This is an introduction to our site.");
        homePage.addModule(introModule);

        Page aboutPage = new Page("About", "Information about us");
        Module teamModule = new Module("Team", "Meet the team behind our website.");
        aboutPage.addModule(teamModule);

        cms.addPage(homePage);
        cms.addPage(aboutPage);

        System.out.println(cms);
    }
}
```