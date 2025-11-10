```java
import java.util.*;

class Unit {
    String name;
    int health;
    int attack;

    public Unit(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.attack = attack;
    }

    public void move(Unit target, int distance) {
        int newHealth = target.health - distance;
        if (newHealth < 0) {
            newHealth = 0;
        }
        target.health = newHealth;
    }

    public void attack(Unit target) {
        int newHealth = target.health - attack;
        if (newHealth < 0) {
            newHealth = 0;
        }
        target.health = newHealth;
    }
}

class Game {
    List<Unit> units;
    int turn;

    public Game() {
        this.units = new ArrayList<>();
        this.turn = 0;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void moveUnit(Unit unit, Unit target, int distance) {
        unit.move(target, distance);
        System.out.println(unit.name + " moved " + distance + " units towards " + target.name);
    }

    public void attackUnit(Unit unit, Unit target) {
        unit.attack(target);
        System.out.println(unit.name + " attacked " + target.name + " for " + unit.attack + " damage");
    }

    public void playTurn() {
        System.out.println("Turn " + (turn + 1));
        for (Unit unit : units) {
            System.out.println(unit.name + " (health: " + unit.health + ")");
        }
        System.out.print("Choose a unit to move or attack (enter 'm' to move, 'a' to attack): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Unit chosenUnit = null;
        for (Unit unit : units) {
            if (unit.name.equals(input)) {
                chosenUnit = unit;
                break;
            }
        }
        if (chosenUnit != null) {
            System.out.print("Enter target's name: ");
            input = scanner.nextLine();
            Unit target = null;
            for (Unit unit : units) {
                if (unit.name.equals(input)) {
                    target = unit;
                    break;
                }
            }
            if (target != null) {
                System.out.print("Enter distance to move (or 0 to attack): ");
                int distance = scanner.nextInt();
                if (distance > 0) {
                    moveUnit(chosenUnit, target, distance);
                } else {
                    attackUnit(chosenUnit, target);
                }
                turn++;
                scanner.close();
            } else {
                System.out.println("Invalid target");
            }
        } else {
            System.out.println("Invalid unit");
        }
    }

    public void playGame() {
        while (true) {
            playTurn();
            System.out.print("Enter 'q' to quit or any key to continue: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("q")) {
                break;
            }
            scanner.close();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.addUnit(new Unit("Unit1", 100, 10));
        game.addUnit(new Unit("Unit2", 100, 10));
        game.playGame();
    }
}
```