public class RemoteControlSystem {
    private Device[] devices;

    public RemoteControlSystem(Device[] devices) {
        this.devices = devices;
    }

    public void onDevice(Device device) {
        device.on();
    }

    public void offDevice(Device device) {
        device.off();
    }
}

public abstract class Device {
    protected boolean isOn;

    public void on() {
        isOn = true;
    }

    public void off() {
        isOn = false;
    }
}

public class Light extends Device {
    public Light() {
        super(new LightButton());
    }

    @Override
    public void on() {
        super.on();
        System.out.println("Light is on");
    }

    @Override
    public void off() {
        super.off();
    }
}

public class LightButton {
    public void press() {
        System.out.println("Button pressed");
    }

    public void release() {
        System.out.println("Button released");
    }
}

public class Fan extends Device {
    public Fan() {
        super(new FanButton());
    }

    @Override
    public void on() {
        super.on();
        System.out.println("Fan is on");
    }

    @Override
    public void off() {
        super.off();
    }
}

public class FanButton {
    public void press() {
        System.out.println("Button pressed");
    }

    public void release() {
        System.out.println("Button released");
    }
}