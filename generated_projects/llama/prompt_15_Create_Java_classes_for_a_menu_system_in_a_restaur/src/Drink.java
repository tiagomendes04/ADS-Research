public class Drink extends MenuItem {
    private String size;

    public Drink(String name, double price, String description, String category, String size) {
        super(name, price, description, category);
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}