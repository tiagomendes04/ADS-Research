```java
public class RemoteControl {
    private int buttonPresses;
    private int powerState;

    public RemoteControl() {
        buttonPresses = 0;
        powerState = false;
    }

    public void pressButton() {
        buttonPresses++;
    }

    public void powerOn() {
        powerState = true;
    }

    public void powerOff() {
        powerState = false;
    }

    public int getButtonPresses() {
        return buttonPresses;
    }

    public boolean isPowerOn() {
        return powerState;
    }
}
```

```java
public class Light extends RemoteControl {
    public Light() {
        super();
    }

    public void turnOn() {
        powerOn();
        System.out.println("Light is on");
    }

    public void turnOff() {
        powerOff();
        System.out.println("Light is off");
    }

    public void adjustBrightness(int brightness) {
        if (isPowerOn()) {
            System.out.println("Adjusting brightness of light: " + brightness);
        } else {
            System.out.println("Cannot adjust brightness of light while it's off");
        }
    }
}
```

```java
public class Fan extends RemoteControl {
    public Fan() {
        super();
    }

    public void turnOn() {
        powerOn();
        System.out.println("Fan is on");
    }

    public void turnOff() {
        powerOff();
        System.out.println("Fan is off");
    }

    public void adjustSpeed(int speed) {
        if (isPowerOn()) {
            System.out.println("Adjusting fan speed: " + speed);
        } else {
            System.out.println("Cannot adjust fan speed while it's off");
        }
    }
}
```

```java
public class Device {
    private Light light;
    private Fan fan;

    public Device(Light light, Fan fan) {
        this.light = light;
        this.fan = fan;
    }

    public void turnLightOn() {
        light.turnOn();
    }

    public void turnLightOff() {
        light.turnOff();
    }

    public void turnFanOn() {
        fan.turnOn();
    }

    public void turnFanOff() {
        fan.turnOff();
    }

    public void adjustFanSpeed(int speed) {
        fan.adjustSpeed(speed);
    }
}
```