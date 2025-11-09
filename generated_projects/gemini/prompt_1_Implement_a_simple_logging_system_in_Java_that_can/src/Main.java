// Main.java
public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        // --- Console Logging Example ---
        System.out.println("--- Starting Console Logging Test ---");
        logger.setMinLogLevel(LogLevel.INFO); // Set minimum level to INFO
        logger.setDestination(LogDestination.CONSOLE); // Set destination to console

        logger.info("This is an INFO message to console.");
        logger.warn("This is a WARNING message to console.");
        logger.error("This is an ERROR message to console.");
        
        System.out.println("--- Changing Console Log Level ---");
        logger.setMinLogLevel(LogLevel.WARN); // Change minimum level to WARN
        logger.info("This INFO message should NOT be shown (min level is WARN).");
        logger.warn("This WARNING message SHOULD be shown.");
        logger.error("This ERROR message SHOULD be shown.");


        // --- File Logging Example ---
        System.out.println("\n--- Starting File Logging Test ---");
        logger.setMinLogLevel(LogLevel.INFO); // Reset minimum level for file logging
        logger.setDestination(LogDestination.FILE); // Set destination to file
        logger.setLogFilePath("my_application.log"); // Specify a custom log file name

        logger.info("This is an INFO message to file.");
        logger.warn("This is a WARNING message to file.");
        logger.error("This is an ERROR message to file.");

        System.out.println("--- Changing File Log Level ---");
        logger.setMinLogLevel(LogLevel.ERROR); // Change minimum level to ERROR
        logger.info("This INFO message should NOT be shown in file (min level is ERROR).");
        logger.warn("This WARNING message should NOT be shown in file (min level is ERROR).");
        logger.error("This ERROR message SHOULD be shown in file.");

        System.out.println("\nFile logging complete. Please check 'my_application.log' for entries.");
    }
}