```java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("LoggerConfig");
        logger.setLevel(Level.ALL);

        Handler consoleHandler = new ConsoleHandler();
        Handler fileHandler = new FileHandler("log.txt", true);

        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);

        logger.info("This is an info message");
        logger.warning("This is a warning message");
        logger.severe("This is a severe message");

        consoleHandler.close();
        fileHandler.close();
    }

    static class ConsoleHandler extends java.util.logging.Handler {
        @Override
        public void publish(LogRecord record) {
            System.out.println(record.getMessage());
        }

        @Override
        public void close() throws SecurityException {
        }

        @Override
        public void flush() {
        }
    }

    static class FileHandler extends java.util.logging.Handler {
        private final File file;
        private final PrintWriter writer;

        public FileHandler(String filename, boolean append) {
            this.file = new File(filename);
            try {
                writer = new PrintWriter(new FileWriter(file, append));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void publish(LogRecord record) {
            writer.println(record.getLevel().getName() + " - " + record.getMessage());
        }

        @Override
        public void close() throws SecurityException {
            writer.close();
        }

        @Override
        public void flush() {
            writer.flush();
        }
    }
}
```

```java
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomFormatter extends Formatter {
    @Override
    public String format(java.util.logging.LogRecord record) {
        return String.format("%1$tF %1$tT - %2$s - %3$s%n",
                record.getMillis(),
                record.getLevel().getName(),
                record.getMessage());
    }
}
```

```java
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LoggerConfig {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("LoggerConfig");
        logger.setLevel(Level.ALL);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());

        Handler fileHandler = new FileHandler("log.txt", true);
        fileHandler.setFormatter(new CustomFormatter());

        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);

        logger.info("This is an info message");
        logger.warning("This is a warning message");
        logger.severe("This is a severe message");

        consoleHandler.close();
        fileHandler.close();
    }
}
```