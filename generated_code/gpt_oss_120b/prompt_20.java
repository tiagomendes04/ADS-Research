```java
// Device.java
public interface Device {
    void turnOn();
    void turnOff();
    boolean isOn();
    String getName();
}
```

```java
// Light.java
public class Light implements Device {
    private final String name;
    private boolean on = false;

    public Light(String name) {
        this.name = name;
    }

    @Override
    public void turnOn() {
        on = true;
        System.out.println(name + " is now ON.");
    }

    @Override
    public void turnOff() {
        on = false;
        System.out.println(name + " is now OFF.");
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public String getName() {
        return name;
    }
}
```

```java
// Fan.java
public class Fan implements Device {
    private final String name;
    private boolean on = false;
    private int speed = 0; // 0 = off, 1-3 = speed levels

    public Fan(String name) {
        this.name = name;
    }

    @Override
    public void turnOn() {
        on = true;
        speed = 1;
        System.out.println(name + " is now ON at speed " + speed + ".");
    }

    @Override
    public void turnOff() {
        on = false;
        speed = 0;
        System.out.println(name + " is now OFF.");
    }

    public void setSpeed(int speed) {
        if (!on) {
            System.out.println(name + " is OFF. Turn it on first.");
            return;
        }
        if (speed < 1 || speed > 3) {
            System.out.println("Invalid speed. Choose between 1 and 3.");
            return;
        }
        this.speed = speed;
        System.out.println(name + " speed set to " + speed + ".");
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public String getName() {
        return name;
    }
}
```

```java
// RemoteControl.java
public interface RemoteControl {
    void pressPowerButton(Device device);
    void pressIncreaseButton(Fan fan);
    void pressDecreaseButton(Fan fan);
}
```

```java
// SimpleRemoteControl.java
public class SimpleRemoteControl implements RemoteControl {

    @Override
    public void pressPowerButton(Device device) {
        if (device.isOn()) {
            device.turnOff();
        } else {
            device.turnOn();
        }
    }

    @Override
    public void pressIncreaseButton(Fan fan) {
        if (!fan.isOn()) {
            System.out.println(fan.getName() + " is OFF. Turn it on first.");
            return;
        }
        int newSpeed = fan.getSpeed() + 1;
        fan.setSpeed(newSpeed);
    }

    @Override
    public void pressDecreaseButton(Fan fan) {
        if (!fan.isOn()) {
            System.out.println(fan.getName() + " is OFF. Turn it on first.");
            return;
        }
        int newSpeed = fan.getSpeed() - 1;
        fan.setSpeed(newSpeed);
    }
}
```

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        Device livingRoomLight = new Light("Living Room Light");
        Fan bedroomFan = new Fan("Bedroom Fan");

        RemoteControl remote = new SimpleRemoteControl();

        // Toggle light
        remote.pressPowerButton(livingRoomLight); // ON
        remote.pressPowerButton(livingRoomLight); // OFF

        // Control fan
        remote.pressPowerButton(bedroomFan); // ON at speed 1
        remote.pressIncreaseButton(bedroomFan); // speed 2
        remote.pressIncreaseButton(bedroomFan); // speed 3
        remote.pressIncreaseButton(bedroomFan); // invalid, stays at 3
        remote.pressDecreaseButton(bedroomFan); // speed 2
        remote.pressPowerButton(bedroomFan); // OFF
    }
}
```