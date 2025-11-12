```java
import java.util.ArrayList;
import java.util.List;

interface Piece {
    String getName();
    void move(int x, int y);
    int getX();
    int getY();
}

class Player {
    private final String name;
    private final List<Piece> pieces;
    private int score;

    public Player(String name) {
        this.name = name;
        this.pieces = new ArrayList<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getScore() {
        return score;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void updateScore(int points) {
        score += points;
    }
}

class GameBoard {
    private final int size;
    private final Piece[][] board;

    public GameBoard(int size) {
        this.size = size;
        this.board = new Piece[size][size];
    }

    public int getSize() {
        return size;
    }

    public Piece getPieceAt(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            return board[x][y];
        }
        return null;
    }

    public void placePiece(Piece piece, int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size && board[x][y] == null) {
            board[x][y] = piece;
            piece.move(x, y);
        }
    }

    public void removePiece(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            board[x][y] = null;
        }
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].getName() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}

class GameEngine {
    private final GameBoard board;
    private final List<Player> players;
    private int currentPlayerIndex;

    public GameEngine(int boardSize, List<Player> players) {
        this.board = new GameBoard(boardSize);
        this.players = players;
        this.currentPlayerIndex = 0;
    }

    public GameBoard getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean makeMove(Piece piece, int x, int y) {
        int currentX = piece.getX();
        int currentY = piece.getY();
        if (isValidMove(currentX, currentY, x, y)) {
            board.removePiece(currentX, currentY);
            board.placePiece(piece, x, y);
            return true;
        }
        return false;
    }

    private boolean isValidMove(int currentX, int currentY, int x, int y) {
        return x >= 0 && x < board.getSize() && y >= 0 && y < board.getSize() && board.getPieceAt(x, y) == null;
    }

    public void updateScores() {
        for (Player player : players) {
            int score = calculateScore(player);
            player.updateScore(score);
        }
    }

    private int calculateScore(Player player) {
        int score = 0;
        for (Piece piece : player.getPieces()) {
            score += piece.getX() + piece.getY(); // Simple scoring logic based on position
        }
        return score;
    }

    public void printScores() {
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }
    }

    public void playGame() {
        while (true) {
            Player currentPlayer = getCurrentPlayer();
            board.printBoard();
            // Simulate move
            boolean validMove = makeMove(currentPlayer.getPieces().get(0), 1, 1); // Example move
            if (validMove) {
                updateScores();
                printScores();
            }
            nextPlayer();
            // End game condition example
            if (currentPlayer.getScore() > 10) break;
        }
    }
}

class SimplePiece implements Piece {
    private final String name;
    private int x;
    private int y;

    public SimplePiece(String name) {
        this.name = name;
        this.x = -1;
        this.y = -1;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}

public class Game {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Alice"));
        players.add(new Player("Bob"));

        players.get(0).addPiece(new SimplePiece("A1"));
        players.get(1).addPiece(new SimplePiece("B1"));

        GameEngine gameEngine = new GameEngine(8, players);
        gameEngine.playGame();
    }
}
```