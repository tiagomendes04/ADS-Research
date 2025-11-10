```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static List<CSVRow> parseCSV(String filePath) {
        List<CSVRow> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 0) {
                    continue;
                }
                rows.add(new CSVRow(values));
            }
        } catch (IOException e) {
            System.err.println("Error parsing CSV file: " + e.getMessage());
        }
        return rows;
    }

    public static class CSVRow {
        private List<String> values;

        public CSVRow(String[] values) {
            this.values = values;
        }

        public List<String> getValues() {
            return values;
        }
    }

    public static void main(String[] args) {
        String filePath = "path_to_your_csv_file.csv";
        List<CSVRow> rows = parseCSV(filePath);
        for (CSVRow row : rows) {
            System.out.println(row.getValues());
        }
    }
}
```