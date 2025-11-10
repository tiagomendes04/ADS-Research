```java
package com.example.matrix;

import java.util.Arrays;

/**
 * Simple matrix library supporting addition, multiplication, and inversion.
 */
public class Matrix {
    private final int rows;
    private final int cols;
    private final double[][] data;

    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("Matrix dimensions must be positive.");
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public Matrix(double[][] values) {
        if (values == null || values.length == 0 || values[0].length == 0)
            throw new IllegalArgumentException("Input array must be non‑empty.");
        this.rows = values.length;
        this.cols = values[0].length;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            if (values[i].length != cols)
                throw new IllegalArgumentException("All rows must have the same length.");
            System.arraycopy(values[i], 0, this.data[i], 0, cols);
        }
    }

    public static Matrix identity(int n) {
        Matrix id = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            id.data[i][i] = 1.0;
        }
        return id;
    }

    public Matrix add(Matrix other) {
        checkDimensionMatch(other);
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] + other.data[i][j];
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows)
            throw new IllegalArgumentException("Incompatible dimensions for multiplication.");
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int k = 0; k < this.cols; k++) {
                double aik = this.data[i][k];
                for (int j = 0; j < other.cols; j++) {
                    result.data[i][j] += aik * other.data[k][j];
                }
            }
        }
        return result;
    }

    public Matrix invert() {
        if (rows != cols)
            throw new IllegalArgumentException("Only square matrices can be inverted.");
        int n = rows;
        Matrix a = this.copy();
        Matrix inv = Matrix.identity(n);

        for (int i = 0; i < n; i++) {
            // Find pivot
            int maxRow = i;
            for (int r = i + 1; r < n; r++) {
                if (Math.abs(a.data[r][i]) > Math.abs(a.data[maxRow][i])) {
                    maxRow = r;
                }
            }
            if (Math.abs(a.data[maxRow][i]) < 1e-12)
                throw new ArithmeticException("Matrix is singular and cannot be inverted.");

            // Swap rows in both matrices
            a.swapRows(i, maxRow);
            inv.swapRows(i, maxRow);

            // Normalize pivot row
            double pivot = a.data[i][i];
            for (int c = 0; c < n; c++) {
                a.data[i][c] /= pivot;
                inv.data[i][c] /= pivot;
            }

            // Eliminate other rows
            for (int r = 0; r < n; r++) {
                if (r == i) continue;
                double factor = a.data[r][i];
                for (int c = 0; c < n; c++) {
                    a.data[r][c] -= factor * a.data[i][c];
                    inv.data[r][c] -= factor * inv.data[i][c];
                }
            }
        }
        return inv;
    }

    private void swapRows(int r1, int r2) {
        double[] tmp = data[r1];
        data[r1] = data[r2];
        data[r2] = tmp;
    }

    private Matrix copy() {
        Matrix copy = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            System.arraycopy(this.data[i], 0, copy.data[i], 0, cols);
        }
        return copy;
    }

    private void checkDimensionMatch(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols)
            throw new IllegalArgumentException("Matrix dimensions must match.");
    }

    public int getRowCount() {
        return rows;
    }

    public int getColumnCount() {
        return cols;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, double value) {
        data[row][col] = value;
    }

    public double[][] toArray() {
        double[][] copy = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(this.data[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix ").append(rows).append("x").append(cols).append(":\n");
        for (double[] row : data) {
            sb.append("  ").append(Arrays.toString(row)).append('\n');
        }
        return sb.toString();
    }
}
```