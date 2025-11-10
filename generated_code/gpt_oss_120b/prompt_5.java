```java
import java.util.*;

abstract class Unit {
    String name;
    int hp;
    int attack;
    int range;
    int x, y;
    boolean alive = true;

    Unit(String name, int hp, int attack, int range, int x, int y) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.range = range;
        this.x = x;
        this.y = y;
    }

    boolean inRange(Unit other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) <= range;
    }

    void move(int dx, int dy, int boardSize) {
        int nx = Math.max(0, Math.min(boardSize - 1, x + dx));
        int ny = Math.max(0, Math.min(boardSize - 1, y + dy));
        x = nx;
        y = ny;
    }

    void attack(Unit target) {
        if (inRange(target) && target.alive) {
            target.hp -= attack;
            if (target.hp <= 0) {
                target.alive = false;
                System.out.println(target.name + " has been defeated!");
            } else {
                System.out.println(target.name + " takes " + attack + " damage, HP: " + target.hp);
            }
        }
    }

    abstract void takeTurn(List<Unit> allies, List<Unit> enemies, int boardSize, Scanner sc);
}

class Soldier extends Unit {
    Soldier(String name, int x, int y) {
        super(name, 30, 10, 1, x, y);
    }

    @Override
    void takeTurn(List<Unit> allies, List<Unit> enemies, int boardSize, Scanner sc) {
        System.out.println(name + "'s turn. Position: (" + x + "," + y + ")");
        System.out.println("1) Move  2) Attack");
        int choice = sc.nextInt();
        if (choice == 1) {
            System.out.println("Enter dx dy:");
            int dx = sc.nextInt();
            int dy = sc.nextInt();
            move(dx, dy, boardSize);
            System.out.println(name + " moved to (" + x + "," + y + ")");
        } else if (choice == 2) {
            Unit target = selectTarget(enemies, sc);
            if (target != null) attack(target);
        }
    }

    private Unit selectTarget(List<Unit> enemies, Scanner sc) {
        System.out.println("Select target:");
        for (int i = 0; i < enemies.size(); i++) {
            Unit u = enemies.get(i);
            System.out.println(i + ") " + u.name + " (" + u.x + "," + u.y + ") HP:" + u.hp);
        }
        int idx = sc.nextInt();
        if (idx >= 0 && idx < enemies.size()) return enemies.get(idx);
        return null;
    }
}

class Game {
    static final int BOARD_SIZE = 5;
    List<Unit> playerUnits = new ArrayList<>();
    List<Unit> enemyUnits = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    void init() {
        playerUnits.add(new Soldier("Player1", 0, 0));
        playerUnits.add(new Soldier("Player2", 0, 1));
        enemyUnits.add(new Soldier("Enemy1", 4, 4));
        enemyUnits.add(new Soldier("Enemy2", 4, 3));
    }

    void loop() {
        boolean playerTurn = true;
        while (!isGameOver()) {
            List<Unit> active = playerTurn ? playerUnits : enemyUnits;
            List<Unit> opponents = playerTurn ? enemyUnits : playerUnits;

            for (Iterator<Unit> it = active.iterator(); it.hasNext(); ) {
                Unit u = it.next();
                if (!u.alive) continue;
                u.takeTurn(active, opponents, BOARD_SIZE, sc);
                removeDead(opponents);
                if (isGameOver()) break;
            }
            playerTurn = !playerTurn;
        }
        System.out.println("Game over!");
    }

    void removeDead(List<Unit> list) {
        list.removeIf(u -> !u.alive);
    }

    boolean isGameOver() {
        return playerUnits.isEmpty() || enemyUnits.isEmpty();
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.init();
        g.loop();
    }
}
```