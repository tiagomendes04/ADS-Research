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