public class Truck extends Vehicle {
    public Truck(String id, double speed) {
        super(id, speed);
    }

    @Override
    public void update() {
        move();
        System.out.println("Truck " + getId() + " moved to position " + getPosition());
    }
}