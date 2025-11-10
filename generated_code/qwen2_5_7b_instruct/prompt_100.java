```java
public class VirtualPet {
    private String name;
    private int health;
    private int happiness;

    public VirtualPet(String name) {
        this.name = name;
        this.health = 100;
        this.happiness = 50;
    }

    public void feed() {
        health += 20;
        happiness += 10;
        System.out.println(name + " was fed. Health: " + health + ", Happiness: " + happiness);
    }

    public void play() {
        health += 10;
        happiness += 20;
        System.out.println(name + " played. Health: " + health + ", Happiness: " + happiness);
    }

    public void checkStatus() {
        System.out.println(name + "'s status: Health: " + health + ", Happiness: " + happiness);
    }

    public void heal() {
        health = 100;
        System.out.println(name + " was healed. Health: " + health);
    }

    public void showMenu() {
        System.out.println("1. Feed " + name);
        System.out.println("2. Play with " + name);
        System.out.println("3. Check " + name + "'s status");
        System.out.println("4. Heal " + name);
        System.out.println("5. Exit");
    }
}

public class VirtualPetSimulator {
    public static void main(String[] args) {
        VirtualPet pet = new VirtualPet("Buddy");
        int choice;
        do {
            pet.showMenu();
            choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 1:
                    pet.feed();
                    break;
                case 2:
                    pet.play();
                    break;
                case 3:
                    pet.checkStatus();
                    break;
                case 4:
                    pet.heal();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);
    }
}
```