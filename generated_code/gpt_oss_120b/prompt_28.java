```java
import java.util.*;
import java.util.stream.Collectors;

/* ---------- Core Model ---------- */

enum Direction {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);
    final int dRow, dCol;
    Direction(int dRow, int dCol) { this.dRow = dRow; this.dCol = dCol; }
}

final class Position {
    final int row, col;
    Position(int row, int col) { this.row = row; this.col = col; }

    Position moved(Direction dir) { return new Position(row + dir.dRow, col + dir.dCol); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return row == p.row && col == p.col;
    }

    @Override public int hashCode() { return Objects.hash(row, col); }
}

/* A piece occupies a single cell. */
final class Piece {
    final int id;
    final Position position;
    final Position goal; // null if no specific goal

    Piece(int id, Position position, Position goal) {
        this.id = id;
        this.position = position;
        this.goal = goal;
    }

    Piece move(Direction dir) { return new Piece(id, position.moved(dir), goal); }

    boolean isAtGoal() { return goal != null && position.equals(goal); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece p = (Piece) o;
        return id == p.id && Objects.equals(position, p.position) && Objects.equals(goal, p.goal);
    }

    @Override public int hashCode() { return Objects.hash(id, position, goal); }
}

/* ---------- Board ---------- */

final class Board {
    final int rows, cols;
    final Map<Integer, Piece> pieces; // key = piece id

    Board(int rows, int cols, Collection<Piece> pieces) {
        this.rows = rows;
        this.cols = cols;
        this.pieces = pieces.stream().collect(Collectors.toMap(p -> p.id, p -> p));
    }

    private Board(int rows, int cols, Map<Integer, Piece> pieces) {
        this.rows = rows;
        this.cols = cols;
        this.pieces = Collections.unmodifiableMap(pieces);
    }

    boolean isInside(Position p) {
        return p.row >= 0 && p.row < rows && p.col >= 0 && p.col < cols;
    }

    boolean isOccupied(Position p) {
        return pieces.values().stream().anyMatch(piece -> piece.position.equals(p));
    }

    Optional<Piece> pieceAt(Position p) {
        return pieces.values().stream().filter(piece -> piece.position.equals(p)).findFirst();
    }

    /** Returns a new Board with the piece moved if the move is legal, otherwise empty. */
    Optional<Board> move(int pieceId, Direction dir) {
        Piece piece = pieces.get(pieceId);
        if (piece == null) return Optional.empty();
        Position target = piece.position.moved(dir);
        if (!isInside(target) || isOccupied(target)) return Optional.empty();

        Map<Integer, Piece> newMap = new HashMap<>(pieces);
        newMap.put(pieceId, piece.move(dir));
        return Optional.of(new Board(rows, cols, newMap));
    }

    boolean isSolved() {
        return pieces.values().stream().allMatch(p -> p.goal == null || p.isAtGoal());
    }

    /** Simple string representation for debugging / hashing */
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rows).append('x').append(cols).append('|');
        pieces.values().stream()
                .sorted(Comparator.comparingInt(p -> p.id))
                .forEach(p -> sb.append(p.id).append('@')
                        .append(p.position.row).append(',').append(p.position.col).append('|'));
        return sb.toString();
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board b = (Board) o;
        return rows == b.rows && cols == b.cols && pieces.equals(b.pieces);
    }

    @Override public int hashCode() { return Objects.hash(rows, cols, pieces); }
}

/* ---------- Move ---------- */

final class Move {
    final int pieceId;
    final Direction direction;

    Move(int pieceId, Direction direction) {
        this.pieceId = pieceId;
        this.direction = direction;
    }

    @Override public String toString() {
        return "Move(piece=" + pieceId + ", dir=" + direction + ")";
    }
}

/* ---------- Solver Interface ---------- */

interface Solver {
    /** Returns the list of moves that solves the puzzle, or empty list if unsolvable. */
    List<Move> solve(Board start);
}

/* ---------- BFS Solver ---------- */

final class BFSSolver implements Solver {
    @Override
    public List<Move> solve(Board start) {
        if (start.isSolved()) return Collections.emptyList();

        Queue<Board> frontier = new ArrayDeque<>();
        Map<Board, MoveInfo> cameFrom = new HashMap<>();

        frontier.add(start);
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Board current = frontier.poll();
            for (Piece piece : current.pieces.values()) {
                for (Direction dir : Direction.values()) {
                    Optional<Board> nextOpt = current.move(piece.id, dir);
                    if (nextOpt.isEmpty()) continue;
                    Board next = nextOpt.get();
                    if (cameFrom.containsKey(next)) continue;

                    cameFrom.put(next, new MoveInfo(piece.id, dir, current));
                    if (next.isSolved()) return reconstructPath(next, cameFrom);
                    frontier.add(next);
                }
            }
        }
        return Collections.emptyList(); // unsolvable
    }

    private List<Move> reconstructPath(Board goal, Map<Board, MoveInfo> cameFrom) {
        LinkedList<Move> path = new LinkedList<>();
        Board cur = goal;
        while (true) {
            MoveInfo info = cameFrom.get(cur);
            if (info == null) break;
            path.addFirst(new Move(info.pieceId, info.direction));
            cur = info.parent;
        }
        return path;
    }

    private static final class MoveInfo {
        final int pieceId;
        final Direction direction;
        final Board parent;
        MoveInfo(int pieceId, Direction direction, Board parent) {
            this.pieceId = pieceId;
            this.direction = direction;
            this.parent = parent;
        }
    }
}

/* ---------- A* Solver ---------- */

final class AStarSolver implements Solver {
    @Override
    public List<Move> solve(Board start) {
        if (start.isSolved()) return Collections.emptyList();

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.fScore));
        Map<Board, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, null, 0, heuristic(start));
        open.add(startNode);
        allNodes.put(start, startNode);

        while (!open.isEmpty()) {
            Node current = open.poll();
            if (current.board.isSolved()) return reconstructPath(current);

            for (Piece piece : current.board.pieces.values()) {
                for (Direction dir : Direction.values()) {
                    Optional<Board> nextOpt = current.board.move(piece.id, dir);
                    if (nextOpt.isEmpty()) continue;
                    Board nextBoard = nextOpt.get();

                    int tentativeG = current.gScore + 1;
                    Node existing = allNodes.get(nextBoard);
                    if (existing == null || tentativeG < existing.gScore) {
                        Node neighbor = new Node(nextBoard, current, new Move(piece.id, dir), tentativeG, heuristic(nextBoard));
                        allNodes.put(nextBoard, neighbor);
                        open.remove(neighbor); // remove if present with higher fScore