```java
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private static final String CONFIG_FILE = "logger.properties";

    private boolean consoleLogEnabled;
    private boolean fileLogEnabled;
    private String consoleLogMessageFormat;
    private String fileLogMessageFormat;

    public Logger() {
        try {
            loadConfiguration();
        } catch (IOException e) {
            System.err.println("Could not load logger configuration: " + e.getMessage());
        }
    }

    public void log(String message, Object... args) {
        if (consoleLogEnabled) {
            System.out.println(String.format(consoleLogMessageFormat, args));
        }
        if (fileLogEnabled) {
            String formattedMessage = String.format(fileLogMessageFormat, args);
            try (FileWriter fileWriter = new FileWriter(CONFIG_FILE, true)) {
                fileWriter.write(formattedMessage + "\n");
            } catch (IOException e) {
                System.err.println("Could not write to log file: " + e.getMessage());
            }
        }
    }

    private void loadConfiguration() throws IOException {
        // TODO: Implement loading configuration from properties file
    }

    public void setConsoleLogEnabled(boolean enabled) {
        consoleLogEnabled = enabled;
    }

    public void setFileLogEnabled(boolean enabled) {
        fileLogEnabled = enabled;
    }

    public void setConsoleLogMessageFormat(String format) {
        consoleLogMessageFormat = format;
    }

    public void setFileLogMessageFormat(String format) {
        fileLogMessageFormat = format;
    }
}
```