```java
// Piece.java

public abstract class Piece {
    private int x;
    private int y;
    private int size;
    private int[][] image;

    public Piece(int x, int y, int size, int[][] image) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public int[][] getImage() {
        return image;
    }

    public abstract void move(int newX, int newY);
}

// MovablePiece.java

public class MovablePiece extends Piece {
    public MovablePiece(int x, int y, int size, int[][] image) {
        super(x, y, size, image);
    }

    @Override
    public void move(int newX, int newY) {
        if (newX >= 0 && newX < getBoardWidth() && newY >= 0 && newY < getBoardHeight()) {
            setX(newX);
            setY(newY);
        }
    }

    private int getBoardWidth() {
        return 10;
    }

    private int getBoardHeight() {
        return 10;
    }
}

// Solver.java

public abstract class Solver {
    public abstract boolean isSolved(Piece[] pieces);
}

// PieceSolver.java

public class PieceSolver extends Solver {
    private int[][] board;
    private Piece[] pieces;

    public PieceSolver(int[][] board, Piece[] pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    @Override
    public boolean isSolved(Piece[] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            Piece piece = pieces[i];
            if (piece.getX() < 0 || piece.getX() >= board[0].length || piece.getY() < 0 || piece.getY() >= board.length) {
                return false;
            }
        }
        return true;
    }
}

// PuzzleGame.java

public class PuzzleGame {
    public static void main(String[] args) {
        int[][] board = new int[10][10];
        Piece[] pieces = new Piece[5];
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = new MovablePiece(i * 2, i, 2, new int[][]{{1, 1}, {1, 1}});
        }
        Solver solver = new PieceSolver(board, pieces);
        System.out.println(solver.isSolved(pieces));
    }
}
```
```java
// BetterSolver.java

import java.util.ArrayList;
import java.util.List;

public class BetterSolver extends Solver {
    private Piece[] pieces;

    public BetterSolver(Piece[] pieces) {
        this.pieces = pieces;
    }

    @Override
    public boolean isSolved(Piece[] pieces) {
        List<Move> possibleMoves = new ArrayList<>();
        for (int i = 0; i < pieces.length; i++) {
            Piece piece = pieces[i];
            int newX = piece.getX();
            int newY = piece.getY();
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) {
                        continue;
                    }
                    newX = piece.getX() + x;
                    newY = piece.getY() + y;
                    if (newX >= 0 && newX < pieces[0].getSize() * 10 && newY >= 0 && newY < pieces[0].getSize() * 10) {
                        possibleMoves.add(new Move(newX, newY, piece));
                    }
                }
            }
        }
        for (Move move : possibleMoves) {
            Piece[] newPieces = pieces.clone();
            newPieces[newPieces.length - 1].move(move.getX(), move.getY());
            if (isSolved(newPieces)) {
                return true;
            }
        }
        return false;
    }

    private static class Move {
        private int x;
        private int y;
        private Piece piece;

        public Move(int x, int y, Piece piece) {
            this.x = x;
            this.y = y;
            this.piece = piece;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Piece getPiece() {
            return piece;
        }
    }
}
```