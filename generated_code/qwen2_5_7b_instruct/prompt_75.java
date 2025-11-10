```java
import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private List<List<Double>> elements;

    public Matrix(List<List<Double>> elements) {
        this.elements = elements;
    }

    public static Matrix add(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition.");
        }
        List<List<Double>> result = new ArrayList<>();
        for (int i = 0; i < a.getRows(); i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < a.getColumns(); j++) {
                row.add(a.elements.get(i).get(j) + b.elements.get(i).get(j));
            }
            result.add(row);
        }
        return new Matrix(result);
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows()) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second matrix for multiplication.");
        }
        List<List<Double>> result = new ArrayList<>();
        for (int i = 0; i < a.getRows(); i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < b.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < a.getColumns(); k++) {
                    sum += a.elements.get(i).get(k) * b.elements.get(k).get(j);
                }
                row.add(sum);
            }
            result.add(row);
        }
        return new Matrix(result);
    }

    public static Matrix inverse(Matrix matrix) {
        int size = matrix.getRows();
        if (size != matrix.getColumns()) {
            throw new IllegalArgumentException("Matrix must be square to find the inverse.");
        }
        List<List<Double>> augmented = new ArrayList<>();
        for (List<Double> row : matrix.elements) {
            augmented.add(new ArrayList<>(row));
        }
        for (int i = 0; i < size; i++) {
            augmented.get(i).add(i + size);
        }
        for (int i = 0; i < size; i++) {
            double pivot = augmented.get(i).get(i);
            for (int j = 0; j < 2 * size; j++) {
                augmented.get(i).set(j, augmented.get(i).get(j) / pivot);
            }
            for (int k = 0; k < size; k++) {
                if (k != i) {
                    double factor = augmented.get(k).get(i);
                    for (int j = 0; j < 2 * size; j++) {
                        augmented.get(k).set(j, augmented.get(k).get(j) - factor * augmented.get(i).get(j));
                    }
                }
            }
        }
        List<List<Double>> inverse = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = size; j < 2 * size; j++) {
                row.add(augmented.get(i).get(j));
            }
            inverse.add(row);
        }
        return new Matrix(inverse);
    }

    public int getRows() {
        return elements.size();
    }

    public int getColumns() {
        return elements.get(0).size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Double> row : elements) {
            for (Double element : row) {
                sb.append(element).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
```