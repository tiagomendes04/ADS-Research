import java.util.ArrayList;
import java.util.List;

public class CMS {
    private List<Page> pages;

    public CMS() {
        this.pages = new ArrayList<>();
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }

    public List<Page> getPages() {
        return pages;
    }
}