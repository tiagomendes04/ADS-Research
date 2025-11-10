```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerLogMonitor {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                monitorLogs();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void monitorLogs() throws IOException {
        String logFilePath = "/path/to/server.log";
        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("ERROR")) {
                generateAlert(line);
            }
        }
        reader.close();
    }

    private static void generateAlert(String logLine) {
        String alertMessage = "Error detected: " + logLine;
        System.out.println("ALERT: " + LocalDateTime.now().format(DATE_FORMATTER) + " - " + alertMessage);
    }
}
```