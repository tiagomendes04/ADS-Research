```java
import java.util.ArrayList;
import java.util.List;

public interface PuzzlePiece {
    void move(int x, int y);
    boolean canMove(int x, int y);
    int getX();
    int getY();
    String getType();
}

public class SlidePuzzlePiece implements PuzzlePiece {
    private int x;
    private int y;
    private final String type;

    public SlidePuzzlePiece(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean canMove(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y) == 1;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String getType() {
        return type;
    }
}

public class PuzzleBoard {
    private final int size;
    private final List<PuzzlePiece> pieces;

    public PuzzleBoard(int size) {
        this.size = size;
        this.pieces = new ArrayList<>();
    }

    public void addPiece(PuzzlePiece piece) {
        pieces.add(piece);
    }

    public boolean movePiece(String type, int x, int y) {
        for (PuzzlePiece piece : pieces) {
            if (piece.getType().equals(type) && piece.canMove(x, y)) {
                piece.move(x, y);
                return true;
            }
        }
        return false;
    }

    public boolean isSolved() {
        for (int i = 0; i < pieces.size(); i++) {
            PuzzlePiece piece = pieces.get(i);
            if (piece.getX() != i % size || piece.getY() != i / size) {
                return false;
            }
        }
        return true;
    }

    public List<PuzzlePiece> getPieces() {
        return new ArrayList<>(pieces);
    }
}

public interface PuzzleSolver {
    boolean solve(PuzzleBoard board);
}

public class SlidePuzzleSolver implements PuzzleSolver {
    @Override
    public boolean solve(PuzzleBoard board) {
        // Simple solver that tries all possible moves
        List<PuzzlePiece> pieces = board.getPieces();
        for (PuzzlePiece piece : pieces) {
            int x = piece.getX();
            int y = piece.getY();
            if (board.movePiece(piece.getType(), x + 1, y)) {
                if (solve(board)) {
                    return true;
                }
                board.movePiece(piece.getType(), x, y);
            }
            if (board.movePiece(piece.getType(), x - 1, y)) {
                if (solve(board)) {
                    return true;
                }
                board.movePiece(piece.getType(), x, y);
            }
            if (board.movePiece(piece.getType(), x, y + 1)) {
                if (solve(board)) {
                    return true;
                }
                board.movePiece(piece.getType(), x, y);
            }
            if (board.movePiece(piece.getType(), x, y - 1)) {
                if (solve(board)) {
                    return true;
                }
                board.movePiece(piece.getType(), x, y);
            }
        }
        return board.isSolved();
    }
}
```