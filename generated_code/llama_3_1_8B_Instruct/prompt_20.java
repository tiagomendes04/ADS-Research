```java
public abstract class Device {
    private String id;
    private String name;

    public Device(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void turnOn();
    public abstract void turnOff();
}
```

```java
public class Light extends Device {
    private int brightness;

    public Light(String id, String name) {
        super(id, name);
        this.brightness = 0;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public void turnOn() {
        System.out.println("Light " + getName() + " turned on");
    }

    @Override
    public void turnOff() {
        System.out.println("Light " + getName() + " turned off");
    }
}
```

```java
public class Fan extends Device {
    private int speed;

    public Fan(String id, String name) {
        super(id, name);
        this.speed = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void turnOn() {
        System.out.println("Fan " + getName() + " turned on");
    }

    @Override
    public void turnOff() {
        System.out.println("Fan " + getName() + " turned off");
    }
}
```

```java
import java.util.HashMap;
import java.util.Map;

public class RemoteControl {
    private Map<String, Device> devices;

    public RemoteControl() {
        devices = new HashMap<>();
    }

    public void addDevice(Device device) {
        devices.put(device.getId(), device);
    }

    public void turnOnDevice(String deviceId) {
        Device device = devices.get(deviceId);
        if (device != null) {
            device.turnOn();
        }
    }

    public void turnOffDevice(String deviceId) {
        Device device = devices.get(deviceId);
        if (device != null) {
            device.turnOff();
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light light = new Light("1", "Living Room Light");
        Fan fan = new Fan("2", "Living Room Fan");

        remoteControl.addDevice(light);
        remoteControl.addDevice(fan);

        remoteControl.turnOnDevice("1");
        remoteControl.turnOffDevice("1");
        remoteControl.turnOnDevice("2");
        remoteControl.turnOffDevice("2");
    }
}
```