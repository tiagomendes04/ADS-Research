```java
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger {

    private static final Logger logger = Logger.getLogger(SimpleLogger.class.getName());

    public SimpleLogger(boolean logToFile) {
        if (logToFile) {
            try {
                logger.setUseParentHandlers(false);
                logger.addHandler(new FileHandler("app.log"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(String message, Level level) {
        logger.log(level, message);
    }

    public static void main(String[] args) {
        SimpleLogger logger = new SimpleLogger(true);
        logger.log("This is an info message", Level.INFO);
        logger.log("This is a warning message", Level.WARNING);
    }
}
```