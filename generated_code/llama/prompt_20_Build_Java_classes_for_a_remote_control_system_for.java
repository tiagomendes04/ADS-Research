**Device.java**
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

**Light.java**
```java
public class Light extends Device {
    private boolean isOn;

    public Light(String id, String name) {
        super(id, name);
        this.isOn = false;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println("Light " + getName() + " turned on");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println("Light " + getName() + " turned off");
    }
}
```

**Fan.java**
```java
public class Fan extends Device {
    private boolean isOn;
    private int speed;

    public Fan(String id, String name) {
        super(id, name);
        this.isOn = false;
        this.speed = 0;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println("Fan " + getName() + " turned on");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println("Fan " + getName() + " turned off");
    }

    public void increaseSpeed() {
        this.speed++;
        System.out.println("Fan " + getName() + " speed increased to " + speed);
    }

    public void decreaseSpeed() {
        if (speed > 0) {
            this.speed--;
            System.out.println("Fan " + getName() + " speed decreased to " + speed);
        }
    }
}
```

**RemoteControl.java**
```java
import java.util.ArrayList;
import java.util.List;

public class RemoteControl {
    private List<Device> devices;

    public RemoteControl() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void turnOnDevice(String deviceId) {
        for (Device device : devices) {
            if (device.getId().equals(deviceId)) {
                device.turnOn();
                return;
            }
        }
        System.out.println("Device not found");
    }

    public void turnOffDevice(String deviceId) {
        for (Device device : devices) {
            if (device.getId().equals(deviceId)) {
                device.turnOff();
                return;
            }
        }
        System.out.println("Device not found");
    }

    public void increaseFanSpeed(String deviceId) {
        for (Device device : devices) {
            if (device.getId().equals(deviceId) && device instanceof Fan) {
                ((Fan) device).increaseSpeed();
                return;
            }
        }
        System.out.println("Device not found or not a fan");
    }

    public void decreaseFanSpeed(String deviceId) {
        for (Device device : devices) {
            if (device.getId().equals(deviceId) && device instanceof Fan) {
                ((Fan) device).decreaseSpeed();
                return;
            }
        }
        System.out.println("Device not found or not a fan");
    }
}
```

**Main.java**
```java
public class Main {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light light = new Light("L1", "Living Room Light");
        Fan fan = new Fan("F1", "Living Room Fan");

        remoteControl.addDevice(light);
        remoteControl.addDevice(fan);

        remoteControl.turnOnDevice("L1");
        remoteControl.turnOffDevice("L1");

        remoteControl.turnOnDevice("F1");
        remoteControl.increaseFanSpeed("F1");
        remoteControl.decreaseFanSpeed("F1");
        remoteControl.turnOffDevice("F1");
    }
}
```