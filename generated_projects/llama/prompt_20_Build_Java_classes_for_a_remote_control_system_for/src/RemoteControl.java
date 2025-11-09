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