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