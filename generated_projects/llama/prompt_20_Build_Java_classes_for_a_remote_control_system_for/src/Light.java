public class Light extends Device {
    private boolean isOn;

    public Light(String id, String name) {
        super(id, name);
        this.isOn = false;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println("Light " + getName() + " turned on");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println("Light " + getName() + " turned off");
    }
}