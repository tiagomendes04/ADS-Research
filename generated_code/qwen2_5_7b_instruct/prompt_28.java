```java
import java.util.ArrayList;
import java.util.List;

class PuzzlePiece {
    private final int id;
    private final int x, y;

    public PuzzlePiece(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

class PuzzleBoard {
    private final List<PuzzlePiece> pieces;
    private final int size;

    public PuzzleBoard(int size) {
        this.size = size;
        this.pieces = new ArrayList<>(size * size);
        for (int i = 1; i <= size * size; i++) {
            pieces.add(new PuzzlePiece(i, i / size, i % size));
        }
    }

    public List<PuzzlePiece> get_pieces() {
        return pieces;
    }

    public void movePiece(int fromX, int fromY, int toX, int toY) {
        for (PuzzlePiece piece : pieces) {
            if (piece.getX() == fromX && piece.getY() == fromY) {
                int tempX = piece.getX();
                int tempY = piece.getY();
                piece.setX(toX);
                piece.setY(toY);
                for (PuzzlePiece p : pieces) {
                    if (p.getX() == toX && p.getY() == toY) {
                        p.setX(tempX);
                        p.setY(tempY);
                        break;
                    }
                }
                break;
            }
        }
    }

    public boolean isSolved() {
        for (int i = 0; i < pieces.size(); i++) {
            PuzzlePiece piece = pieces.get(i);
            if (piece.getX() != i / size || piece.getY() != i % size) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (PuzzlePiece piece : pieces) {
                    if (piece.getX() == i && piece.getY() == j) {
                        sb.append(piece.getId()).append(" ");
                        break;
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

class PuzzleSolver {
    public static void solvePuzzle(PuzzleBoard board) {
        while (!board.isSolved()) {
            for (PuzzlePiece piece : board.get_pieces()) {
                int x = piece.getX();
                int y = piece.getY();
                if (x > 0 && !board.get pieces().get(x - 1).equals(piece)) {
                    board.movePiece(x, y, x - 1, y);
                } else if (x < board.size - 1 && !board.get_pieces().get(x + 1).equals(piece)) {
                    board.movePiece(x, y, x + 1, y);
                } else if (y > 0 && !board.get_pieces().get(x * board.size + y - 1).equals(piece)) {
                    board.movePiece(x, y, x, y - 1);
                } else if (y < board.size - 1 && !board.get_pieces().get(x * board.size + y + 1).equals(piece)) {
                    board.movePiece(x, y, x, y + 1);
                }
            }
        }
        System.out.println("Puzzle solved!");
    }
}
```