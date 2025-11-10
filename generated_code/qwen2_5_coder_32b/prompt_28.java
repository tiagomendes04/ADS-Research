```java
import java.util.ArrayList;
import java.util.List;

interface PuzzleSolver {
    boolean solve(Puzzle puzzle);
}

class Puzzle {
    private List<Piece> pieces;
    private int size;

    public Puzzle(int size) {
        this.size = size;
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getSize() {
        return size;
    }

    public boolean isSolved() {
        // Simplified check for demonstration purposes
        return pieces.stream().allMatch(Piece::isInPlace);
    }
}

class Piece {
    private int id;
    private int position;
    private boolean inPlace;

    public Piece(int id, int position) {
        this.id = id;
        this.position = position;
        this.inPlace = false;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isInPlace() {
        return inPlace;
    }

    public void setInPlace(boolean inPlace) {
        this.inPlace = inPlace;
    }
}

class SimplePuzzleSolver implements PuzzleSolver {
    @Override
    public boolean solve(Puzzle puzzle) {
        for (Piece piece : puzzle.getPieces()) {
            if (piece.getPosition() == piece.getId()) {
                piece.setInPlace(true);
            } else {
                piece.setPosition(piece.getId());
                piece.setInPlace(true);
            }
        }
        return puzzle.isSolved();
    }
}
```