import java.util.ArrayList;
 import java.util.List;
 import java.util.Random;
 
 public class TrafficSimulation {
 
     public static void main(String[] args) {
         Road road = new Road(1000, 1); // 1000 meters long, 1 lane
         List<Vehicle> vehicles = new ArrayList<>();
 
         Random random = new Random();
 
         // Create some vehicles with random speeds and positions
         for (int i = 0; i < 10; i++) {
             double speed = 10 + random.nextDouble() * 30; // Speed between 10 and 40 m/s
             double position = random.nextDouble() * 900; // Position between 0 and 900 meters
             vehicles.add(new Car(position, speed));
         }
 
         road.setVehicles(vehicles);
 
         // Simulate for a few steps
         for (int i = 0; i < 100; i++) {
             road.update(1); // Update the road state every 1 second
 
             System.out.println("Time: " + i + "s");
             for (Vehicle vehicle : road.getVehicles()) {
                 System.out.println(vehicle);
             }
             System.out.println("------------------");
 
             try {
                 Thread.sleep(100); // Pause for 100 milliseconds to visualize
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }
 }
 
 
 class Road {
     private double length;
     private int lanes;
     private List<Vehicle> vehicles;
 
     public Road(double length, int lanes) {
         this.length = length;
         this.lanes = lanes;
         this.vehicles = new ArrayList<>();
     }
 
     public void update(double timeStep) {
         // Update vehicles' positions based on their speeds and interactions
         for (int i = 0; i < vehicles.size(); i++) {
             Vehicle vehicle = vehicles.get(i);
 
             // Basic following behavior (very simplified)
             double distanceToNext = Double.MAX_VALUE;
             for (int j = 0; j < vehicles.size(); j++) {
                 if (i != j) {
                     double otherVehiclePosition = vehicles.get(j).getPosition();
                     if (otherVehiclePosition > vehicle.getPosition()) {
                         distanceToNext = Math.min(distanceToNext, otherVehiclePosition - vehicle.getPosition());
                     }
                 }
             }
 
             double desiredSpeed = 30;  // Target speed (m/s)
             double maxAcceleration = 2; // m/s^2
             double maxDeceleration = -3; // m/s^2
 
             double acceleration = 0;
             if (distanceToNext < 50) {
                 // Slow down if close to the car ahead
                 acceleration = maxDeceleration;
             } else if (vehicle.getSpeed() < desiredSpeed) {
                 // Accelerate towards the desired speed
                 acceleration = maxAcceleration;
             } else {
                 acceleration = 0; // Maintain speed
             }
 
             vehicle.setSpeed(vehicle.getSpeed() + acceleration * timeStep);
             if (vehicle.getSpeed() < 0) {
                 vehicle.setSpeed(0); // Prevent negative speeds
             }
 
             vehicle.setPosition(vehicle.getPosition() + vehicle.getSpeed() * timeStep);
 
             // Boundary condition: Wrap around if exceeding road length
             if (vehicle.getPosition() > length) {
                 vehicle.setPosition(vehicle.getPosition() - length);
             }
         }
     }
 
     public double getLength() {
         return length;
     }
 
     public int getLanes() {
         return lanes;
     }
 
     public List<Vehicle> getVehicles() {
         return vehicles;
     }
 
     public void setVehicles(List<Vehicle> vehicles) {
         this.vehicles = vehicles;
     }
 }
 
 
 interface Vehicle {
     double getPosition();
     void setPosition(double position);
     double getSpeed();
     void setSpeed(double speed);
 }
 
 class Car implements Vehicle {
     private double position;
     private double speed;
 
     public Car(double position, double speed) {
         this.position = position;
         this.speed = speed;
     }
 
     @Override
     public double getPosition() {
         return position;
     }
 
     @Override
     public void setPosition(double position) {
         this.position = position;
     }
 
     @Override
     public double getSpeed() {
         return speed;
     }
 
     @Override
     public void setSpeed(double speed) {
         this.speed = speed;
     }
 
     @Override
     public String toString() {
         return "Car{" +
                 "position=" + String.format("%.2f", position) +
                 ", speed=" + String.format("%.2f", speed) +
                 '}';
     }
 }