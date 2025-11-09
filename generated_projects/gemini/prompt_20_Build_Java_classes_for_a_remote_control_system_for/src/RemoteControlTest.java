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