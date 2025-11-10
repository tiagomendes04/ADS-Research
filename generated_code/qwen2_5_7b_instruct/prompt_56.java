```java
public class Device {
    private String id;
    private String name;
    private DeviceType type;

    public Device(String id, String name, DeviceType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeviceType getType() {
        return type;
    }

    public void turnOn() {
        System.out.println(name + " is turned on.");
    }

    public void turnOff() {
        System.out.println(name + " is turned off.");
    }
}

public enum DeviceType {
    LIGHT, HEATER, FAN, TV
}

public class LightDevice extends Device {
    public LightDevice(String id, String name) {
        super(id, name, DeviceType.LIGHT);
    }

    @Override
    public void turnOn() {
        System.out.println(name + " (Light) is turned on with bright lights.");
    }

    @Override
    public void turnOff() {
        System.out.println(name + " (Light) is turned off with no lights.");
    }
}

public class HeaterDevice extends Device {
    public HeaterDevice(String id, String name) {
        super(id, name, DeviceType.HEATER);
    }

    @Override
    public void turnOn() {
        System.out.println(name + " (Heater) is turned on to warm up.");
    }

    @Override
    public void turnOff() {
        System.out.println(name + " (Heater) is turned off to cool down.");
    }
}

public class FanDevice extends Device {
    public FanDevice(String id, String name) {
        super(id, name, DeviceType.FAN);
    }

    @Override
    public void turnOn() {
        System.out.println(name + " (Fan) is turned on to circulate air.");
    }

    @Override
    public void turnOff() {
        System.out.println(name + " (Fan) is turned off to stop circulation.");
    }
}

public class TVDevice extends Device {
    public TVDevice(String id, String name) {
        super(id, name, DeviceType.TV);
    }

    @Override
    public void turnOn() {
        System.out.println(name + " (TV) is turned on to watch the screen.");
    }

    @Override
    public void turnOff() {
        System.out.println(name + " (TV) is turned off to stop the screen.");
    }
}

public class HomeAutomationSystem {
    private List<Device> devices;

    public HomeAutomationSystem() {
        devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void turnOnAll() {
        for (Device device : devices) {
            device.turnOn();
        }
    }

    public void turnOffAll() {
        for (Device device : devices) {
            device.turnOff();
        }
    }

    public static void main(String[] args) {
        HomeAutomationSystem system = new HomeAutomationSystem();
        system.addDevice(new LightDevice("1", "Living Room Light"));
        system.addDevice(new HeaterDevice("2", "Living Room Heater"));
        system.addDevice(new FanDevice("3", "Living Room Fan"));
        system.addDevice(new TVDevice("4", "Living Room TV"));

        system.turnOnAll();
        system.turnOffAll();
    }
}
```