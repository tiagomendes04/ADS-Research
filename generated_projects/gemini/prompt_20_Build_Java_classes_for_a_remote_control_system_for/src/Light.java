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