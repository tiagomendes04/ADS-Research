public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        Order order = new Order();
        order.addItem(new Food("Burger", 10.99, "Juicy beef burger", "Burgers", "Beef"));
        order.addItem(new Drink("Coke", 2.99, "Cold Coke", "Drinks", "Large"));

        restaurant.placeOrder(order);

        System.out.println("Order Total: $" + order.getTotal());
        System.out.println("Order Items:");
        for (Menu item : order.getItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }
}