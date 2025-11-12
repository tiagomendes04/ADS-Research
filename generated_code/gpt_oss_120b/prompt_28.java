```java
package puzzle;

import java.util.*;
import java.util.stream.Collectors;

/* ---------- Core Model ---------- */

final class Position {
    final int row, col;
    Position(int row, int col) { this.row = row; this.col = col; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return row == p.row && col == p.col;
    }
    @Override public int hashCode() { return Objects.hash(row, col); }
}

/** Immutable tile (piece). Zero represents the empty slot. */
final class Tile {
    final int value;               // 0 = empty, >0 = numbered tile
    Tile(int value) { this.value = value; }
    boolean isEmpty() { return value == 0; }
}

/** Immutable board state. */
final class Board {
    private final int rows, cols;
    private final int[][] grid;               // rows x cols
    private final Position emptyPos;           // location of zero

    Board(int[][] grid) {
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = new int[rows][cols];
        Position empty = null;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                this.grid[r][c] = grid[r][c];
                if (grid[r][c] == 0) empty = new Position(r, c);
            }
        }
        if (empty == null) throw new IllegalArgumentException("Board must contain a zero tile");
        this.emptyPos = empty;
    }

    int rows() { return rows; }
    int cols() { return cols; }
    Position emptyPos() { return emptyPos; }

    int get(int r, int c) { return grid[r][c]; }

    /** Returns a new Board after sliding tile at (srcRow,srcCol) into the empty slot. */
    Board move(int srcRow, int srcCol) {
        if (!isAdjacent(srcRow, srcCol, emptyPos.row, emptyPos.col))
            throw new IllegalArgumentException("Tiles not adjacent to empty slot");
        int[][] next = copyGrid();
        next[emptyPos.row][emptyPos.col] = next[srcRow][srcCol];
        next[srcRow][srcCol] = 0;
        return new Board(next);
    }

    /** All possible moves from this state. */
    List<Board> neighbours() {
        List<Board> list = new ArrayList<>();
        int r = emptyPos.row, c = emptyPos.col;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (inBounds(nr, nc)) list.add(move(nr, nc));
        }
        return list;
    }

    /** Goal test – tiles in row‑major order ending with zero. */
    boolean isGoal() {
        int expected = 1;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                if (r == rows-1 && c == cols-1) {
                    if (grid[r][c] != 0) return false;
                } else {
                    if (grid[r][c] != expected++) return false;
                }
            }
        }
        return true;
    }

    /** Heuristic: Manhattan distance sum (for A* if needed). */
    int manhattan() {
        int sum = 0;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                int val = grid[r][c];
                if (val == 0) continue;
                int targetRow = (val - 1) / cols;
                int targetCol = (val - 1) % cols;
                sum += Math.abs(r - targetRow) + Math.abs(c - targetCol);
            }
        }
        return sum;
    }

    private boolean inBounds(int r, int c) { return r >= 0 && r < rows && c >= 0 && c < cols; }
    private boolean isAdjacent(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2) == 1;
    }
    private int[][] copyGrid() {
        int[][] copy = new int[rows][cols];
        for (int i = 0; i < rows; ++i) copy[i] = grid[i].clone();
        return copy;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board b = (Board) o;
        return rows == b.rows && cols == b.cols && Arrays.deepEquals(grid, b.grid);
    }
    @Override public int hashCode() { return Arrays.deepHashCode(grid); }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : grid) {
            for (int v : row) sb.append(String.format("%2d ", v));
            sb.append('\n');
        }
        return sb.toString();
    }
}

/* ---------- Solver Infrastructure ---------- */

interface PuzzleSolver {
    /** Returns a list of board states from start to goal, inclusive. Empty list if unsolvable. */
    List<Board> solve(Board start);
}

/* Breadth‑First Search – guarantees shortest solution (in moves). */
class BFSSolver implements PuzzleSolver {
    @Override
    public List<Board> solve(Board start) {
        if (start.isGoal()) return List.of(start);
        Queue<Board> q = new ArrayDeque<>();
        Map<Board, Board> parent = new HashMap<>();
        q.add(start);
        parent.put(start, null);
        while (!q.isEmpty()) {
            Board cur = q.poll();
            for (Board nb : cur.neighbours()) {
                if (parent.containsKey(nb)) continue;
                parent.put(nb, cur);
                if (nb.isGoal()) return reconstructPath(parent, nb);
                q.add(nb);
            }
        }
        return List.of(); // unsolvable
    }

    private List<Board> reconstructPath(Map<Board, Board> parent, Board goal) {
        Deque<Board> stack = new ArrayDeque<>();
        for (Board b = goal; b != null; b = parent.get(b)) stack.push(b);
        return new ArrayList<>(stack);
    }
}

/* Depth‑First Search – may find a solution quickly but not optimal. */
class DFSSolver implements PuzzleSolver {
    private final int maxDepth;
    DFSSolver(int maxDepth) { this.maxDepth = maxDepth; }

    @Override
    public List<Board> solve(Board start) {
        Set<Board> visited = new HashSet<>();
        List<Board> path = new ArrayList<>();
        if (dfs(start, visited, path, 0)) return new ArrayList<>(path);
        return List.of();
    }

    private boolean dfs(Board cur, Set<Board> visited, List<Board> path, int depth) {
        visited.add(cur);
        path.add(cur);
        if (cur.isGoal()) return true;
        if (depth >= maxDepth) { path.remove(path.size() - 1); return false; }
        for (Board nb : cur.neighbours()) {
            if (visited.contains(nb)) continue;
            if (dfs(nb, visited, path, depth + 1)) return true;
        }
        path.remove(path.size() - 1);
        return false;
    }
}

/* A* Solver – uses Manhattan distance heuristic. */
class AStarSolver implements PuzzleSolver {
    @Override
    public List<Board> solve(Board start) {
        if (start.isGoal()) return List.of(start);
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<Board, Node> all = new HashMap<>();

        Node startNode = new Node(start, null, 0, start.manhattan());
        open.add(startNode);
        all.put(start, startNode);

        while (!open.isEmpty()) {
            Node cur = open.poll();
            if (cur.board.isGoal()) return reconstruct(cur);
            for (Board nb : cur.board.neighbours()) {
                int tentativeG = cur.g + 1;
                Node existing = all.get(nb);
                if (existing == null || tentativeG < existing.g) {
                    Node next = new Node(nb, cur, tentativeG, nb.manhattan());
                    open.remove(existing);
                    open.add(next);
                    all.put(nb, next);
                }
            }
        }
        return List.of(); // unsolvable
    }

    private List<Board> reconstruct(Node goal) {
        Deque<Board> stack = new ArrayDeque<>();
        for (Node n = goal; n != null; n = n.parent) stack.push(n.board);
        return new ArrayList<>(stack);
    }

    private static final class Node {
        final Board board;
        final Node parent;
        final int g;          // cost from start
        final int h;          // heuristic
        final int f;          // g + h
        Node(Board board, Node parent, int g, int h) {
            this.board = board;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }
}

/* ---------- Game Driver (example usage) ---------- */

public class PuzzleGame {
    public static void main(String[] args) {
        int[][] start = {
                {2, 8, 3},
                {1, 6, 4},
                {7, 0, 5}
        };
        Board board = new Board(start);
        System.out.println("Start board:");
        System.out.println(board);

        PuzzleSolver solver = new BFSSolver();   // swap for DFSSolver or AStarSolver
        long t0 = System.nanoTime();
        List<Board> solution = solver.solve(board);
        long t1 = System.nanoTime();

        if (solution.isEmpty()) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution found in " + (solution.size() - 1) + " moves.");
            for (int i = 0; i < solution.size(); ++i) {
                System.out.println("Step " + i + ":");
                System.out.println(solution.get(i));
            }
        }
        System.out.printf("Elapsed: %.3f ms%n", (t1 - t0) / 1_000_000.0);
    }
}
```