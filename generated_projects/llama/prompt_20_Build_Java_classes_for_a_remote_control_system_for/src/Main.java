public class Main {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light light = new Light("L1", "Living Room Light");
        Fan fan = new Fan("F1", "Living Room Fan");

        remoteControl.addDevice(light);
        remoteControl.addDevice(fan);

        remoteControl.turnOnDevice("L1");
        remoteControl.turnOffDevice("L1");

        remoteControl.turnOnDevice("F1");
        remoteControl.increaseFanSpeed("F1");
        remoteControl.decreaseFanSpeed("F1");
        remoteControl.turnOffDevice("F1");
    }
}