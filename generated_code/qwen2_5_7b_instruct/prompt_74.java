```java
public class TemperatureControl {
    private double currentTemperature;
    private double targetTemperature;
    private boolean heatingEnabled;
    private boolean coolingEnabled;

    public TemperatureControl(double initialTemperature, double targetTemperature) {
        this.currentTemperature = initialTemperature;
        this.targetTemperature = targetTemperature;
        this.heatingEnabled = false;
        this.coolingEnabled = false;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public void increaseTemperature(double amount) {
        this.currentTemperature += amount;
    }

    public void decreaseTemperature(double amount) {
        this.currentTemperature -= amount;
    }

    public void update() {
        if (currentTemperature < targetTemperature) {
            heatingEnabled = true;
            coolingEnabled = false;
        } else if (currentTemperature > targetTemperature) {
            heatingEnabled = false;
            coolingEnabled = true;
        } else {
            heatingEnabled = false;
            coolingEnabled = false;
        }
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public boolean isHeatingEnabled() {
        return heatingEnabled;
    }

    public boolean isCoolingEnabled() {
        return coolingEnabled;
    }
}

public class ThermostatSimulation {
    public static void main(String[] args) {
        TemperatureControl thermostat = new TemperatureControl(20.0, 22.0);
        for (int i = 0; i < 10; i++) {
            thermostat.update();
            System.out.println("Current Temperature: " + thermostat.getCurrentTemperature() + ", Target Temperature: " + thermostat.getTargetTemperature());
            if (thermostat.isHeatingEnabled()) {
                thermostat.increaseTemperature(1.0);
            } else if (thermostat.isCoolingEnabled()) {
                thermostat.decreaseTemperature(1.0);
            }
            System.out.println("Heating Enabled: " + thermostat.isHeatingEnabled() + ", Cooling Enabled: " + thermostat.isCoolingEnabled());
        }
    }
}
```