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
        this.health = 50;
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

    public void feed() {
        hunger = Math.min(100, hunger + 10);
        System.out.println("You fed " + name + ". Hunger level is now " + hunger + "%.");
    }

    public void play() {
        happiness = Math.min(100, happiness + 10);
        System.out.println("You played with " + name + ". Happiness level is now " + happiness + "%.");
    }

    public void rest() {
        health = Math.min(100, health + 10);
        System.out.println("You let " + name + " rest. Health level is now " + health + "%.");
    }

    public void checkStatus() {
        System.out.println("Name: " + name);
        System.out.println("Hunger: " + hunger + "%");
        System.out.println("Happiness: " + happiness + "%");
        System.out.println("Health: " + health + "%");
    }

    public void tick() {
        hunger = Math.max(0, hunger - 2);
        happiness = Math.max(0, happiness - 2);
        health = Math.max(0, health - 2);
        System.out.println("Time passed. Hunger level is now " + hunger + "%, Happiness level is now " + happiness + "%, Health level is now " + health + "%.");
    }
}
```

```java
import java.util.Scanner;

public class PetSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VirtualPet pet = new VirtualPet("Buddy");

        while (true) {
            System.out.println("1. Feed " + pet.getName());
            System.out.println("2. Play with " + pet.getName());
            System.out.println("3. Let " + pet.getName() + " rest");
            System.out.println("4. Check status");
            System.out.println("5. Tick time");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    pet.feed();
                    break;
                case 2:
                    pet.play();
                    break;
                case 3:
                    pet.rest();
                    break;
                case 4:
                    pet.checkStatus();
                    break;
                case 5:
                    pet.tick();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 6.");
            }
        }
    }
}
```