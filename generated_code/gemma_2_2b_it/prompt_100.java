```java
import java.util.Scanner;

public class VirtualPet {

    private String name;
    private int hunger;
    private int happiness;
    private int energy;

    public VirtualPet(String name) {
        this.name = name;
        this.hunger = 50;
        this.happiness = 50;
        this.energy = 50;
    }

    public void feed() {
        hunger -= 20;
        happiness += 10;
        energy += 10;
        System.out.println(name + " is happy and full of energy after a nice meal!");
    }

    public void play() {
        energy -= 10;
        happiness += 20;
        hunger += 5;
        System.out.println(name + " had a great time playing!");
    }

    public void checkStatus() {
        System.out.println("Name: " + name);
        System.out.println("Hunger: " + hunger);
        System.out.println("Happiness: " + happiness);
        System.out.println("Energy: " + energy);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VirtualPet pet = new VirtualPet("Fluffy");

        while (true) {
            System.out.println("\nWhat would you like to do? (Feed, Play, Check, Exit)");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("feed")) {
                pet.feed();
            } else if (choice.equals("play")) {
                pet.play();
            } else if (choice.equals("check")) {
                pet.checkStatus();
            } else if (choice.equals("exit")) {
                System.out.println("Thank you for playing!");
                scanner.close();
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
```

```java
import java.util.Random;

public class PetHealthTracker {

    private int hunger;
    private int happiness;
    private int energy;

    public PetHealthTracker() {
        this.hunger = 50;
        this.happiness = 50;
        this.energy = 50;
    }

    public void feed() {
        hunger -= 20;
        happiness += 10;
        energy += 10;
    }

    public void play() {
        energy -= 10;
        happiness += 20;
        hunger += 5;
    }

    public void checkStatus() {
        System.out.println("Hunger: " + hunger);
        System.out.println("Happiness: " + happiness);
        System.out.println("Energy: " + energy);
    }
}
```