// Example usage
public class Main {
    public static void main(String[] args) {
        EventLogger.addFilter(new EventLogger.Filter() {
            @Override
            public boolean accept(LogRecord record) {
                return record.getLevel().intValue() >= Level.INFO.intValue();
            }
        });

        EventLogger.info("Info message");
        EventLogger.warn("Warning message");
        EventLogger.error("Error message");
    }
}