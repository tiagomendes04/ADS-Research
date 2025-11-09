```java
public interface RemoteControl {
    void turnOn();
    void turnOff();
    void setSpeed(int speed);
    void setBrightness(int brightness);
}

public class Light implements RemoteControl {
    private boolean isOn;
    private int brightness;

    @Override
    public void turnOn() {
        isOn = true;
    }

    @Override
    public void turnOff() {
        isOn = false;
    }

    @Override
    public void setSpeed(int speed) {
        // Not applicable for lights
    }

    @Override
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getBrightness() {
        return brightness;
    }
}

public class Fan implements RemoteControl {
    private boolean isOn;
    private int speed;

    @Override
    public void turnOn() {
        isOn = true;
    }

    @Override
    public void turnOff() {
        isOn = false;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void setBrightness(int brightness) {
        // Not applicable for fans
    }

    public boolean isOn() {
        return isOn;
    }

    public int getSpeed() {
        return speed;
    }
}

public class RemoteControlSystem {
    private Map<String, RemoteControl> devices;

    public RemoteControlSystem() {
        devices = new HashMap<>();
    }

    public void addDevice(String name, RemoteControl device) {
        devices.put(name, device);
    }

    public void removeDevice(String name) {
        devices.remove(name);
    }

    public RemoteControl getDevice(String name) {
        return devices.get(name);
    }

    public void controlDevice(String name, String command, int value) {
        RemoteControl device = devices.get(name);
        if (device != null) {
            switch (command.toLowerCase()) {
                case "on":
                    device.turnOn();
                    break;
                case "off":
                    device.turnOff();
                    break;
                case "speed":
                    device.setSpeed(value);
                    break;
                case "brightness":
                    device.setBrightness(value);
                    break;
            }
        }
    }
}
```