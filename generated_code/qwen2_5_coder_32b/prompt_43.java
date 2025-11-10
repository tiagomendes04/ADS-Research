```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static List<String[]> parseCSV(String filePath, int expectedColumns) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");
                if (values.length != expectedColumns) {
                    throw new IOException("Error at line " + lineNumber + ": Expected " + expectedColumns + " columns but got " + values.length);
                }
                data.add(values);
            }
        }
        return data;
    }

    public static void main(String[] args) {
        try {
            List<String[]> csvData = parseCSV("example.csv", 3);
            for (String[] row : csvData) {
                for (String value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
```