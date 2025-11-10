```java
import java.util.ArrayList;
import java.util.List;

public abstract class GameEngine {
    private List<GamePiece> pieces;
    private List<GameMove> moves;

    public GameEngine() {
        this.pieces = new ArrayList<>();
        this.moves = new ArrayList<>();
    }

    public void addPiece(GamePiece piece) {
        this.pieces.add(piece);
    }

    public void addMove(GameMove move) {
        this.moves.add(move);
    }

    public List<GamePiece> getPieces() {
        return this.pieces;
    }

    public List<GameMove> getMoves() {
        return this.moves;
    }

    public abstract int calculateScore();
}

```java
public abstract class GamePiece {
    private String name;
    private int value;

    public GamePiece(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public abstract void move(GameMove move);
}

```java
public abstract class GameMove {
    private String type;
    private GamePiece piece;

    public GameMove(String type, GamePiece piece) {
        this.type = type;
        this.piece = piece;
    }

    public String getType() {
        return this.type;
    }

    public GamePiece getPiece() {
        return this.piece;
    }
}

```java
public enum PieceType {
    PAWN,
    KNIGHT,
    BISHOP,
    ROOK,
    QUEEN,
    KING
}

```java
public class ConcreteGamePiece extends GamePiece {
    private PieceType type;

    public ConcreteGamePiece(String name, int value, PieceType type) {
        super(name, value);
        this.type = type;
    }

    public PieceType getType() {
        return this.type;
    }

    @Override
    public void move(GameMove move) {
        System.out.println(this.getName() + " moved to " + move.getPiece().getName());
    }
}

```java
public class ConcreteGameMove extends GameMove {
    private int x;
    private int y;

    public ConcreteGameMove(String type, GamePiece piece, int x, int y) {
        super(type, piece);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

```java
public class ChessEngine extends GameEngine {
    @Override
    public int calculateScore() {
        int score = 0;
        for (GamePiece piece : getPieces()) {
            score += piece.getValue();
        }
        return score;
    }
}

```java
public class Main {
    public static void main(String[] args) {
        GameEngine engine = new ChessEngine();
        GamePiece pawn = new ConcreteGamePiece("Pawn", 1, PieceType.PAWN);
        GamePiece knight = new ConcreteGamePiece("Knight", 3, PieceType.KNIGHT);
        engine.addPiece(pawn);
        engine.addPiece(knight);
        GameMove move = new ConcreteGameMove("Move", pawn, 1, 2);
        engine.addMove(move);
        pawn.move(move);
        System.out.println("Score: " + engine.calculateScore());
    }
}
```