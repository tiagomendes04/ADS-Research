```java
public class Device {
    private String name;
    private boolean isOn;

    public Device(String name) {
        this.name = name;
        this.isOn = false;
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
}

public class Light extends Device {
    public Light(String name) {
        super(name);
    }
}

public class Fan extends Device {
    private int speed;

    public Fan(String name) {
        super(name);
        this.speed = 1;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}

public class RemoteControl {
    private Device device;

    public RemoteControl(Device device) {
        this.device = device;
    }

    public void pressOnButton() {
        device.turnOn();
    }

    public void pressOffButton() {
        device.turnOff();
    }

    public void pressSpeedUpButton() {
        if (device instanceof Fan) {
            Fan fan = (Fan) device;
            fan.setSpeed(fan.getSpeed() + 1);
        }
    }

    public void pressSpeedDownButton() {
        if (device instanceof Fan) {
            Fan fan = (Fan) device;
            fan.setSpeed(fan.getSpeed() - 1);
        }
    }

    public String getStatus() {
        return "Device: " + device.getName() + ", On: " + device.isOn() + ", Speed: " + ((device instanceof Fan) ? ((Fan) device).getSpeed() : "");
    }
}
```