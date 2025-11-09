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