```java
import java.util.ArrayList;
import java.util.List;

public class BoardGame {

    public static class Piece {
        String name;
        int x, y;
        public Piece(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
        public void move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Game {
        List<Piece> pieces;
        int[][] board;
        public Game() {
            pieces = new ArrayList<>();
            board = new int[10][10];
        }
        public void addPiece(Piece piece) {
            pieces.add(piece);
        }
        public void movePiece(int fromX, int fromY, int toX, int toY) {
            // Implement move logic here
        }
    }

    public static class Main {
        public static void main(String[] args) {
            Game game = new Game();
            Piece piece1 = new Piece("Piece1", 0, 0);
            game.addPiece(piece1);
        }
    }
}
```