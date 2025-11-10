```java
public class Device {
    private String name;
    private String type;
    private String status;

    public Device(String name, String type, String status) {
        this.name = name;
        this.type = type;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Control {
    private List<Device> devices;

    public Control() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
    }

    public Device getDevice(String name) {
        for (Device device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    public boolean hasDevice(String name) {
        return devices.stream().anyMatch(device -> device.getName().equals(name));
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Hub {
    private List<Control> controls;

    public Hub() {
        this.controls = new ArrayList<>();
    }

    public void addControl(Control control) {
        controls.add(control);
    }

    public void removeControl(Control control) {
        controls.remove(control);
    }

    public List<Control> getControls() {
        return controls;
    }
}
```

```java
import java.util.HashMap;
import java.util.Map;

public class DeviceController {
    private Map<String, Device> devices;
    private Map<String, Control> controls;

    public DeviceController() {
        this.devices = new HashMap<>();
        this.controls = new HashMap<>();
    }

    public void addDevice(Device device) {
        devices.put(device.getName(), device);
    }

    public void removeDevice(Device device) {
        devices.remove(device.getName());
    }

    public Device getDevice(String name) {
        return devices.get(name);
    }

    public void addControl(Control control) {
        controls.put(name(), control);
    }

    public void removeControl(Control control) {
        controls.remove(name(), control);
    }

    public List<Control> getControls(String name) {
        return controls.get(name());
    }
}
```