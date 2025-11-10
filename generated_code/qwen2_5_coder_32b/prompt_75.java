```java
public class Matrix {
    private double[][] data;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = data;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getElement(int row, int col) {
        return data[row][col];
    }

    public void setElement(int row, int col, double value) {
        data[row][col] = value;
    }

    public Matrix add(Matrix other) {
        if (rows != other.getRows() || cols != other.getCols()) {
            throw new IllegalArgumentException("Matrices must have the same dimensions to be added.");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setElement(i, j, this.getElement(i, j) + other.getElement(i, j));
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        if (cols != other.getRows()) {
            throw new IllegalArgumentException("Number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }
        Matrix result = new Matrix(rows, other.getCols());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.getCols(); j++) {
                for (int k = 0; k < cols; k++) {
                    result.setElement(i, j, result.getElement(i, j) + this.getElement(i, k) * other.getElement(k, j));
                }
            }
        }
        return result;
    }

    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Only square matrices can be inverted.");
        }
        double[][] inverse = new double[rows][cols];
        double det = determinant();
        if (det == 0) {
            throw new ArithmeticException("Matrix is singular and cannot be inverted.");
        }
        if (rows == 1) {
            inverse[0][0] = 1 / det;
        } else {
            double[][] cofactors = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    cofactors[i][j] = Math.pow(-1, i + j) * minor(i, j).determinant();
                }
            }
            Matrix adjugate = new Matrix(cofactors).transpose();
            inverse = adjugate.scalarMultiply(1 / det).data;
        }
        return new Matrix(inverse);
    }

    private Matrix minor(int row, int col) {
        double[][] minorData = new double[rows - 1][cols - 1];
        for (int i = 0, minorI = 0; i < rows; i++) {
            if (i != row) {
                for (int j = 0, minorJ = 0; j < cols; j++) {
                    if (j != col) {
                        minorData[minorI][minorJ] = data[i][j];
                        minorJ++;
                    }
                }
                minorI++;
            }
        }
        return new Matrix(minorData);
    }

    private double determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Determinant can only be calculated for square matrices.");
        }
        if (rows == 1) {
            return data[0][0];
        } else if (rows == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        } else {
            double det = 0;
            for (int i = 0; i < cols; i++) {
                det += Math.pow(-1, i) * data[0][i] * minor(0, i).determinant();
            }
            return det;
        }
    }

    private Matrix transpose() {
        double[][] transposedData = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedData[j][i] = data[i][j];
            }
        }
        return new Matrix(transposedData);
    }

    private Matrix scalarMultiply(double scalar) {
        double[][] multipliedData = new double[rows][