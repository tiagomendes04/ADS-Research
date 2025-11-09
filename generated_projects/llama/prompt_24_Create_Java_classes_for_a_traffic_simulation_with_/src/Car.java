public class Car extends Vehicle {
    public Car(String id, double speed) {
        super(id, speed);
    }

    @Override
    public void update() {
        move();
        System.out.println("Car " + getId() + " moved to position " + getPosition());
    }
}