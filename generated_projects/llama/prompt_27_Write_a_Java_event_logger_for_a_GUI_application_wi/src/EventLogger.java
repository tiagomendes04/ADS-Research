import java.util.ArrayList;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class EventLogger {

    private static final Logger logger = Logger.getLogger("EventLogger");
    private static final List<Filter> filters = new ArrayList<>();

    static {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                for (Filter filter : filters) {
                    if (!filter.accept(record)) {
                        return;
                    }
                }
                Formatter formatter = new Formatter() {
                    @Override
                    public String format(LogRecord record) {
                        return String.format("%1$tF %1$tT - %2$s - %3$s - %4$s%n",
                                record.getMillis(),
                                record.getLoggerName(),
                                record.getLevel().getName(),
                                record.getMessage());
                    }
                };
                System.out.println(formatter.format(record));
            }

            @Override
            public void close() throws SecurityException {
            }
        };
        logger.addHandler(handler);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warning(message);
    }

    public static void error(String message) {
        logger.severe(message);
    }

    public static void addFilter(Filter filter) {
        filters.add(filter);
    }

    public interface Filter {
        boolean accept(LogRecord record);
    }
}