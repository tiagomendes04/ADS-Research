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