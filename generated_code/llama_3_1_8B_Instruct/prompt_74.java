```java
public class TemperatureController {
    private double temperature;
    private double targetTemperature;

    public TemperatureController(double initialTemperature) {
        this.temperature = initialTemperature;
        this.targetTemperature = initialTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void increaseTemperature(double amount) {
        temperature = Math.min(temperature + amount, 30);
    }

    public void decreaseTemperature(double amount) {
        temperature = Math.max(temperature - amount, 10);
    }
}
```

```java
import java.util.Scanner;

public class SmartThermostat {
    private TemperatureController controller;

    public SmartThermostat(TemperatureController controller) {
        this.controller = controller;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Current temperature: " + controller.getTemperature());
            System.out.println("Target temperature: " + controller.getTargetTemperature());

            System.out.println("1. Increase temperature");
            System.out.println("2. Decrease temperature");
            System.out.println("3. Set target temperature");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter amount to increase temperature: ");
                    double increaseAmount = scanner.nextDouble();
                    controller.increaseTemperature(increaseAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to decrease temperature: ");
                    double decreaseAmount = scanner.nextDouble();
                    controller.decreaseTemperature(decreaseAmount);
                    break;
                case 3:
                    System.out.print("Enter new target temperature: ");
                    double targetTemperature = scanner.nextDouble();
                    controller.setTargetTemperature(targetTemperature);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    public static void main(String[] args) {
        TemperatureController controller = new TemperatureController(20);
        SmartThermostat thermostat = new SmartThermostat(controller);
        thermostat.run();
    }
}
```