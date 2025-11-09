import java.util.ArrayList;
import java.util.List;

public class ContentManagementSystem {
    private List<Page> pages;

    public ContentManagementSystem() {
        this.pages = new ArrayList<>();
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public void removePage(Page page) {
        pages.remove(page);
    }

    public Page getPage(String title) {
        for (Page page : pages) {
            if (page.getTitle().equals(title)) {
                return page;
            }
        }
        return null;
    }

    public List<Page> getAllPages() {
        return new ArrayList<>(pages);
    }
}

class Page {
    private String title;
    private List<Module> modules;

    public Page(String title) {
        this.title = title;
        this.modules = new ArrayList<>();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void removeModule(Module module) {
        modules.remove(module);
    }

    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }

    public List<Module> getAllModules() {
        return new ArrayList<>(modules);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

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
}