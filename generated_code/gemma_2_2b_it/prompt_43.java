```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser {

    public static void main(String[] args) {
        File file = new File("data.csv");
        try {
            List<String[]> data = parseCSV(file);
            for (String[] row : data) {
                System.out.println(Arrays.toString(row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> parseCSV(File file) throws Exception {
        List<String[]> data = new ArrayList<>();
        String line;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] row = line.split(",");
                if (row.length == 0 || row.length > 100) {
                    throw new Exception("Invalid line format: " + line);
                }
                data.add(row);
            }
        } catch (FileNotFoundException e) {
            throw new Exception("File not found: " + file.getAbsolutePath());
        }
        return data;
    }
}
```