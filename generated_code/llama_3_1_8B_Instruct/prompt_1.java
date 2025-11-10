```java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logger {
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    static {
        setLevel(Level.ALL);
        setFormatter(new SimpleFormatter());
    }

    public static void setLevel(Level level) {
        logger.setLevel(level);
        logger.addHandler(getConsoleHandler());
        logger.addHandler(getFileHandler());
    }

    public static void setFormatter(Formatter formatter) {
        logger.setFormatter(formatter);
        logger.addHandler(getConsoleHandler());
        logger.addHandler(getFileHandler());
    }

    private static ConsoleHandler getConsoleHandler() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        return handler;
    }

    private static FileHandler getFileHandler() {
        try {
            File file = new File("log.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileHandler handler = new FileHandler(file.getAbsolutePath(), true);
            handler.setLevel(Level.ALL);
            return handler;
        } catch (IOException e) {
            return null;
        }
    }

    public static void log(String message, Level level) {
        logger.log(level, message);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Logger.setLevel(Level.ALL);
        Logger.log("Hello, world!", Level.INFO);
        Logger.log("Error occurred", Level.SEVERE);
    }
}
```