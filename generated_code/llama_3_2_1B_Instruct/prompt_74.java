```java
import java.util.Scanner;

public class SmartThermostatSimulation {
    public static void main(String[] args) {
        Thermostat thermostat = new Thermostat();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Smart Thermostat Simulation");
            System.out.println("1. Set Temperature");
            System.out.println("2. Get Current Temperature");
            System.out.println("3. Increase Temperature");
            System.out.println("4. Decrease Temperature");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    thermostat.setTemperature(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Current Temperature: " + thermostat.getCurrentTemperature());
                    break;
                case 3:
                    thermostat.increaseTemperature();
                    break;
                case 4:
                    thermostat.decreaseTemperature();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }

            System.out.println();
        }
    }
}
```

```java
public class Thermostat {
    private double temperature;

    public Thermostat() {
        this.temperature = 20.0; // Default temperature in Celsius
    }

    public void setTemperature(String temperature) {
        try {
            this.temperature = Double.parseDouble(temperature);
            System.out.println("Temperature set to " + temperature + "°C");
        } catch (NumberFormatException e) {
            System.out.println("Invalid temperature. Please enter a valid number.");
        }
    }

    public double getCurrentTemperature() {
        return this.temperature;
    }

    public void increaseTemperature() {
        if (this.temperature < 30.0) {
            this.temperature += 0.5;
            System.out.println("Temperature increased to " + this.temperature + "°C");
        } else {
            System.out.println("Maximum temperature reached. Cannot increase further.");
        }
    }

    public void decreaseTemperature() {
        if (this.temperature > 15.0) {
            this.temperature -= 0.5;
            System.out.println("Temperature decreased to " + this.temperature + "°C");
        } else {
            System.out.println("Minimum temperature reached. Cannot decrease further.");
        }
    }
}
```