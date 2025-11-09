public class Fan extends Device {
    private boolean isOn;
    private int speed;

    public Fan(String id, String name) {
        super(id, name);
        this.isOn = false;
        this.speed = 0;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        System.out.println("Fan " + getName() + " turned on");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        System.out.println("Fan " + getName() + " turned off");
    }

    public void increaseSpeed() {
        this.speed++;
        System.out.println("Fan " + getName() + " speed increased to " + speed);
    }

    public void decreaseSpeed() {
        if (speed > 0) {
            this.speed--;
            System.out.println("Fan " + getName() + " speed decreased to " + speed);
        }
    }
}