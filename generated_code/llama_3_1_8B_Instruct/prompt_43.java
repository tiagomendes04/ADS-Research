```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static class CSVRow {
        public String[] values;

        public CSVRow(String[] values) {
            this.values = values;
        }
    }

    public static class CSVError {
        public int lineNumber;
        public String message;

        public CSVError(int lineNumber, String message) {
            this.lineNumber = lineNumber;
            this.message = message;
        }
    }

    public static List<CSVRow> parseCSV(String filePath, boolean validate) {
        List<CSVRow> rows = new ArrayList<>();
        List<CSVError> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (validate) {
                    if (values.length == 0) {
                        errors.add(new CSVError(lineNumber, "Empty row"));
                    } else {
                        for (String value : values) {
                            if (value.trim().isEmpty()) {
                                errors.add(new CSVError(lineNumber, "Empty value"));
                            }
                        }
                    }
                }

                rows.add(new CSVRow(values));
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error parsing CSV file: " + e.getMessage());
        }

        return rows;
    }

    public static void main(String[] args) {
        List<CSVRow> rows = parseCSV("example.csv", true);
        List<CSVError> errors = new ArrayList<>();

        for (CSVRow row : rows) {
            System.out.println("Row " + rows.indexOf(row) + ": " + String.join(", ", row.values));
        }

        for (CSVError error : errors) {
            System.out.println("Error on line " + error.lineNumber + ": " + error.message);
        }
    }
}
```