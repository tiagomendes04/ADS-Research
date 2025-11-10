```java
public interface Device {
    void on();
    void off();
}

public class Light implements Device {
    private String location;

    public Light(String location) {
        this.location = location;
    }

    @Override
    public void on() {
        System.out.println(location + " light is on");
    }

    @Override
    public void off() {
        System.out.println(location + " light is off");
    }
}

public class Fan implements Device {
    private String location;
    private int speed;

    public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;
    public static final int OFF = 0;

    public Fan(String location) {
        this.location = location;
        this.speed = OFF;
    }

    public void high() {
        speed = HIGH;
        System.out.println(location + " fan is on high");
    }

    public void medium() {
        speed = MEDIUM;
        System.out.println(location + " fan is on medium");
    }

    public void low() {
        speed = LOW;
        System.out.println(location + " fan is on low");
    }

    @Override
    public void off() {
        speed = OFF;
        System.out.println(location + " fan is off");
    }

    public int getSpeed() {
        return speed;
    }
}

public interface Command {
    void execute();
    void undo();
}

public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}

public class FanHighCommand implements Command {
    private Fan fan;
    private int prevSpeed;

    public FanHighCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.high();
    }

    @Override
    public void undo() {
        if (prevSpeed == Fan.HIGH) {
            fan.high();
        } else if (prevSpeed == Fan.MEDIUM) {
            fan.medium();
        } else if (prevSpeed == Fan.LOW) {
            fan.low();
        } else if (prevSpeed == Fan.OFF) {
            fan.off();
        }
    }
}

public class FanMediumCommand implements Command {
    private Fan fan;
    private int prevSpeed;

    public FanMediumCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.medium();
    }

    @Override
    public void undo() {
        if (prevSpeed == Fan.HIGH) {
            fan.high();
        } else if (prevSpeed == Fan.MEDIUM) {
            fan.medium();
        } else if (prevSpeed == Fan.LOW) {
            fan.low();
        } else if (prevSpeed == Fan.OFF) {
            fan.off();
        }
    }
}

public class FanLowCommand implements Command {
    private Fan fan;
    private int prevSpeed;

    public FanLowCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.low();
    }

    @Override
    public void undo() {
        if (prevSpeed == Fan.HIGH) {
            fan.high();
        } else if (prevSpeed == Fan.MEDIUM) {
            fan.medium();
        } else if (prevSpeed == Fan.LOW) {
            fan.low();
        } else if (prevSpeed == Fan.OFF) {
            fan.off();
        }
    }
}

public class FanOffCommand implements Command {
    private Fan fan;
    private int prevSpeed;

    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        prevSpeed = fan.getSpeed();
        fan.off();
    }

    @Override
    public void undo() {
        if (prevSpeed == Fan.HIGH) {
            fan.high();
        } else if (prevSpeed == Fan.MEDIUM) {
            fan.medium();
        } else if (prevSpeed == Fan.LOW) {
            fan.low();
        } else if (prevSpeed == Fan.OFF) {
            fan.off();
        }
    }
}

public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;