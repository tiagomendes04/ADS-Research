import java.util.ArrayList;
import java.util.List;

public class TrafficSimulation {
    private List<Vehicle> vehicles;
    private List<Intersection> intersections;

    public TrafficSimulation() {
        this.vehicles = new ArrayList<>();
        this.intersections = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addIntersection(Intersection intersection) {
        intersections.add(intersection);
    }

    public void simulate() {
        for (Vehicle vehicle : vehicles) {
            vehicle.update();
        }

        for (Intersection intersection : intersections) {
            intersection.update();
        }
    }
}