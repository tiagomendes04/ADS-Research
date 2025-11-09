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