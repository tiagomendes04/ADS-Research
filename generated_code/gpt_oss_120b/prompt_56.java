```java
// Device.java
public interface Device {
    String getId();
    String getName();
    void status();
}

// AbstractDevice.java
public abstract class AbstractDevice implements Device {
    protected final String id;
    protected final String name;

    public AbstractDevice(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return name; }
}

// Light.java
public class Light extends AbstractDevice {
    private boolean on = false;
    private int brightness = 0; // 0-100

    public Light(String id, String name) {
        super(id, name);
    }

    public void turnOn() { on = true; brightness = 100; }

    public void turnOff() { on = false; brightness = 0; }

    public void setBrightness(int level) {
        if (level < 0) level = 0;
        if (level > 100) level = 100;
        brightness = level;
        on = level > 0;
    }

    @Override
    public void status() {
        System.out.println("Light[" + id + "] " + name + " is " + (on ? "ON" : "OFF") + ", brightness: " + brightness + "%");
    }
}

// Thermostat.java
public class Thermostat extends AbstractDevice {
    private double temperature = 22.0; // default Celsius
    private boolean hvacOn = false;

    public Thermostat(String id, String name) {
        super(id, name);
    }

    public void setTemperature(double temp) {
        temperature = temp;
        hvacOn = true;
    }

    public double getTemperature() { return temperature; }

    public void turnOff() { hvacOn = false; }

    @Override
    public void status() {
        System.out.println("Thermostat[" + id + "] " + name + " set to " + temperature + "°C, HVAC is " + (hvacOn ? "ON" : "OFF"));
    }
}

// DoorLock.java
public class DoorLock extends AbstractDevice {
    private boolean locked = true;

    public DoorLock(String id, String name) {
        super(id, name);
    }

    public void lock() { locked = true; }

    public void unlock() { locked = false; }

    @Override
    public void status() {
        System.out.println("DoorLock[" + id + "] " + name + " is " + (locked ? "LOCKED" : "UNLOCKED"));
    }
}

// DeviceController.java
import java.util.*;

public class DeviceController {
    private final Map<String, Device> devices = new HashMap<>();

    public void register(Device device) {
        devices.put(device.getId(), device);
    }

    public Device get(String id) {
        return devices.get(id);
    }

    public void execute(String id, Runnable action) {
        Device device = devices.get(id);
        if (device != null) {
            action.run();
        } else {
            System.out.println("Device with id '" + id + "' not found.");
        }
    }

    public void showAllStatus() {
        devices.values().forEach(Device::status);
    }
}

// HomeAutomationSystem.java
public class HomeAutomationSystem {
    public static void main(String[] args) {
        DeviceController controller = new DeviceController();

        Light livingRoomLight = new Light("light-1", "Living Room Light");
        Thermostat mainThermostat = new Thermostat("thermo-1", "Main Thermostat");
        DoorLock frontDoor = new DoorLock("lock-1", "Front Door");

        controller.register(livingRoomLight);
        controller.register(mainThermostat);
        controller.register(frontDoor);

        // Simulate actions
        controller.execute("light-1", () -> {
            livingRoomLight.turnOn();
            livingRoomLight.setBrightness(75);
        });

        controller.execute("thermo-1", () -> mainThermostat.setTemperature(20.5));

        controller.execute("lock-1", frontDoor::unlock);

        // Show status of all devices
        controller.showAllStatus();
    }
}
```