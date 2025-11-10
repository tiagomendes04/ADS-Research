```java
import java.util.ArrayList;
import java.util.List;

public class Game {
    private int turns = 0;
    private int currentPlayer = 0;
    private List<Unit> units = new ArrayList<>();
    private List<Unit> enemyUnits = new ArrayList<>();

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public Unit getUnit(int index) {
        return units.get(index);
    }

    public void attack(Unit unit) {
        unit.attack();
    }

    public int getTurn() {
        return turns;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setTurn(int turn) {
        turns = turn;
    }

    public void startGame() {
        while (turns < 50) {
            System.out.println("Turn " + (turns + 1) + ": " + currentPlayer + "'s turn");
            System.out.println("Units: ");
            for (Unit unit : units) {
                System.out.println(unit.toString());
            }
            System.out.println("Enemies: ");
            for (Unit unit : enemyUnits) {
                System.out.println(unit.toString());
            }
            System.out.println("Current Player: " + (currentPlayer + 1));
            System.out.print("Enter your command (move, attack, quit): ");
            String input = System.console().readLine();
            switch (input.toLowerCase()) {
                case "move":
                    System.out.print("Enter unit ID: ");
                    int unitId = Integer.parseInt(System.console().readLine());
                    Unit unit = getUnit(unitId - 1);
                    if (unit != null) {
                        System.out.print("Enter destination X: ");
                        int x = Integer.parseInt(System.console().readLine());
                        System.out.print("Enter destination Y: ");
                        int y = Integer.parseInt(System.console().readLine());
                        unit.move(x, y);
                    }
                    break;
                case "attack":
                    System.out.print("Enter unit ID: ");
                    unitId = Integer.parseInt(System.console().readLine());
                    Unit unit = getUnit(unitId - 1);
                    if (unit != null) {
                        System.out.print("Enter enemy X: ");
                        int enemyX = Integer.parseInt(System.console().readLine());
                        System.out.print("Enter enemy Y: ");
                        int enemyY = Integer.parseInt(System.console().readLine());
                        unit.attack(enemyX, enemyY);
                    }
                    break;
                case "quit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command");
            }
            currentPlayer = (currentPlayer + 1) % 2;
        }
        System.out.println("Game over!");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}

class Unit {
    private int x;
    private int y;
    private int id;

    public Unit(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void attack(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
```