```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle {
    private String id;
    private double position;
    private double velocity;
    private double acceleration;
    private List<Instruction> instructions;

    public Vehicle(String id, double initialPosition, double initialVelocity, double initialAcceleration) {
        this.id = id;
        this.position = initialPosition;
        this.velocity = initialVelocity;
        this.acceleration = initialAcceleration;
        this.instructions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public void executeInstructions() {
        for (Instruction instruction : instructions) {
            switch (instruction.getType()) {
                case MOVE:
                    move(instruction.getDistance());
                    break;
                case CHANGE_ACCELERATION:
                    setAcceleration(instruction.getAcceleration());
                    break;
                case CHANGE_POSITION:
                    setPosition(instruction.getPosition());
                    break;
            }
        }
    }

    private void move(double distance) {
        position += velocity * time;
    }

    private void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    private void setPosition(double position) {
        this.position = position;
    }

    public static class Instruction {
        private String type;
        private double distance;
        private double acceleration;
        private double position;

        public Instruction(String type, double distance, double acceleration, double position) {
            this.type = type;
            this.distance = distance;
            this.acceleration = acceleration;
            this.position = position;
        }

        public String getType() {
            return type;
        }

        public double getDistance() {
            return distance;
        }

        public double getAcceleration() {
            return acceleration;
        }

        public double getPosition() {
            return position;
        }
    }
}
```

```java
import java.util.Random;

public class TrafficSimulation {
    private List<Vehicle> vehicles;
    private Random random;

    public TrafficSimulation() {
        vehicles = new ArrayList<>();
        random = new Random();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void runSimulation() {
        for (Vehicle vehicle : vehicles) {
            vehicle.executeInstructions();
        }
    }
}
```