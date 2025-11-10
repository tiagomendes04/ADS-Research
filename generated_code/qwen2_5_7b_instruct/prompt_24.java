```java
public class Vehicle {
    private String id;
    private int speed;
    private int position;

    public Vehicle(String id, int speed, int position) {
        this.id = id;
        this.speed = speed;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPosition() {
        return position;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

public class TrafficSimulation {
    private int numVehicles;
    private Vehicle[] vehicles;

    public TrafficSimulation(int numVehicles) {
        this.numVehicles = numVehicles;
        this.vehicles = new Vehicle[numVehicles];
    }

    public void initializeVehicles() {
        for (int i = 0; i < numVehicles; i++) {
            vehicles[i] = new Vehicle("V" + (i + 1), 0, i);
        }
    }

    public void updatePositions() {
        for (int i = 0; i < numVehicles - 1; i++) {
            vehicles[i].setPosition(vehicles[i + 1].getPosition());
        }
        vehicles[numVehicles - 1].setPosition(vehicles[numVehicles - 1].getPosition() + vehicles[numVehicles - 1].getSpeed());
    }

    public void printPositions() {
        for (Vehicle vehicle : vehicles) {
            System.out.println("Vehicle " + vehicle.getId() + ": " + vehicle.getPosition());
        }
    }

    public static void main(String[] args) {
        TrafficSimulation simulation = new TrafficSimulation(5);
        simulation.initializeVehicles();
        for (int i = 0; i < 10; i++) {
            simulation.updatePositions();
            simulation.printPositions();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```