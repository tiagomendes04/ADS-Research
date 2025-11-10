```java
import java.util.ArrayList;
import java.util.List;

interface Piece {
    String getType();
    List<Move> getValidMoves(Board board);
}

class Move {
    int startX, startY;
    int endX, endY;

    public Move(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public String toString() {
        return "Move from (" + startX + ", " + startY + ") to (" + endX + ", " + endY + ")";
    }
}

class Board {
    private Piece[][] grid;
    private int size;

    public Board(int size) {
        this.size = size;
        this.grid = new Piece[size][size];
    }

    public void placePiece(Piece piece, int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            grid[x][y] = piece;
        }
    }

    public Piece getPiece(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            return grid[x][y];
        }
        return null;
    }

    public boolean isOccupied(int x, int y) {
        return getPiece(x, y) != null;
    }

    public int getSize() {
        return size;
    }
}

abstract class Player {
    String name;
    List<Piece> pieces;

    public Player(String name) {
        this.name = name;
        this.pieces = new ArrayList<>();
    }

    abstract int calculateScore();

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public String getName() {
        return name;
    }
}

class GameEngine {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public GameEngine(int boardSize, Player player1, Player player2) {
        this.board = new Board(boardSize);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public boolean makeMove(Move move) {
        Piece piece = board.getPiece(move.startX, move.startY);
        if (piece != null && piece.getValidMoves(board).contains(move)) {
            board.placePiece(null, move.startX, move.startY);
            board.placePiece(piece, move.endX, move.endY);
            switchPlayer();
            return true;
        }
        return false;
    }

    public int getScore(Player player) {
        return player.calculateScore();
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}

class ExamplePiece implements Piece {
    private String type;

    public ExamplePiece(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> validMoves = new ArrayList<>();
        int x = -1, y = -1;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getPiece(i, j) == this) {
                    x = i;
                    y = j;
                    break;
                }
            }
            if (x != -1) break;
        }
        if (x == -1 || y == -1) return validMoves;

        if (x + 1 < board.getSize() && !board.isOccupied(x + 1, y)) {
            validMoves.add(new Move(x, y, x + 1, y));
        }
        if (y + 1 < board.getSize() && !board.isOccupied(x, y + 1)) {
            validMoves.add(new Move(x, y, x, y + 1));
        }
        return validMoves;
    }
}

class ExamplePlayer extends Player {
    public ExamplePlayer(String name) {
        super(name);
    }

    @Override
    int calculateScore() {
        return pieces.size();
    }
}

public class Main {
    public static void main(String[] args) {
        Player player1 = new ExamplePlayer("Player 1");
        Player player2 = new ExamplePlayer("Player 2");

        GameEngine engine = new GameEngine(8, player1, player2);

        Piece piece1 = new ExamplePiece("Type A");
        Piece piece2 = new ExamplePiece("Type B");

        player1.addPiece(piece1);