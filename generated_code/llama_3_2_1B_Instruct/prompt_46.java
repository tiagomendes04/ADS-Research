```java
import java.util.ArrayList;
import java.util.List;

public class BoardGameEngine {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;
    private static final int PIECE_SIZE = 50;

    private Piece[][] board;
    private int currentPlayer;

    public BoardGameEngine() {
        board = new Piece[BOARD_HEIGHT][BOARD_WIDTH];
        currentPlayer = 0;
    }

    public void addPiece(int x, int y) {
        board[y][x] = new Piece(x, y);
    }

    public void removePiece(int x, int y) {
        board[y][x] = null;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    public Piece getPiece(int x, int y) {
        if (board[y][x] != null) {
            return board[y][x];
        }
        return null;
    }

    public void printBoard() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (board[y][x] != null) {
                    System.out.print(board[y][x].getPiece().toString() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}

class Piece {
    private int x;
    private int y;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return Integer.toString(x) + " " + Integer.toString(y);
    }
}

class GameOver {
    public static void main(String[] args) {
        BoardGameEngine engine = new BoardGameEngine();
        engine.addPiece(0, 0);
        engine.addPiece(1, 0);
        engine.addPiece(2, 0);
        engine.addPiece(3, 0);
        engine.switchPlayer();
        engine.printBoard();
        engine.switchPlayer();
        engine.printBoard();
    }
}
```

```java
public class Piece {
    private int x;
    private int y;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return Integer.toString(x) + " " + Integer.toString(y);
    }
}
```