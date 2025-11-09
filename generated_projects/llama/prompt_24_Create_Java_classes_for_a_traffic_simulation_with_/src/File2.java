import java.util.Random;

public abstract class Vehicle {
    protected String id;
    protected double speed;
    protected double position;
    protected Random random;

    public Vehicle(String id, double speed) {
        this.id = id;
        this.speed = speed;
        this.position = 0;
        this.random = new Random();
    }

    public abstract void update();

    public void move() {
        position += speed;
    }

    public String getId() {
        return id;
    }

    public double getPosition() {
        return position;
    }
}