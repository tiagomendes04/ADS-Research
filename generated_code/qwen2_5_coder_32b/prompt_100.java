```java
public class VirtualPet {
    private String name;
    private int hunger;
    private int happiness;
    private int health;

    public VirtualPet(String name) {
        this.name = name;
        this.hunger = 50;
        this.happiness = 50;
        this.health = 100;
    }

    public void feed() {
        if (hunger > 10) {
            hunger -= 10;
            happiness += 5;
        } else {
            hunger = 0;
            happiness += 5;
        }
    }

    public void play() {
        if (happiness < 95) {
            happiness += 10;
            hunger += 5;
        } else {
            happiness = 100;
            hunger += 5;
        }
    }

    public void checkHealth() {
        if (hunger > 70 || happiness < 30) {
            health -= 5;
        }
        if (health < 20) {
            System.out.println(name + " is very sick and needs immediate care!");
        } else if (health < 50) {
            System.out.println(name + " is not feeling well.");
        } else {
            System.out.println(name + " is healthy.");
        }
    }

    public String getName() {
        return name;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getHealth() {
        return health;
    }

    public void displayStatus() {
        System.out.println("Name: " + name);
        System.out.println("Hunger: " + hunger);
        System.out.println("Happiness: " + happiness);
        System.out.println("Health: " + health);
    }
}

public class PetSimulation {
    public static void main(String[] args) {
        VirtualPet pet = new VirtualPet("Buddy");
        pet.displayStatus();
        pet.feed();
        pet.play();
        pet.checkHealth();
        pet.displayStatus();
    }
}
```