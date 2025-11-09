import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Unit {
    private static int nextId = 0;
    private int id;
    private String name;
    private int playerId;
    private int x, y;
    private int health;
    private int attackPower;
    private boolean hasActedThisTurn;

    public Unit(String name, int playerId, int x, int y, int health, int attackPower) {
        this.id = nextId++;
        this.name = name;
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.health = health;
        this.attackPower = attackPower;
        this.hasActedThisTurn = false;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPlayerId() { return playerId; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }
    public int getAttackPower() { return attackPower; }
    public boolean hasActedThisTurn() { return hasActedThisTurn; }
    public boolean isAlive() { return health > 0; }

    public void move(int dx, int dy) {
        if (!isAlive()) return;
        this.x += dx;
        this.y += dy;
        System.out.println("      " + name + " moved to (" + x + ", " + y + ")");
    }

    public void takeDamage(int amount) {
        if (!isAlive()) return;
        this.health -= amount;
        if (this.health < 0) this.health = 0;
        System.out.println("      " + name + " took " + amount + " damage. Remaining health: " + health);
        if (!isAlive()) {
            System.out.println("      " + name + " has been defeated!");
        }
    }

    public void markActed() {
        this.hasActedThisTurn = true;
    }

    public void resetActedStatus() {
        this.hasActedThisTurn = false;
    }

    public double getDistance(Unit other) {
        // Using Manhattan distance for simplicity
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}

class Player {
    private static int nextId = 0;
    private int id;
    private String name;

    public Player(String name) {
        this.id = nextId++;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}

public class Game {
    private List<Player> players;
    private List<Unit> allUnits;
    private int currentPlayerIndex;
    private int turnNumber;
    private boolean isGameOver;
    private Random random;

    public Game() {
        this.players = new ArrayList<>();
        this.allUnits = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.turnNumber = 0;
        this.isGameOver = false;
        this.random = new Random();
    }

    public void initializeGame() {
        System.out.println("Initializing Game...");

        Player player1 = new Player("Player 1 (Red)");
        Player player2 = new Player("Player 2 (Blue)");
        players.add(player1);
        players.add(player2);

        // Player 1 units
        allUnits.add(new Unit("Warrior A", player1.getId(), 0, 0, 100, 20));
        allUnits.add(new Unit("Archer B", player1.getId(), 1, 1, 60, 30));
        allUnits.add(new Unit("Mage C", player1.getId(), 0, 2, 70, 25));

        // Player 2 units
        allUnits.add(new Unit("Knight X", player2.getId(), 9, 9, 110, 22));
        allUnits.add(new Unit("Rogue Y", player2.getId(), 8, 8, 65, 32));
        allUnits.add(new Unit("Healer Z", player2.getId(), 9, 7, 75, 15));

        System.out.println("Game Initialized with " + players.size() + " players and " + allUnits.size() + " units.");
    }

    public void startGameLoop() {
        initializeGame();

        while (!isGameOver) {
            turnNumber++;
            Player currentPlayer = players.get(currentPlayerIndex);

            System.out.println("\n--- Turn " + turnNumber + " ---");
            System.out.println("It's " + currentPlayer.getName() + "'s turn.");

            // Reset acted status for current player's alive units
            allUnits.stream()
                    .filter(unit -> unit.getPlayerId() == currentPlayer.getId() && unit.isAlive())
                    .forEach(Unit::resetActedStatus);

            simulatePlayerTurn(currentPlayer);

            checkWinCondition();

            if (!isGameOver) {
                advanceTurn();
            }
        }

        System.out.println("\n--- Game Over! ---");
        // Announce winner
        long remainingPlayers = players.stream()
                                    .filter(p -> allUnits.stream().anyMatch(u -> u.getPlayerId() == p.getId() && u.isAlive()))
                                    .count();

        if (remainingPlayers == 1) {
            Player winner = players.stream()
                                .filter(p -> allUnits.stream().anyMatch(u -> u.getPlayerId() == p.getId() && u.isAlive()))
                                .findFirst().orElse(null);
            if (winner != null) {
                System.out.println(winner.getName() + " wins!");
            }
        } else {
            System.out.println("The game ended with no clear winner (e.g., all units defeated simultaneously).");
        }
    }

    private void simulatePlayerTurn(Player player) {
        List<Unit> playerAliveUnits = allUnits.stream()
                                  .filter(u -> u.getPlayerId() == player.getId() && u.isAlive())
                                  .collect(Collectors.toList());

        if (playerAliveUnits.isEmpty()) {
            System.out.println("  " + player.getName() + " has no units left to act.");
            return;
        }

        for (Unit unit : playerAliveUnits) {
            if (!unit.hasActedThisTurn()) {
                System.out.println("  " + player.getName() + "'s " + unit.getName() + " (HP:" + unit.getHealth() + ", Pos:" + unit.getX() + "," + unit.getY() + ") is acting.");

                boolean acted = false;
                // Simple AI: Try to attack first, if not possible, then move
                if (tryAttack(unit, player.getId())) {
                    acted = true;
                } else {
                    tryMove(unit);
                    acted = true;
                }

                if (acted) {
                    unit.markActed();
                }
                System.out.println("    " + unit.getName() + " status after action: Pos: (" + unit.getX() + ", " + unit.getY() + ") HP: " + unit.getHealth() + (unit.isAlive() ? "" : " (Defeated)"));
            }
        }
    }

    private boolean tryAttack(Unit attacker, int attackerPlayerId) {
        // Find enemy units within attack range (Manhattan distance 1 for simplicity)
        List<Unit> potentialTargets = allUnits.stream()
                .filter(u -> u.getPlayerId() != attackerPlayerId && u.isAlive() && attacker.getDistance(u) <= 1)
                .collect(Collectors.toList());

        if (!potentialTargets.isEmpty()) {
            // Pick a random target from eligible ones
            Unit target = potentialTargets.get(random.nextInt(potentialTargets.size()));
            System.out.println("      " + attacker.getName() + " attacks " + target.getName() + "!");
            target.takeDamage(attacker.getAttackPower());
            return true;
        }
        return false;
    }

    private void tryMove(Unit unit) {
        // Randomly choose a direction to move (dx, dy can be -1, 0, or 1)
        int dx = random.nextInt(3) - 1; // -1, 0, 1
        int dy = random.nextInt(3) - 1; // -1, 0, 1

        // Ensure some movement if both dx and dy are 0
        if (dx == 0 && dy == 0) {
            if (random.nextBoolean()) dx = random.nextBoolean() ? 1 : -1;
            else dy = random.nextBoolean() ? 1 : -1;
        }
        unit.move(dx, dy);
    }

    private void advanceTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private void checkWinCondition() {
        int activePlayersCount = 0;
        Player lastActivePlayer = null;

        for (Player p : players) {
            boolean hasAliveUnits = allUnits.stream()
                                            .anyMatch(u -> u.getPlayerId() == p.getId() && u.isAlive());
            if (hasAliveUnits) {
                activePlayersCount++;
                lastActivePlayer = p;
            }
        }

        if (activePlayersCount <= 1) { // If 0 or 1 player has units left
            isGameOver = true;
            if (activePlayersCount == 1) {
                System.out.println("\n*** " + lastActivePlayer.getName() + " is the last player standing! ***");
            } else {
                System.out.println("\n*** All players have lost their units! ***");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGameLoop();
    }
}