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