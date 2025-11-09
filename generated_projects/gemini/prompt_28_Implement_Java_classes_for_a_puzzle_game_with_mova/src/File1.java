import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import java.util.Objects;
 import java.util.Random;
 import java.util.Stack;
 
 // Interface for movable puzzle pieces
 interface MovablePiece {
     void move(Direction direction, PuzzleBoard board);
     boolean canMove(Direction direction, PuzzleBoard board);
 }
 
 // Enum for directions
 enum Direction {
     UP, DOWN, LEFT, RIGHT
 }
 
 // Abstract class for puzzle boards
 abstract class PuzzleBoard {
     protected int rows;
     protected int cols;
     protected MovablePiece[][] board;
 
     public PuzzleBoard(int rows, int cols) {
         this.rows = rows;
         this.cols = cols;
         this.board = new MovablePiece[rows][cols];
     }
 
     public int getRows() {
         return rows;
     }
 
     public int getCols() {
         return cols;
     }
 
     public MovablePiece getPiece(int row, int col) {
         return board[row][col];
     }
 
     public void setPiece(int row, int col, MovablePiece piece) {
         board[row][col] = piece;
     }
 
     public abstract boolean isSolved();
     public abstract PuzzleBoard copy();
 
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         PuzzleBoard that = (PuzzleBoard) o;
         if (rows != that.rows || cols != that.cols) return false;
         return Arrays.deepEquals(board, that.board);
     }
 
     @Override
     public int hashCode() {
         int result = Objects.hash(rows, cols);
         result = 31 * result + Arrays.deepHashCode(board);
         return result;
     }
 }
 
 // Abstract class for puzzle solvers
 abstract class PuzzleSolver {
     public abstract List<Direction> solve(PuzzleBoard initialBoard);
 }
 
 // Example implementation: Sliding Tile Puzzle
 class SlidingTile implements MovablePiece {
     private int value;
 
     public SlidingTile(int value) {
         this.value = value;
     }
 
     public int getValue() {
         return value;
     }
 
     @Override
     public void move(Direction direction, PuzzleBoard board) {
         SlidingTilePuzzle slidingTilePuzzle = (SlidingTilePuzzle) board;
         int[] emptyPosition = slidingTilePuzzle.getEmptyTilePosition();
         int emptyRow = emptyPosition[0];
         int emptyCol = emptyPosition[1];
         int tileRow = -1, tileCol = -1;
 
         for (int i = 0; i < board.getRows(); i++) {
             for (int j = 0; j < board.getCols(); j++) {
                 if (board.getPiece(i, j) == this) {
                     tileRow = i;
                     tileCol = j;
                     break;
                 }
             }
             if (tileRow != -1) break;
         }
 
         if (tileRow == -1) return;
 
         board.setPiece(emptyRow, emptyCol, this);
         board.setPiece(tileRow, tileCol, null);
         slidingTilePuzzle.setEmptyTilePosition(tileRow, tileCol);
     }
 
     @Override
     public boolean canMove(Direction direction, PuzzleBoard board) {
         SlidingTilePuzzle slidingTilePuzzle = (SlidingTilePuzzle) board;
         int[] emptyPosition = slidingTilePuzzle.getEmptyTilePosition();
         int emptyRow = emptyPosition[0];
         int emptyCol = emptyPosition[1];
         int tileRow = -1, tileCol = -1;
 
         for (int i = 0; i < board.getRows(); i++) {
             for (int j = 0; j < board.getCols(); j++) {
                 if (board.getPiece(i, j) == this) {
                     tileRow = i;
                     tileCol = j;
                     break;
                 }
             }
             if (tileRow != -1) break;
         }
 
         if (tileRow == -1) return false;
 
         switch (direction) {
             case UP:
                 return tileRow == emptyRow + 1 && tileCol == emptyCol;
             case DOWN:
                 return tileRow == emptyRow - 1 && tileCol == emptyCol;
             case LEFT:
                 return tileCol == emptyCol + 1 && tileRow == emptyRow;
             case RIGHT:
                 return tileCol == emptyCol - 1 && tileRow == emptyRow;
             default:
                 return false;
         }
     }
 
     @Override
     public String toString() {
         return String.valueOf(value);
     }
 
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         SlidingTile that = (SlidingTile) o;
         return value == that.value;
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(value);
     }
 }
 
 class SlidingTilePuzzle extends PuzzleBoard {
     private int[] emptyTilePosition;
 
     public SlidingTilePuzzle(int rows, int cols) {
         super(rows, cols);
         this.emptyTilePosition = new int[2];
     }
 
     public int[] getEmptyTilePosition() {
         return emptyTilePosition;
     }
 
     public void setEmptyTilePosition(int row, int col) {
         this.emptyTilePosition[0] = row;
         this.emptyTilePosition[1] = col;
     }
 
     @Override
     public boolean isSolved() {
         int expectedValue = 1;
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if (i == rows - 1 && j == cols - 1) {
                     if (board[i][j] != null) return false;
                 } else {
                     SlidingTile tile = (SlidingTile) board[i][j];
                     if (tile == null || tile.getValue() != expectedValue) return false;
                 }
                 expectedValue++;
             }
         }
         return true;
     }
 
     @Override
     public PuzzleBoard copy() {
         SlidingTilePuzzle newBoard = new SlidingTilePuzzle(rows, cols);
         newBoard.setEmptyTilePosition(emptyTilePosition[0], emptyTilePosition[1]);
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if (board[i][j] != null) {
                     newBoard.setPiece(i, j, new SlidingTile(((SlidingTile) board[i][j]).getValue()));
                 } else {
                     newBoard.setPiece(i, j, null);
                 }
             }
         }
         return newBoard;
     }
 
     public static SlidingTilePuzzle createInitialState(int rows, int cols) {
         SlidingTilePuzzle board = new SlidingTilePuzzle(rows, cols);
         List<Integer> numbers = new ArrayList<>();
         for (int i = 1; i < rows * cols; i++) {
             numbers.add(i);
         }
         numbers.add(null);
 
         Random random = new Random();
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 Integer number = numbers.remove(random.nextInt(numbers.size()));
                 if (number != null) {
                     board.setPiece(i, j, new SlidingTile(number));
                 } else {
                     board.setPiece(i, j, null);
                     board.setEmptyTilePosition(i, j);
                 }
             }
         }
         return board;
     }
 
     @Override
     public String toString() {
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if (board[i][j] == null) {
                     sb.append("  ");
                 } else {
                     sb.append(String.format("%2d", ((SlidingTile) board[i][j]).getValue()));
                 }
                 sb.append(" ");
             }
             sb.append("\n");
         }
         return sb.toString();
     }
 }
 
 
 // Example implementation: A* search solver
 class AStarSlidingTileSolver extends PuzzleSolver {
 
     private int heuristic(SlidingTilePuzzle board) {
         int manhattanDistance = 0;
         for (int i = 0; i < board.getRows(); i++) {
             for (int j = 0; j < board.getCols(); j++) {
                 SlidingTile tile = (SlidingTile) board.getPiece(i, j);
                 if (tile != null) {
                     int value = tile.getValue();
                     int targetRow = (value - 1) / board.getCols();
                     int targetCol = (value - 1) % board.getCols();
                     manhattanDistance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                 }
             }
         }
         return manhattanDistance;
     }
 
     @Override
     public List<Direction> solve(PuzzleBoard initialBoard) {
         SlidingTilePuzzle startBoard = (SlidingTilePuzzle) initialBoard;
         Stack<Node> stack = new Stack<>();
         Node initialNode = new Node(startBoard, null, null, 0, heuristic(startBoard));
         stack.push(initialNode);
 
         List<PuzzleBoard> visited = new ArrayList<>();
         visited.add(startBoard);
 
         while (!stack.isEmpty()) {
             Node currentNode = stack.pop();
 
             if (currentNode.board.isSolved()) {
                 return constructPath(currentNode);
             }
 
             Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
             for (Direction direction : directions) {
                 SlidingTilePuzzle newBoard = (SlidingTilePuzzle) currentNode.board.copy();
                 int[] emptyPosition = newBoard.getEmptyTilePosition();
                 int emptyRow = emptyPosition[0];
                 int emptyCol = emptyPosition[1];
                 int newRow = emptyRow, newCol = emptyCol;
 
                 switch (direction) {
                     case UP:
                         newRow--;
                         break;
                     case DOWN:
                         newRow++;
                         break;
                     case LEFT:
                         newCol--;
                         break;
                     case RIGHT:
                         newCol++;
                         break;
                 }
 
                 if (newRow >= 0 && newRow < newBoard.getRows() && newCol >= 0 && newCol < newBoard.getCols() && newBoard.getPiece(newRow, newCol) != null) {
                     SlidingTile tile = (SlidingTile) newBoard.getPiece(newRow, newCol);
                     if(tile.canMove(direction, newBoard)) {
                         tile.move(direction, newBoard);
 
                         if (!visited.contains(newBoard)) {
                             visited.add(newBoard);
                             int newCost = currentNode.cost + 1;
                             int newHeuristic = heuristic(newBoard);
                             Node newNode = new Node(newBoard, currentNode, direction, newCost, newHeuristic);
                             stack.push(newNode);
                             stack = sortStack(stack);
                         }
                     }
 
                 }
 
             }
         }
 
         return null; // No solution found
     }
 
     private Stack<Node> sortStack(Stack<Node> input)
     {
         Node[] arr= new Node[input.size()];
         int i = 0;
         while(!input.isEmpty()){
             arr[i++] = input.pop();
         }
         Arrays.sort(arr,(a,b) -> Integer.compare(b.cost + b.heuristic, a.cost + a.heuristic));
         for(Node item: arr){
             input.push(item);
         }
         return input;
     }
 
     private List<Direction> constructPath(Node node) {
         List<Direction> path = new ArrayList<>();
         while (node.direction != null) {
             path.add(0, node.direction);
             node = node.parent;
         }
         return path;
     }
 
     private static class Node {
         SlidingTilePuzzle board;
         Node parent;
         Direction direction;
         int cost;
         int heuristic;
 
         public Node(SlidingTilePuzzle board, Node parent, Direction direction, int cost, int heuristic) {
             this.board = board;
             this.parent = parent;
             this.direction = direction;
             this.cost = cost;
             this.heuristic = heuristic;
         }
     }
 }
 
 // Example usage
 class Main {
     public static void main(String[] args) {
         // Create a Sliding Tile Puzzle
         SlidingTilePuzzle initialBoard = SlidingTilePuzzle.createInitialState(3, 3);
         System.out.println("Initial board:\n" + initialBoard);
 
         // Solve the puzzle
         PuzzleSolver solver = new AStarSlidingTileSolver();
         List<Direction> solution = solver.solve(initialBoard);
 
         if (solution != null) {
             System.out.println("Solution found in " + solution.size() + " moves:");
             SlidingTilePuzzle currentBoard = (SlidingTilePuzzle) initialBoard.copy();
             System.out.println("Initial:\n" + currentBoard);
             for (Direction direction : solution) {
                 int[] emptyPosition = currentBoard.getEmptyTilePosition();
                 int emptyRow = emptyPosition[0];
                 int emptyCol = emptyPosition[1];
                 int newRow = emptyRow, newCol = emptyCol;
 
                 switch (direction) {
                     case UP:
                         newRow--;
                         break;
                     case DOWN:
                         newRow++;
                         break;
                     case LEFT:
                         newCol--;
                         break;
                     case RIGHT:
                         newCol++;
                         break;
                 }
                 SlidingTile tile = (SlidingTile) currentBoard.getPiece(newRow, newCol);
                 tile.move(direction, currentBoard);
                 System.out.println("Move " + direction + ":\n" + currentBoard);
             }
 
             if (currentBoard.isSolved()) {
                 System.out.println("Puzzle solved successfully!");
             } else {
                 System.out.println("Error: Puzzle not solved correctly.");
             }
         } else {
             System.out.println("No solution found.");
         }
     }
 }