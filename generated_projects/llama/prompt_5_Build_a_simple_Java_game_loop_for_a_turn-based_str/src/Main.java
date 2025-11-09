import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Unit class
class Unit {
    private String name;
    private int health;
    private int attack;

    public Unit(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
        }
    }

    public void move(int x, int y) {
        System.out.println(name + " moved to (" + x + ", " + y + ")");
    }

    public void attack(Unit target) {
        target.takeDamage(attack);
        System.out.println(name + " attacked " + target.getName() + " for " + attack + " damage");
    }
}

// Game class
class Game {
    private List<Unit> units;
    private int turn;

    public Game() {
        units = new ArrayList<>();
        turn = 0;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Turn " + (turn + 1));
            for (Unit unit : units) {
                System.out.println(unit.getName() + " (Health: " + unit.getHealth() + ")");
            }
            System.out.println("Enter unit number to move or attack:");
            for (int i = 0; i < units.size(); i++) {
                System.out.println((i + 1) + ". " + units.get(i).getName());
            }
            int choice = scanner.nextInt() - 1;
            if (choice >= 0 && choice < units.size()) {
                Unit unit = units.get(choice);
                System.out.println("Enter action (m to move, a to attack):");
                String action = scanner.next();
                if (action.equalsIgnoreCase("m")) {
                    System.out.println("Enter new x and y coordinates:");
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    unit.move(x, y);
                } else if (action.equalsIgnoreCase("a")) {
                    System.out.println("Enter unit number to attack:");
                    for (int i = 0; i < units.size(); i++) {
                        System.out.println((i + 1) + ". " + units.get(i).getName());
                    }
                    int targetChoice = scanner.nextInt() - 1;
                    if (targetChoice >= 0 && targetChoice < units.size()) {
                        Unit target = units.get(targetChoice);
                        if (target != unit) {
                            unit.attack(target);
                        } else {
                            System.out.println("Cannot attack self.");
                        }
                    }
                }
            }
            turn++;
            if (turn >= units.size()) {
                turn = 0;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.addUnit(new Unit("Unit 1", 100, 10));
        game.addUnit(new Unit("Unit 2", 100, 10));
        game.startGame();
    }
}