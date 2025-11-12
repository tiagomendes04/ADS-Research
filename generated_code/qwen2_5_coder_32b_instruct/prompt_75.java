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
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions to be added.");
        }
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setElement(i, j, this.getElement(i, j) + other.getElement(i, j));
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    result.setElement(i, j, result.getElement(i, j) + this.getElement(i, k) * other.getElement(k, j));
                }
            }
        }
        return result;
    }

    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix must be square to be inverted.");
        }
        double[][] inverseData = new double[rows][cols];
        double[][] tempData = copyData(data);
        int[] index = new int[rows];
        luDecomposition(tempData, index);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                inverseData[i][j] = 0.0;
            }
            inverseData[i][i] = 1.0;
        }
        luBackSubstitution(tempData, index, inverseData);
        return new Matrix(inverseData);
    }

    private double[][] copyData(double[][] original) {
        double[][] copy = new double[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    private void luDecomposition(double[][] a, int[] index) {
        double[][] lu = a;
        int n = lu.length;
        double[] scale = new double[n];
        for (int i = 0; i < n; i++) {
            double max = 0.0;
            for (int j = 0; j < n; j++) {
                double absAij = Math.abs(lu[i][j]);
                if (absAij > max) {
                    max = absAij;
                }
            }
            if (max == 0.0) {
                throw new ArithmeticException("Singular matrix");
            }
            scale[i] = 1.0 / max;
        }
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < j + 1; i++) {
                double sum = lu[i][j];
                for (int k = 0; k < i; k++) {
                    sum -= lu[i][k] * lu[k][j];
                }
                lu[i][j] = sum;
            }
            if (j < n - 1) {
                for (int i = j + 1; i < n; i++) {
                    double sum = lu[i][j];
                    for (int k = 0; k < j; k++) {
                        sum -= lu[i][k] * lu[k][j];
                    }
                    lu[i][j] = sum;
                    double piv = lu[i][j] * scale[i];
                    int pivotIndex = j;
                    for (int k = j + 1; k < n; k++) {
                        doubleakk = lu[k][j] * scale[k];
                        if (akk > piv) {
                            piv = akk;
                            pivotIndex = k;
                        }
                    }
                    if (pivotIndex != j) {
                        for (int k = 0; k < n; k++) {
                            double t = lu[pivotIndex][k];
                            lu[pivotIndex][k] = lu[j][k];
                            lu[j][k] = t;
                        }
                        int k = index[j];
                        index[j] = index[pivotIndex];
                        index[pivotIndex] = k;
                        double s = scale[j];
                        scale[j] = scale[pivotIndex];
                        scale[pivotIndex] = s;
                    }
                    if (lu[j][j] == 0.0) {
                        lu[j][j] = 1e-20;
                    }
                    if (pivotIndex != j) {
                        lu[i][j] /= lu[j][j];
                    }
                }
            }
        }
    }

    private void luBackSubstitution(double[][] lu, int[] index, double[][] b) {
        int n = lu.length;
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(b[index[i]], 0, x, 0, n);
            for (int j = 0; j < i; j++) {
                x[j] -= lu[i][j] * x[j];
            }
            b[i] = x;
        }
        for (int i = n - 1; i >= 0; i--) {
            x[i] = b[i][i] / lu[i][i];
            for (int j = i - 1; j >= 0; j--) {
                b[j][i] -= lu[j][i] * x[i];
            }
            b[i] = x;
        }
    }
}
```