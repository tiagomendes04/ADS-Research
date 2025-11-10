```java
import java.util.Scanner;

public class TurnBasedStrategyGame {
    private Unit[] units;
    private Scanner scanner;

    public TurnBasedStrategyGame(Unit[] units) {
        this.units = units;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            for (Unit unit : units) {
                System.out.println(unit + " turn");
                unit.takeTurn();
                if (isGameOver()) {
                    break;
                }
            }
            if (isGameOver()) {
                break;
            }
        }
    }

    private boolean isGameOver() {
        for (Unit unit : units) {
            if (unit.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Unit[] units = {
            new Unit("Unit1", 100, 50),
            new Unit("Unit2", 150, 30)
        };

        new TurnBasedStrategyGame(units).run();
    }
}

class Unit {
    private String name;
    private int health;
    private int attackPower;

    public Unit(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public void takeTurn() {
        System.out.println(name + " is thinking...");
        attackTarget();
    }

    public void attackTarget() {
        System.out.print(name + " attacks. Target? ");
        Scanner scanner = new Scanner(System.in);
        String targetName = scanner.nextLine();
        for (Unit unit : TurnBasedStrategyGame.this.units) {
            if (unit.name.equals(targetName)) {
                unit.takeDamage(attackPower);
                break;
            }
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health: " + health);
        if (health <= 0) {
            System.out.println(name + " is dead.");
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
```