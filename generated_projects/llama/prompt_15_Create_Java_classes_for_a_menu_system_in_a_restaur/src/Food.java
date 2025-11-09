public class Food extends MenuItem {
    private String type;

    public Food(String name, double price, String description, String category, String type) {
        super(name, price, description, category);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}