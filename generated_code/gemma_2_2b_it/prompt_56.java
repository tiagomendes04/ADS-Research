```java
import java.util.HashMap;
import java.util.Map;

class Device {
    String name;
    String type;
    boolean enabled;

    public Device(String name, String type) {
        this.name = name;
        this.type = type;
        this.enabled = false;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "Device{" +
               "name='" + name + '\'' +
               ", type='" + type + '\'' +
               ", enabled=" + enabled +
               '}';
    }
}

class ControlHub {
    Map<String, Device> devices = new HashMap<>();

    public void addDevice(String name, String type) {
        devices.put(name, new Device(name, type));
    }

    public void controlDevice(String name, String command) {
        Device device = devices.get(name);
        if (device != null) {
            switch (command) {
                case "turnOn":
                    device.enable();
                    break;
                case "turnOff":
                    device.disable();
                    break;
                default:
                    System.out.println("Invalid command for device: " + name);
            }
        } else {
            System.out.println("Device not found: " + name);
        }
    }

    public String[] getDevicesList() {
        String[] devicesList = new String[devices.size()];
        int i = 0;
        for (String deviceName : devices.keySet()) {
            devicesList[i++] = deviceName;
        }
        return devicesList;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        ControlHub hub = new ControlHub();
        hub.addDevice("Lamp", "Light");
        hub.addDevice("Thermostat", "HVAC");
        
        System.out.println("Devices list: " + hub.getDevicesList());

        hub.controlDevice("Lamp", "turnOn");
        hub.controlDevice("Thermostat", "turnOn");
        
        System.out.println("Lamp status: " + hub.devices.get("Lamp").isEnabled());
        System.out.println("Thermostat status: " + hub.devices.get("Thermostat").isEnabled());
    }
}
```