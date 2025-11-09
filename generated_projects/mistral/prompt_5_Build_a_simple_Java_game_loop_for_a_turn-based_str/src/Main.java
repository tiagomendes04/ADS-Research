import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Unit {
    protected String name;
    protected int health;
    protected int attackPower;
    protected int movementRange;
    protected int x, y;

    public Unit(String name, int health, int attackPower, int movementRange, int x, int y) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.movementRange = movementRange;
        this.x = x;
        this.y = y;
    }

    public abstract void move(int newX, int newY);
    public abstract void attack(Unit target);
    public boolean isAlive() { return health > 0; }
}

class Soldier extends Unit {
    public Soldier(String name, int x, int y) {
        super(name, 100, 20, 3, x, y);
    }

    @Override
    public void move(int newX, int newY) {
        int distance = Math.abs(newX - x) + Math.abs(newY - y);
        if (distance <= movementRange) {
            x = newX;
            y = newY;
            System.out.println(name + " moved to (" + x + ", " + y + ")");
        } else {
            System.out.println(name + " cannot move that far!");
        }
    }

    @Override
    public void attack(Unit target) {
        target.health -= attackPower;
        System.out.println(name + " attacks " + target.name + " for " + attackPower + " damage!");
        if (!target.isAlive()) {
            System.out.println(target.name + " has been defeated!");
        }
    }
}

class Game {
    private List<Unit> playerUnits;
    private List<Unit> enemyUnits;
    private Scanner scanner;

    public Game() {
        playerUnits = new ArrayList<>();
        enemyUnits = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addPlayerUnit(Unit unit) {
        playerUnits.add(unit);
    }

    public void addEnemyUnit(Unit unit) {
        enemyUnits.add(unit);
    }

    public void playerTurn() {
        System.out.println("\nPlayer's Turn:");
        for (Unit unit : playerUnits) {
            if (unit.isAlive()) {
                System.out.println(unit.name + " (HP: " + unit.health + ") at (" + unit.x + ", " + unit.y + ")");
            }
        }

        System.out.println("\nChoose a unit to control (1-" + playerUnits.size() + "):");
        int choice = scanner.nextInt() - 1;
        if (choice < 0 || choice >= playerUnits.size() || !playerUnits.get(choice).isAlive()) {
            System.out.println("Invalid choice!");
            return;
        }

        Unit currentUnit = playerUnits.get(choice);
        System.out.println("\n1. Move\n2. Attack\n3. End Turn");
        int action = scanner.nextInt();

        switch (action) {
            case 1:
                System.out.println("Enter new X coordinate:");
                int newX = scanner.nextInt();
                System.out.println("Enter new Y coordinate:");
                int newY = scanner.nextInt();
                currentUnit.move(newX, newY);
                break;
            case 2:
                System.out.println("Choose target (1-" + enemyUnits.size() + "):");
                int targetChoice = scanner.nextInt() - 1;
                if (targetChoice < 0 || targetChoice >= enemyUnits.size() || !enemyUnits.get(targetChoice).isAlive()) {
                    System.out.println("Invalid target!");
                    break;
                }
                currentUnit.attack(enemyUnits.get(targetChoice));
                break;
            case 3:
                System.out.println("Ending turn...");
                break;
            default:
                System.out.println("Invalid action!");
        }
    }

    public void enemyTurn() {
        System.out.println("\nEnemy's Turn:");
        for (Unit unit : enemyUnits) {
            if (unit.isAlive()) {
                // Simple AI: move towards nearest player unit and attack if in range
                Unit nearestPlayer = null;
                int minDistance = Integer.MAX_VALUE;

                for (Unit player : playerUnits) {
                    if (player.isAlive()) {
                        int distance = Math.abs(unit.x - player.x) + Math.abs(unit.y - player.y);
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestPlayer = player;
                        }
                    }
                }

                if (nearestPlayer != null) {
                    if (minDistance <= unit.movementRange) {
                        unit.move(nearestPlayer.x, nearestPlayer.y);
                    }
                    if (minDistance == 1) {
                        unit.attack(nearestPlayer);
                    }
                }
            }
        }
    }

    public boolean isGameOver() {
        boolean playerWon = true;
        for (Unit unit : playerUnits) {
            if (unit.isAlive()) {
                playerWon = false;
                break;
            }
        }

        boolean enemyWon = true;
        for (Unit unit : enemyUnits) {
            if (unit.isAlive()) {
                enemyWon = false;
                break;
            }
        }

        if (playerWon) {
            System.out.println("Player wins!");
            return true;
        } else if (enemyWon) {
            System.out.println("Enemy wins!");
            return true;
        }
        return false;
    }

    public void start() {
        // Initialize game with some units
        addPlayerUnit(new Soldier("Player Soldier 1", 0, 0));
        addPlayerUnit(new Soldier("Player Soldier 2", 1, 0));
        addEnemyUnit(new Soldier("Enemy Soldier 1", 8, 8));
        addEnemyUnit(new Soldier("Enemy Soldier 2", 9, 8));

        while (!isGameOver()) {
            playerTurn();
            if (isGameOver()) break;
            enemyTurn();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}