```java
import java.util.*;

public class BoardGameEngine {

    // ---- Core Interfaces ----
    public interface Move {
        boolean isValid(Board board, Player player);
        void apply(Board board, Player player);
    }

    public interface Scorable {
        int getScore(Player player);
    }

    // ---- Core Classes ----
    public static class Board {
        private final int rows, cols;
        private final Piece[][] grid;

        public Board(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.grid = new Piece[rows][cols];
        }

        public int getRows() { return rows; }
        public int getCols() { return cols; }

        public Piece getPiece(int r, int c) {
            if (inBounds(r, c)) return grid[r][c];
            return null;
        }

        public void setPiece(int r, int c, Piece piece) {
            if (inBounds(r, c)) grid[r][c] = piece;
        }

        public void removePiece(int r, int c) {
            if (inBounds(r, c)) grid[r][c] = null;
        }

        public boolean inBounds(int r, int c) {
            return r >= 0 && r < rows && c >= 0 && c < cols;
        }

        public List<Piece> getAllPieces() {
            List<Piece> list = new ArrayList<>();
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] != null) list.add(grid[r][c]);
                }
            }
            return list;
        }
    }

    public static abstract class Piece {
        protected final Player owner;
        protected int row, col;

        public Piece(Player owner, int row, int col) {
            this.owner = owner;
            this.row = row;
            this.col = col;
        }

        public Player getOwner() { return owner; }
        public int getRow() { return row; }
        public int getCol() { return col; }

        public void setPosition(int r, int c) {
            this.row = r;
            this.col = c;
        }

        public abstract List<Move> getLegalMoves(Board board);
    }

    public static class Player {
        private final String name;
        private final int id;
        private int score = 0;

        public Player(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() { return name; }
        public int getId() { return id; }
        public int getScore() { return score; }
        public void addScore(int delta) { score += delta; }
    }

    // ---- Example Implementations ----
    public static class SimplePiece extends Piece {
        public SimplePiece(Player owner, int row, int col) {
            super(owner, row, col);
        }

        @Override
        public List<Move> getLegalMoves(Board board) {
            List<Move> moves = new ArrayList<>();
            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] d : dirs) {
                int nr = row + d[0];
                int nc = col + d[1];
                if (board.inBounds(nr, nc) && board.getPiece(nr, nc) == null) {
                    moves.add(new SimpleMove(this, nr, nc));
                }
            }
            return moves;
        }
    }

    public static class SimpleMove implements Move {
        private final SimplePiece piece;
        private final int toRow, toCol;
        private int fromRow, fromCol;

        public SimpleMove(SimplePiece piece, int toRow, int toCol) {
            this.piece = piece;
            this.toRow = toRow;
            this.toCol = toCol;
        }

        @Override
        public boolean isValid(Board board, Player player) {
            if (!piece.getOwner().equals(player)) return false;
            if (!board.inBounds(toRow, toCol)) return false;
            return board.getPiece(toRow, toCol) == null;
        }

        @Override
        public void apply(Board board, Player player) {
            fromRow = piece.getRow();
            fromCol = piece.getCol();
            board.removePiece(fromRow, fromCol);
            piece.setPosition(toRow, toCol);
            board.setPiece(toRow, toCol, piece);
        }
    }

    public static class SimpleScoring implements Scorable {
        @Override
        public int getScore(Player player) {
            return player.getScore();
        }
    }

    // ---- Game Engine ----
    public static class GameEngine {
        private final Board board;
        private final List<Player> players;
        private final Scorable scoring;
        private int currentPlayerIdx = 0;
        private boolean gameOver = false;

        public GameEngine(Board board, List<Player> players, Scorable scoring) {
            this.board = board;
            this.players = players;
            this.scoring = scoring;
        }

        public Player getCurrentPlayer() {
            return players.get(currentPlayerIdx);
        }

        public void nextTurn() {
            currentPlayerIdx = (currentPlayerIdx + 1) % players.size();
        }

        public boolean makeMove(Move move) {
            if (gameOver) return false;
            Player player = getCurrentPlayer();
            if (!move.isValid(board, player)) return false;
            move.apply(board, player);
            updateScore(player);
            checkGameOver();
            if (!gameOver) nextTurn();
            return true;
        }

        private void updateScore(Player player) {
            // Example: +1 per move
            player.addScore(1);
        }

        private void checkGameOver() {
            // Example condition: any player reaches 10 points
            for (Player p : players) {
                if (scoring.getScore(p) >= 10) {
                    gameOver = true;
                    break;
                }
            }
        }

        public boolean isGameOver() { return gameOver; }

        public Board getBoard() { return board; }

        public List<Player> getPlayers() { return Collections.unmodifiableList(players); }
    }

    // ---- Demo Main ----
    public static void main(String[] args) {
        Board board = new Board(5, 5);
        Player p1 = new Player("Alice", 1);
        Player p2 = new Player("Bob", 2);
        List<Player> players = Arrays.asList(p1, p2);
        GameEngine engine = new GameEngine(board, players, new SimpleScoring());

        // Place initial pieces
        SimplePiece sp1 = new SimplePiece(p1, 0, 0);
        SimplePiece sp2 = new SimplePiece(p2, 4, 4);
        board.setPiece(0, 0, sp1);
        board.setPiece(4, 4, sp2);

        Scanner scanner = new Scanner(System.in);
        while (!engine.isGameOver()) {
            Player cur = engine.getCurrentPlayer();
            System.out.println("\nCurrent player: " + cur.getName() + " (Score: " + cur.getScore() + ")");
            System.out.println("Enter move (rowFrom colFrom rowTo colTo):");
            int rFrom = scanner.nextInt();
            int cFrom = scanner.nextInt();
            int rTo = scanner.nextInt();
            int cTo = scanner.nextInt();

            Piece piece = board.getPiece(rFrom, cFrom);
            if (piece == null || !piece.getOwner().equals(cur)) {
                System.out.println("Invalid piece selection.");
                continue;
            }
            Move move = new SimpleMove((SimplePiece) piece, rTo, cTo);
            if (engine.makeMove(move)) {
                System.out.println("Move applied.");
            } else {
                System.out.println("Invalid move.");
            }
        }

        System.out.println("\nGame Over!");
        for (Player p : engine.getPlayers()) {
            System.out.println(p.getName() + " final score: " + p.getScore());
        }
    }
}
```