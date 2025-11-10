```java
public class Thermostat {
    private double currentTemperature;
    private double setPoint;
    private double heatingCost;
    private boolean heatingOn;

    public Thermostat(double setPoint) {
        this.setPoint = setPoint;
        this.currentTemperature = setPoint;
        this.heatingCost = 0.0;
        this.heatingOn = false;
    }

    public void setHeatingOn(boolean heatingOn) {
        this.heatingOn = heatingOn;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getSetPoint() {
        return setPoint;
    }

    public double getHeatingCost() {
        return heatingCost;
    }

    public void updateTemperature(double temperatureChange) {
        currentTemperature += temperatureChange;
        if (currentTemperature > setPoint) {
            heatingOn = true;
        } else if (currentTemperature < setPoint) {
            heatingOn = false;
        }
        heatingCost += calculateHeatingCost(temperatureChange);
    }

    private double calculateHeatingCost(double temperatureChange) {
        // Calculate heating cost based on temperature change
        return 0.1 * temperatureChange;
    }
}
```

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the set point temperature: ");
        double setPoint = scanner.nextDouble();
        Thermostat thermostat = new Thermostat(setPoint);

        while (true) {
            System.out.println("Current temperature: " + thermostat.getCurrentTemperature());
            System.out.println("Set Point: " + thermostat.getSetPoint());
            System.out.println("Heating Cost: " + thermostat.getHeatingCost());
            System.out.println("Heating on: " + thermostat.getHeatingOn());

            System.out.println("Enter 'q' to quit or any other key to control temperature.");
            String input = scanner.next();
            if (input.equals("q")) {
                break;
            }
            System.out.print("Enter temperature change: ");
            double temperatureChange = scanner.nextDouble();
            thermostat.updateTemperature(temperatureChange);
        }
        scanner.close();
    }
}
``` 
```java
import java.util.Random;

public class HeatingCostCalculator {
    public double calculateHeatingCost(double temperatureChange) {
        // Calculate heating cost based on temperature change
        return 0.1 * temperatureChange;
    }
}
```