```java
import java.util.ArrayList;
import java.util.List;

public class PuzzlePiece {
    private int x;
    private int y;

    public PuzzlePiece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class PuzzleGrid {
    private List<PuzzlePiece> pieces;

    public PuzzleGrid() {
        pieces = new ArrayList<>();
    }

    public void addPiece(PuzzlePiece piece) {
        pieces.add(piece);
    }

    public List<PuzzlePiece> getPieces() {
        return pieces;
    }

    // ... other methods ...
}

public class Solver {
    // ... methods for solving the puzzle ...
}
```