```java
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleLogger {
    private PrintWriter writer;
    private boolean logToFile;

    public SimpleLogger(String filePath, boolean logToFile) throws IOException {
        this.logToFile = logToFile;
        if (logToFile) {
            this.writer = new PrintWriter(new FileWriter(filePath, true), true);
        }
    }

    public void log(String message) {
        if (logToFile) {
            writer.println(message);
        } else {
            System.out.println(message);
        }
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }

    public static void main(String[] args) throws IOException {
        SimpleLogger logger = new SimpleLogger("log.txt", true);
        logger.log("This is a test log message.");
        logger.close();

        SimpleLogger consoleLogger = new SimpleLogger(null, false);
        consoleLogger.log("This is a console log message.");
    }
}
```