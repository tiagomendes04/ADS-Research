```java
// Matrix.java

public class Matrix {
    private double[][] data;
    private int rows;
    private int cols;

    public Matrix(double[][] data) {
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
    }

    public double[][] getData() {
        return data;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public static Matrix add(Matrix A, Matrix B) {
        if (A.getRows() != B.getRows() || A.getCols() != B.getCols()) {
            throw new RuntimeException("Matrices must have the same dimensions for addition");
        }

        double[][] result = new double[A.getRows()][A.getCols()];
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getCols(); j++) {
                result[i][j] = A.getData()[i][j] + B.getData()[i][j];
            }
        }

        return new Matrix(result);
    }

    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.getCols() != B.getRows()) {
            throw new RuntimeException("Number of columns in A must be equal to the number of rows in B for multiplication");
        }

        double[][] result = new double[A.getRows()][B.getCols()];
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < B.getCols(); j++) {
                for (int k = 0; k < A.getCols(); k++) {
                    result[i][j] += A.getData()[i][k] * B.getData()[k][j];
                }
            }
        }

        return new Matrix(result);
    }

    public static Matrix invert(Matrix A) {
        if (A.getRows() != A.getCols()) {
            throw new RuntimeException("Matrix must be square for inversion");
        }

        double det = 0;
        for (int i = 0; i < A.getRows(); i++) {
            det += Math.pow(-1, i) * A.getData()[i][i] * cofactor(A, i, i);
        }

        if (det == 0) {
            throw new RuntimeException("Matrix is singular and cannot be inverted");
        }

        double[][] result = new double[A.getRows()][A.getCols()];
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getCols(); j++) {
                result[i][j] = cofactor(A, i, j) / det;
            }
        }

        return new Matrix(result);
    }

    private static double cofactor(Matrix A, int row, int col) {
        double[][] cofactorMatrix = new double[A.getRows() - 1][A.getCols() - 1];
        int cofactorRow = 0;
        for (int i = 0; i < A.getRows(); i++) {
            if (i == row) {
                continue;
            }
            int cofactorCol = 0;
            for (int j = 0; j < A.getCols(); j++) {
                if (j == col) {
                    continue;
                }
                cofactorMatrix[cofactorRow][cofactorCol] = A.getData()[i][j];
                cofactorCol++;
            }
            cofactorRow++;
        }

        return Math.pow(-1, row + col) * determinant(cofactorMatrix);
    }

    private static double determinant(double[][] matrix) {
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double det = 0;
        for (int i = 0; i < matrix.length; i++) {
            double[][] subMatrix = new double[matrix.length - 1][matrix.length - 1];
            int subMatrixRow = 0;
            for (int j = 1; j < matrix.length; j++) {
                int subMatrixCol = 0;
                for (int k = 0; k < matrix.length; k++) {
                    if (k == i) {
                        continue;
                    }
                    subMatrix[subMatrixRow][subMatrixCol] = matrix[j][k];
                    subMatrixCol++;
                }
                subMatrixRow++;
            }
            det += Math.pow(-1, i) * matrix[0][i] * determinant(subMatrix);
        }

        return det;
    }
}
```

```java
// Main.java

public class Main {
    public static void main(String[] args) {
        double[][] A = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        double[][] B = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};

        Matrix matrixA = new Matrix(A);
        Matrix matrixB = new Matrix(B);

        Matrix sum = Matrix.add(matrixA, matrixB);
        Matrix product = Matrix.multiply(matrixA, matrixB);
        Matrix inverse = Matrix.invert(matrixA);

        System.out.println("Sum:");
        for (double[] row : sum.getData()) {
            System.out.println(java.util.Arrays.toString(row));
        }

        System.out.println("Product:");
        for (double[] row : product.getData()) {
            System.out.println(java.util.Arrays.toString(row));
        }

        System.out.println("Inverse:");
        for (double[] row : inverse.getData()) {
            System.out.println(java.util.Arrays.toString(row));
        }
    }
}
```