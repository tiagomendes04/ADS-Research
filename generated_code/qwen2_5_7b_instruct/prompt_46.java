```java
public class BoardGameEngine {
    private Piece[] pieces;
    private int[][] board;
    private int score;

    public BoardGameEngine(int size) {
        pieces = new Piece[size * size];
        board = new int[size][size];
        score = 0;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void placePiece(int x, int y, Piece piece) {
        if (board[x][y] == 0) {
            pieces[piece.getId()] = piece;
            board[x][y] = piece.getId() + 1;
        }
    }

    public void movePiece(int id, int newX, int newY) {
        if (isValidMove(id, newX, newY)) {
            Piece piece = pieces[id];
            int oldX = piece.getX();
            int oldY = piece.getY();
            board[oldX][oldY] = 0;
            piece.setX(newX);
            piece.setY(newY);
            board[newX][newY] = id + 1;
        }
    }

    private boolean isValidMove(int id, int newX, int newY) {
        Piece piece = pieces[id];
        if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[newX].length) {
            return false;
        }
        if (board[newX][newY] != 0 && board[newX][newY] - 1 != id) {
            return false;
        }
        return true;
    }

    public void calculateScore() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0) {
                    int pieceId = board[i][j] - 1;
                    Piece piece = pieces[pieceId];
                    piece.increaseScore();
                    score += piece.getScore();
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public static class Piece {
        private int id;
        private int x;
        private int y;
        private int score;

        public Piece(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getId() {
            return id;
        }

        public void increaseScore() {
            score++;
        }

        public int getScore() {
            return score;
        }
    }
}
```