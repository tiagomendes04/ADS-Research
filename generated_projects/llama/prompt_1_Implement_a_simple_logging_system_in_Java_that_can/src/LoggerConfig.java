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