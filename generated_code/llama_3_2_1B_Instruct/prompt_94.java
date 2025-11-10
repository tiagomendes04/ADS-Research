```java
import java.util.*;
import java.util.logging.*;
import java.util.logging.file.*;

public class ServerLogMonitor {
    private static final String LOG_FILE = "/var/log/apache2/access.log";
    private static final Logger logger = Logger.getLogger(ServerLogMonitor.class.getName());
    private static final Set<String> alerts = new HashSet<>();

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    File file = new File(LOG_FILE);
                    logger.info("Log file: " + file.getName());
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (isAlert(line)) {
                            logger.severe(line);
                            alerts.add(line);
                        }
                    }
                    scanner.close();
                } catch (IOException e) {
                    logger.severe("Error reading log file: " + e.getMessage());
                }
                try {
                    Thread.sleep(60); // read log file every minute
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    private static boolean isAlert(String line) {
        String[] words = line.split("\\s+");
        for (String word : words) {
            if (word.toLowerCase().contains("error") || word.toLowerCase().contains("warning")) {
                return true;
            }
        }
        return false;
    }
}
```