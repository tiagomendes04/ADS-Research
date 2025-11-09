// Main class (Example Usage)
public class Main {
    public static void main(String[] args) {
        // Create Menu Items
        MenuItem burger = new MenuItem("Burger", "Classic beef burger with lettuce and tomato", 9.99, false);
        MenuItem fries = new MenuItem("Fries", "Crispy golden fries", 3.99, true);
        MenuItem salad = new MenuItem("Salad", "Fresh garden salad with vinaigrette dressing", 7.99, true);

        // Create Menu Categories
        MenuCategory mains = new MenuCategory("Mains");
        mains.addItem(burger);
        mains.addItem(salad);

        MenuCategory sides = new MenuCategory("Sides");
        sides.addItem(fries);

        // Create Menu
        Menu menu = new Menu();
        menu.addCategory(mains);
        menu.addCategory(sides);

        // Print Menu
        System.out.println("Menu:\n" + menu);

        // Create Order
        Order order = new Order("John Doe", "Extra napkins please");
        order.addItem(new OrderItem(burger, 1));
        order.addItem(new OrderItem(fries, 2, "Extra salt"));
        order.addItem(new OrderItem(salad, 1));


        // Print Order
        System.out.println("\nOrder Details:\n" + order);
    }
}