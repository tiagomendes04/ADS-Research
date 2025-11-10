```java
public class SmartThermostat {
    private double currentTemperature;
    private double targetTemperature;
    private boolean isHeating;

    public SmartThermostat(double initialTemperature) {
        this.currentTemperature = initialTemperature;
        this.targetTemperature = 20.0;
        this.isHeating = false;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
        adjustHeating();
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public boolean isHeating() {
        return isHeating;
    }

    private void adjustHeating() {
        if (currentTemperature < targetTemperature) {
            startHeating();
        } else if (currentTemperature > targetTemperature) {
            stopHeating();
        }
    }

    private void startHeating() {
        isHeating = true;
        System.out.println("Heating started.");
    }

    private void stopHeating() {
        isHeating = false;
        System.out.println("Heating stopped.");
    }

    public void simulateTemperatureChange(double change) {
        currentTemperature += change;
        adjustHeating();
    }
}

public class ThermostatSimulator {
    public static void main(String[] args) {
        SmartThermostat thermostat = new SmartThermostat(18.0);
        System.out.println("Current Temperature: " + thermostat.getCurrentTemperature());
        System.out.println("Target Temperature: " + thermostat.getTargetTemperature());
        System.out.println("Is Heating: " + thermostat.isHeating());

        thermostat.setTargetTemperature(22.0);
        System.out.println("Target Temperature changed to 22.0");
        thermostat.simulateTemperatureChange(1.0);
        System.out.println("Current Temperature: " + thermostat.getCurrentTemperature());
        System.out.println("Is Heating: " + thermostat.isHeating());

        thermostat.simulateTemperatureChange(2.0);
        System.out.println("Current Temperature: " + thermostat.getCurrentTemperature());
        System.out.println("Is Heating: " + thermostat.isHeating());

        thermostat.simulateTemperatureChange(-4.0);
        System.out.println("Current Temperature: " + thermostat.getCurrentTemperature());
        System.out.println("Is Heating: " + thermostat.isHeating());
    }
}
```