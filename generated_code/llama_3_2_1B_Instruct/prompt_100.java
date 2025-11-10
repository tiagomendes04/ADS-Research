```java
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String name;
    private int age;
    private int health;

    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
        this.health = 100;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHealth() {
        return health;
    }

    public void feed() {
        this.health += 10;
        System.out.println(name + " has eaten a nutritious meal and gained 10 health points.");
    }

    public void play() {
        this.health += 20;
        System.out.println(name + " has played outside and gained 20 health points.");
    }

    public void heal() {
        this.health = Math.max(0, this.health - 20);
        System.out.println(name + " has recovered from a minor illness and gained 20 health points.");
    }

    public void checkHealth() {
        System.out.println(name + " has " + this.health + " health points left.");
    }
}

public class VirtualPet {
    private List<Pet> pets;

    public VirtualPet() {
        this.pets = new ArrayList<>();
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
    }

    public void feedAll() {
        for (Pet pet : this.pets) {
            pet.feed();
        }
    }

    public void playAll() {
        for (Pet pet : this.pets) {
            pet.play();
        }
    }

    public void healAll() {
        for (Pet pet : this.pets) {
            pet.heal();
        }
    }

    public void checkHealth() {
        for (Pet pet : this.pets) {
            pet.checkHealth();
        }
    }

    public static void main(String[] args) {
        VirtualPet pet = new VirtualPet();

        Pet dog = new Pet("Dog", 3);
        pet.addPet(dog);

        Pet cat = new Pet("Cat", 2);
        pet.addPet(cat);

        pet.feedAll();
        pet.playAll();
        pet.healAll();

        pet.checkHealth();
    }
}
```