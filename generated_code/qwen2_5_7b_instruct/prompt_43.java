```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public static class CsvException extends RuntimeException {
        public CsvException(String message) {
            super(message);
        }
    }

    public static class CsvRecord {
        private final List<String> fields;

        public CsvRecord(List<String> fields) {
            this.fields = fields;
        }

        public List<String> getFields() {
            return fields;
        }
    }

    public static List<CsvRecord> parseCsv(String filePath) throws CsvException {
        List<CsvRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 4) {
                    throw new CsvException("Invalid number of fields in line: " + line);
                }
                records.add(new CsvRecord(List.of(fields[0], fields[1], fields[2], fields[3])));
            }
        } catch (IOException e) {
            throw new CsvException("Error reading file: " + filePath, e);
        }
        return records;
    }

    public static void main(String[] args) {
        try {
            List<CsvRecord> records = parseCsv("path/to/yourfile.csv");
            for (CsvRecord record : records) {
                System.out.println(record.getFields());
            }
        } catch (CsvException e) {
            System.err.println("CSV Parsing Error: " + e.getMessage());
        }
    }
}
```