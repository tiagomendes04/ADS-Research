```java
public abstract class Device {
    private String id;
    private String name;
    private boolean isOn;

    public Device(String id, String name) {
        this.id = id;
        this.name = name;
        this.isOn = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
    }

    public abstract void control();
}
```

```java
public class Light extends Device {
    public Light(String id, String name) {
        super(id, name);
    }

    @Override
    public void control() {
        System.out.println("Light " + getName() + " is " + (isOn() ? "on" : "off"));
    }
}
```

```java
public class TemperatureSensor extends Device {
    private double temperature;

    public TemperatureSensor(String id, String name) {
        super(id, name);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public void control() {
        System.out.println("Temperature sensor " + getName() + " is " + (isOn() ? "on" : "off"));
        System.out.println("Current temperature: " + getTemperature() + "°C");
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class HomeAutomationSystem {
    private List<Device> devices;

    public HomeAutomationSystem() {
        devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void controlDevice(Device device) {
        device.control();
    }

    public void controlAllDevices() {
        for (Device device : devices) {
            device.control();
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        HomeAutomationSystem system = new HomeAutomationSystem();

        Light light = new Light("L1", "Kitchen Light");
        TemperatureSensor temperatureSensor = new TemperatureSensor("TS1", "Living Room Temperature Sensor");

        system.addDevice(light);
        system.addDevice(temperatureSensor);

        system.controlAllDevices();

        light.turnOn();
        temperatureSensor.setTemperature(25.5);

        system.controlDevice(light);
        system.controlDevice(temperatureSensor);
    }
}
```