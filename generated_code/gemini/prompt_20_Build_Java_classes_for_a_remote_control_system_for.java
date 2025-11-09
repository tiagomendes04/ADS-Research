```java
// Device interface
public interface Device {
    void on();
    void off();
    String getName();
}
```

```java
// Light class
public class Light implements Device {
    private String name;
    private boolean isOn;

    public Light(String name) {
        this.name = name;
        this.isOn = false;
    }

    @Override
    public void on() {
        isOn = true;
        System.out.println(name + " light is ON");
    }

    @Override
    public void off() {
        isOn = false;
        System.out.println(name + " light is OFF");
    }

    @Override
    public String getName() {
        return name;
    }
}
```

```java
// Fan class
public class Fan implements Device {
    private String name;
    private boolean isOn;
    private int speed; // 1 (low), 2 (medium), 3 (high)

    public Fan(String name) {
        this.name = name;
        this.isOn = false;
        this.speed = 0; // 0 = off
    }

    @Override
    public void on() {
        isOn = true;
        speed = 1; //Default to low speed on turn ON
        System.out.println(name + " fan is ON at speed " + speed);
    }

    @Override
    public void off() {
        isOn = false;
        speed = 0;
        System.out.println(name + " fan is OFF");
    }

    public void increaseSpeed() {
        if (isOn) {
            speed = Math.min(3, speed + 1);
            System.out.println(name + " fan speed increased to " + speed);
        } else {
            System.out.println("Fan is OFF.  Turn it on first.");
        }

    }

    public void decreaseSpeed() {
        if (isOn) {
            speed = Math.max(1, speed - 1);
            System.out.println(name + " fan speed decreased to " + speed);
        } else {
            System.out.println("Fan is OFF. Turn it on first.");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
}
```

```java
// Command interface
public interface Command {
    void execute();
    void undo();
}
```

```java
// On Command
public class OnCommand implements Command {
    private Device device;

    public OnCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.on();
    }

    @Override
    public void undo() {
        device.off();
    }
}
```

```java
// Off Command
public class OffCommand implements Command {
    private Device device;

    public OffCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.off();
    }

    @Override
    public void undo() {
        device.on();
    }
}
```

```java
// Increase Fan Speed Command
public class IncreaseFanSpeedCommand implements Command {
    private Fan fan;
    private int previousSpeed;

    public IncreaseFanSpeedCommand(Fan fan) {
        this.fan = fan;
        this.previousSpeed = fan.getSpeed();
    }

    @Override
    public void execute() {
        previousSpeed = fan.getSpeed();
        fan.increaseSpeed();
    }

    @Override
    public void undo() {
        //Set the fan to the previous speed without printing a message.
        while (fan.getSpeed() > previousSpeed) {
            fan.decreaseSpeed();
        }

        while (fan.getSpeed() < previousSpeed) {
            fan.increaseSpeed();
        }

    }
}
```

```java
// Decrease Fan Speed Command
public class DecreaseFanSpeedCommand implements Command {
    private Fan fan;
    private int previousSpeed;

    public DecreaseFanSpeedCommand(Fan fan) {
        this.fan = fan;
        this.previousSpeed = fan.getSpeed();
    }

    @Override
    public void execute() {
        previousSpeed = fan.getSpeed();
        fan.decreaseSpeed();
    }

    @Override
    public void undo() {
        //Set the fan to the previous speed without printing a message.
         while (fan.getSpeed() > previousSpeed) {
            fan.decreaseSpeed();
        }

        while (fan.getSpeed() < previousSpeed) {
            fan.increaseSpeed();
        }
    }
}
```

```java
// Remote Control
public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command[] undoCommands; // Store the last executed command for each slot
    private static final int NUM_SLOTS = 7; // Number of slots on the remote

    public RemoteControl() {
        onCommands = new Command[NUM_SLOTS];
        offCommands = new Command[NUM_SLOTS];
        undoCommands = new Command[NUM_SLOTS];

        // Initialize all commands to NoCommand (do nothing)
        Command noCommand = new NoCommand();
        for (int i = 0; i < NUM_SLOTS; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
            undoCommands[i] = noCommand;
        }
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

     public void setCommand(int slot, Command onCommand, Command offCommand, Command undoCommand) { //Overloaded version for fan increase/decrease.
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
        undoCommands[slot] = undoCommand;

    }

    public void onButtonWasPushed(int slot) {
        if(onCommands[slot] != null){
            onCommands[slot].execute();
            undoCommands[slot] = onCommands[slot]; // Store the command for undo
        }

    }

    public void offButtonWasPushed(int slot) {
        if (offCommands[slot] != null) {
            offCommands[slot].execute();
            undoCommands[slot] = offCommands[slot]; // Store the command for undo
        }

    }

    public void undoButtonWasPushed(int slot) {
        if(undoCommands[slot] != null) {
            undoCommands[slot].undo();
            undoCommands[slot] = new NoCommand(); // Reset undo command after it is used.
        }

    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n------ Remote Control ------\n");
        for (int i = 0; i < NUM_SLOTS; i++) {
            stringBuilder.append("[slot " + i + "] " + onCommands[i].getClass().getSimpleName()
                    + "    " + offCommands[i].getClass().getSimpleName() + "\n");
        }
        stringBuilder.append("[undo] " + undoCommands.getClass().getSimpleName());
        return stringBuilder.toString();
    }
}
```

```java
// No Command (Do Nothing)
class NoCommand implements Command {
    public void execute() { }
    public void undo() { }
}
```

```java
// Example Usage
public class RemoteControlTest {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light livingRoomLight = new Light("Living Room");
        Fan ceilingFan = new Fan("Ceiling");

        OnCommand livingRoomLightOn = new OnCommand(livingRoomLight);
        OffCommand livingRoomLightOff = new OffCommand(livingRoomLight);
        OnCommand ceilingFanOn = new OnCommand(ceilingFan);
        OffCommand ceilingFanOff = new OffCommand(ceilingFan);
        IncreaseFanSpeedCommand ceilingFanIncrease = new IncreaseFanSpeedCommand(ceilingFan);
        DecreaseFanSpeedCommand ceilingFanDecrease = new DecreaseFanSpeedCommand(ceilingFan);

        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, ceilingFanOn, ceilingFanOff, ceilingFanIncrease); //Using overloaded version to support the undo action for fan increase
        remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff, ceilingFanDecrease); //Using overloaded version to support the undo action for fan decrease.


        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        System.out.println(remoteControl);
        remoteControl.undoButtonWasPushed(0);

        remoteControl.onButtonWasPushed(1);
        remoteControl.onButtonWasPushed(1);
        remoteControl.onButtonWasPushed(1);
        System.out.println("Fan Speed: " + ceilingFan.getSpeed());
        remoteControl.offButtonWasPushed(1);
        remoteControl.undoButtonWasPushed(1);

        remoteControl.onButtonWasPushed(2);
        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);

        remoteControl.undoButtonWasPushed(2); //Decrease does nothing because fan is off.
        System.out.println("Fan Speed: " + ceilingFan.getSpeed());

        remoteControl.undoButtonWasPushed(1); //Try to undo when no command was executed for fan increase speed;
    }
}
```