```java
public class Piece {
    public enum Type {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
    }

    private int id;
    private int color;
    private PieceType type;
    private boolean isMovable;

    public Piece(int id, PieceType type, int color) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.isMovable = true;
    }

    public int getId() {
        return id;
    }

    public PieceType getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }
}
```

```java
public enum PieceType {
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
}
```

```java
public class PieceFactory {
    public static Piece createPiece(int id, PieceType type, int color) {
        return new Piece(id, type, color);
    }
}
```

```java
public class PieceSolver {
    public Piece getPiece(Piece piece) {
        // Assuming piece is a Piece object with id, type, and color
        if (piece.getColor() == PieceColor.RED) {
            return PieceFactory.createPiece(piece.getId(), PieceType.KING, PieceColor.RED);
        } else {
            return PieceFactory.createPiece(piece.getId(), PieceType.QUEEN, PieceColor.RED);
        }
    }
}
```

```java
public enum PieceColor {
    RED, GREEN, BLUE, YELLOW
}
```

```java
public class PieceBoard {
    private Piece[][] pieces;

    public PieceBoard(int rows, int cols) {
        this.pieces = new Piece[rows][cols];
    }

    public void addPiece(Piece piece) {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == null) {
                    pieces[i][j] = piece;
                    break;
                }
            }
        }
    }

    public Piece getPiece(int row, int col) {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] != null && pieces[i][j].getRow() == row && pieces[i][j].getCol() == col) {
                    return pieces[i][j];
                }
            }
        }
        return null;
    }
}
```

```java
public class PieceBoardSolver {
    public PieceBoard getPieceBoard(PieceSolver pieceSolver) {
        PieceBoard board = new PieceBoard(8, 8);
        for (Piece piece : pieceSolver.getPieceAll()) {
            board.addPiece(piece);
        }
        return board;
    }

    public PieceBoard getPieceBoard(int boardRow, int boardCol) {
        PieceBoard board = new PieceBoard(boardRow, boardCol);
        for (Piece piece : pieceSolver.getPieceAll()) {
            if (piece.getRow() == boardRow && piece.getCol() == boardCol) {
                board.addPiece(piece);
            }
        }
        return board;
    }
}
```