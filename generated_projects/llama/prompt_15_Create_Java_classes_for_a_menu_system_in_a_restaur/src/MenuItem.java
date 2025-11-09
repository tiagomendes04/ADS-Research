public class MenuItem extends Menu {
    private String category;

    public MenuItem(String name, double price, String description, String category) {
        super(name, price, description);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}