```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Unit {
    private int x;
    private int y;
    private int health;

    public Unit(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public void attack(Unit target) {
        target.takeDamage(10);
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) this.health = 0;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }
}

public class TurnBasedGame {
    private List<Unit> units;
    private Scanner scanner;

    public TurnBasedGame() {
        units = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeUnits();
    }

    private void initializeUnits() {
        units.add(new Unit(0, 0, 100));
        units.add(new Unit(5, 5, 100));
    }

    public void start() {
        while (units.size() > 1) {
            for (Unit unit : units) {
                if (!unit.isAlive()) continue;
                System.out.println("Unit at (" + unit.getX() + ", " + unit.getY() + ") with health " + unit.getHealth() + ":");
                System.out.println("1. Move");
                System.out.println("2. Attack");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter new X and Y coordinates:");
                        int newX = scanner.nextInt();
                        int newY = scanner.nextInt();
                        unit.move(newX, newY);
                        break;
                    case 2:
                        System.out.println("Select target unit index (0-" + (units.size() - 2) + "):");
                        int targetIndex = scanner.nextInt();
                        if (targetIndex != units.indexOf(unit)) {
                            unit.attack(units.get(targetIndex));
                        } else {
                            System.out.println("Cannot attack self.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
            removeDeadUnits();
        }
        System.out.println("Game Over! Winner is unit at (" + units.get(0).getX() + ", " + units.get(0).getY() + ")");
        scanner.close();
    }

    private void removeDeadUnits() {
        units.removeIf(unit -> !unit.isAlive());
    }

    public static void main(String[] args) {
        TurnBasedGame game = new TurnBasedGame();
        game.start();
    }
}
```