public abstract class Device {
    private String id;
    private String name;

    public Device(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void turnOn();
    public abstract void turnOff();
}