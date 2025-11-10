```java
public interface Device {
    void turnOn();
    void turnOff();
    String getStatus();
}

public class Light implements Device {
    private boolean isOn;

    public Light() {
        this.isOn = false;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
    }

    @Override
    public void turnOff() {
        this.isOn = false;
    }

    @Override
    public String getStatus() {
        return isOn ? "Light is ON" : "Light is OFF";
    }
}

public class Thermostat implements Device {
    private int temperature;

    public Thermostat(int initialTemperature) {
        this.temperature = initialTemperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    @Override
    public void turnOn() {
        // Placeholder for turning on thermostat functionality
    }

    @Override
    public void turnOff() {
        // Placeholder for turning off thermostat functionality
    }

    @Override
    public String getStatus() {
        return "Thermostat temperature is " + temperature + " degrees";
    }
}

public class HomeAutomationSystem {
    private Device[] devices;

    public HomeAutomationSystem(Device[] devices) {
        this.devices = devices;
    }

    public void controlDevice(int index, boolean turnOn) {
        if (index >= 0 && index < devices.length) {
            if (turnOn) {
                devices[index].turnOn();
            } else {
                devices[index].turnOff();
            }
        }
    }

    public void displayDeviceStatuses() {
        for (int i = 0; i < devices.length; i++) {
            System.out.println("Device " + i + ": " + devices[i].getStatus());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Device light = new Light();
        Device thermostat = new Thermostat(22);

        Device[] devices = {light, thermostat};
        HomeAutomationSystem system = new HomeAutomationSystem(devices);

        system.displayDeviceStatuses();
        system.controlDevice(0, true);
        system.controlDevice(1, false);
        system.displayDeviceStatuses();
    }
}
```