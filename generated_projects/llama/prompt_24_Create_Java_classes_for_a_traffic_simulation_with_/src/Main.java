public class Main {
    public static void main(String[] args) {
        TrafficSimulation simulation = new TrafficSimulation();

        Car car1 = new Car("Car1", 10);
        Car car2 = new Car("Car2", 15);
        Truck truck = new Truck("Truck1", 20);

        simulation.addVehicle(car1);
        simulation.addVehicle(car2);
        simulation.addVehicle(truck);

        TrafficLight trafficLight = new TrafficLight(TrafficLight.Color.GREEN);
        simulation.addIntersection(new Intersection());

        simulation.simulate();
    }
}