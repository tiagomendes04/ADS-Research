```java
// Food.java
public class Food {
    private final String name;
    private final int nutrition; // amount by which hunger is reduced

    public Food(String name, int nutrition) {
        this.name = name;
        this.nutrition = nutrition;
    }

    public String getName() {
        return name;
    }

    public int getNutrition() {
        return nutrition;
    }
}
```

```java
// Toy.java
public class Toy {
    private final String name;
    private final int funValue;      // amount by which happiness is increased
    private int durability;          // decreases with each use

    public Toy(String name, int funValue, int durability) {
        this.name = name;
        this.funValue = funValue;
        this.durability = durability;
    }

    public String getName() {
        return name;
    }

    public int getFunValue() {
        return funValue;
    }

    public boolean isUsable() {
        return durability > 0;
    }

    public void use() {
        if (durability > 0) {
            durability--;
        }
    }
}
```

```java
// Pet.java
public class Pet {
    private final String name;
    private int health;      // 0 - 100
    private int hunger;      // 0 (full) - 100 (starving)
    private int happiness;   // 0 - 100

    public Pet(String name) {
        this.name = name;
        this.health = 100;
        this.hunger = 0;
        this.happiness = 80;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public void feed(Food food) {
        hunger = Math.max(0, hunger - food.getNutrition());
        health = Math.min(100, health + food.getNutrition() / 5);
    }

    public void play(Toy toy) {
        if (!toy.isUsable()) {
            return;
        }
        toy.use();
        happiness = Math.min(100, happiness + toy.getFunValue());
        hunger = Math.min(100, hunger + toy.getFunValue() / 2);
        health = Math.max(0, health - toy.getFunValue() / 10);
    }

    public void passTime(int minutes) {
        int hungerIncrease = minutes / 5;
        int happinessDecrease = minutes / 10;

        hunger = Math.min(100, hunger + hungerIncrease);
        happiness = Math.max(0, happiness - happinessDecrease);
        updateHealth();
    }

    private void updateHealth() {
        if (hunger > 80) {
            health = Math.max(0, health - (hunger - 80) / 2);
        }
        if (happiness < 20) {
            health = Math.max(0, health - (20 - happiness) / 2);
        }
        if (health == 0) {
            hunger = 100;
            happiness = 0;
        }
    }

    @Override
    public String toString() {
        return String.format("%s [Health=%d, Hunger=%d, Happiness=%d]",
                name, health, hunger, happiness);
    }
}
```

```java
// VirtualPetSimulator.java
import java.util.*;

public class VirtualPetSimulator {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Map<String, Food> FOOD_MENU = Map.of(
            "apple", new Food("Apple", 15),
            "bread", new Food("Bread", 25),
            "meat", new Food("Meat", 40)
    );
    private static final Map<String, Toy> TOY_MENU = Map.of(
            "ball", new Toy("Ball", 20, 10),
            "rope", new Toy("Rope", 15, 8),
            "laser", new Toy("Laser Pointer", 25, 5)
    );

    public static void main(String[] args) {
        System.out.print("Name your pet: ");
        String petName = SCANNER.nextLine().trim();
        Pet pet = new Pet(petName);
        System.out.println("Welcome, " + pet.getName() + "!");

        while (true) {
            System.out.println("\n" + pet);
            System.out.println("Choose action: [feed] [play] [wait] [quit]");
            String action = SCANNER.nextLine().trim().toLowerCase();

            switch (action) {
                case "feed":
                    handleFeeding(pet);
                    break;
                case "play":
                    handlePlaying(pet);
                    break;
                case "wait":
                    System.out.print("Minutes to wait: ");
                    int minutes = Integer.parseInt(SCANNER.nextLine().trim());
                    pet.passTime(minutes);
                    break;
                case "quit":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid action.");
            }

            if (pet.getHealth() == 0) {
                System.out.println(pet.getName() + " has passed away. Game over.");
                return;
            }
        }
    }

    private static void handleFeeding(Pet pet) {
        System.out.println("Available food: " + String.join(", ", FOOD_MENU.keySet()));
        System.out.print("What do you feed? ");
        String choice = SCANNER.nextLine().trim().toLowerCase();
        Food food = FOOD_MENU.get(choice);
        if (food != null) {
            pet.feed(food);
            System.out.println(pet.getName() + " ate a " + food.getName() + ".");
        } else {
            System.out.println("Unknown food.");
        }
    }

    private static void handlePlaying(Pet pet) {
        System.out.println("Available toys: " + String.join(", ", TOY_MENU.keySet()));
        System.out.print("What do you play with? ");
        String choice = SCANNER.nextLine().trim().toLowerCase();
        Toy toy = TOY_MENU.get(choice);
        if (toy != null && toy.isUsable()) {
            pet.play(toy);
            System.out.println(pet.getName() + " played with the " + toy.getName() + ".");
        } else {
            System.out.println("Toy not available or broken.");
        }
    }
}
```