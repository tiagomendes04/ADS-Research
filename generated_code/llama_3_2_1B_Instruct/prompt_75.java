```java
public class Matrix {
    private double[][] data;

    public Matrix(double[][] data) {
        this.data = data;
    }

    public static Matrix add(Matrix a, Matrix b) {
        int rows = Math.max(a.data.length, b.data.length);
        int cols = Math.max(a.data[0].length, b.data[0].length);

        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i < a.data.length && j < a.data[0].length) {
                    result[i][j] += a.data[i][j];
                }
                if (i < b.data.length && j < b.data[0].length) {
                    result[i][j] += b.data[i][j];
                }
            }
        }
        return new Matrix(result);
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        int rowsA = a.data.length;
        int colsA = a.data[0].length;
        int colsB = b.data[0].length;

        if (colsA != b.data.length) {
            throw new RuntimeException("Number of columns in A must be equal to number of rows in B");
        }

        double[][] result = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a.data[i][k] * b.data[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    public static Matrix inverse(Matrix a) {
        int rows = a.data.length;
        int cols = a.data[0].length;

        if (rows != cols) {
            throw new RuntimeException("Matrix must be square");
        }

        // Gaussian elimination
        double[][] result = a.data.clone();
        for (int i = 0; i < rows; i++) {
            if (result[i][i] == 0) {
                int swapIndex = -1;
                for (int j = i + 1; j < rows; j++) {
                    if (result[j][i] != 0) {
                        swapIndex = j;
                        break;
                    }
                }
                if (swapIndex == -1) {
                    throw new RuntimeException("Matrix is singular");
                }
                for (int k = i; k < cols; k++) {
                    double temp = result[i][k];
                    result[i][k] = result[swapIndex][k];
                    result[swapIndex][k] = temp;
                }
            }
            double factor = result[i][i];
            for (int k = 0; k < cols; k++) {
                result[i][k] /= factor;
            }
            for (int j = 0; j < rows; j++) {
                if (j != i) {
                    factor = result[j][i];
                    for (int k = 0; k < cols; k++) {
                        result[j][k] -= factor * result[i][k];
                    }
                }
            }
        }

        // Back substitution
        for (int i = rows - 1; i >= 0; i--) {
            for (int k = i + 1; k < cols; k++) {
                result[i][k] = 0;
            }
        }
        return new Matrix(result);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : data) {
            sb.append("{");
            for (int i = 0; i < row.length; i++) {
                sb.append(row[i]);
                if (i < row.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("}");
            sb.append("\n");
        }
        return sb.toString();
    }
}
```