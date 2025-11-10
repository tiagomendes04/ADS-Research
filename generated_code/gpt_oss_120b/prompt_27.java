```java
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/* ==================== Event Model ==================== */
enum LogLevel { DEBUG, INFO, WARN, ERROR }

final class LogEvent {
    private final LocalDateTime timestamp;
    private final LogLevel level;
    private final String source;
    private final String message;

    LogEvent(LogLevel level, String source, String message) {
        this.timestamp = LocalDateTime.now();
        this.level = level;
        this.source = source;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public LogLevel getLevel()       { return level; }
    public String getSource()        { return source; }
    public String getMessage()       { return message; }

    @Override
    public String toString() {
        return String.format("%s [%s] (%s): %s",
                timestamp.format(DateTimeFormatter.ISO_LOCAL_TIME),
                level,
                source,
                message);
    }
}

/* ==================== Filter API ==================== */
interface LogFilter {
    boolean accept(LogEvent event);
}

/* Example filter implementations */
final class LevelFilter implements LogFilter {
    private final LogLevel minLevel;

    LevelFilter(LogLevel minLevel) { this.minLevel = minLevel; }

    @Override
    public boolean accept(LogEvent e) {
        return e.getLevel().ordinal() >= minLevel.ordinal();
    }
}

final class SourceFilter implements LogFilter {
    private final String allowedSource;

    SourceFilter(String allowedSource) { this.allowedSource = allowedSource; }

    @Override
    public boolean accept(LogEvent e) {
        return e.getSource().equalsIgnoreCase(allowedSource);
    }
}

/* ==================== Logger ==================== */
final class EventLogger {
    private final List<LogFilter> filters = new CopyOnWriteArrayList<>();
    private final List<LogListener> listeners = new CopyOnWriteArrayList<>();

    /* Listener API */
    interface LogListener {
        void onLog(LogEvent event);
    }

    public void addFilter(LogFilter filter)   { filters.add(filter); }
    public void removeFilter(LogFilter filter){ filters.remove(filter); }
    public void clearFilters()                { filters.clear(); }

    public void addListener(LogListener l)    { listeners.add(l); }
    public void removeListener(LogListener l) { listeners.remove(l); }

    public void log(LogLevel level, String source, String message) {
        LogEvent event = new LogEvent(level, source, message);
        if (filters.stream().allMatch(f -> f.accept(event))) {
            listeners.forEach(l -> l.onLog(event));
        }
    }

    /* Convenience overloads */
    public void debug(String src, String msg) { log(LogLevel.DEBUG, src, msg); }
    public void info (String src, String msg) { log(LogLevel.INFO , src, msg); }
    public void warn (String src, String msg) { log(LogLevel.WARN , src, msg); }
    public void error(String src, String msg) { log(LogLevel.ERROR, src, msg); }
}

/* ==================== GUI Demo ==================== */
public class GuiLoggerDemo extends JFrame {
    private final JTextArea logArea = new JTextArea(15, 50);
    private final EventLogger logger = new EventLogger();

    public GuiLoggerDemo() {
        super("GUI Event Logger");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initLogger();
        initUI();
        pack();
        setLocationRelativeTo(null);
    }

    private void initLogger() {
        logger.addListener(event -> {
            EventQueue.invokeLater(() -> {
                logArea.append(event.toString() + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        });
        // Example: only INFO and above, ignore DEBUG
        logger.addFilter(new LevelFilter(LogLevel.INFO));
    }

    private void initUI() {
        JPanel panel = new JPanel();
        JButton btnInfo = new JButton("Info");
        JButton btnWarn = new JButton("Warn");
        JButton btnError = new JButton("Error");
        JButton btnDebug = new JButton("Debug (filtered)");

        ActionListener al = e -> {
            String src = "GuiLoggerDemo";
            switch (e.getActionCommand()) {
                case "Info":   logger.info(src, "Info button pressed"); break;
                case "Warn":   logger.warn(src, "Warning button pressed"); break;
                case "Error":  logger.error(src, "Error button pressed"); break;
                case "Debug (filtered)":
                    logger.debug(src, "Debug button pressed");
                    break;
            }
        };

        btnInfo.addActionListener(al);
        btnWarn.addActionListener(al);
        btnError.addActionListener(al);
        btnDebug.addActionListener(al);

        panel.add(btnInfo);
        panel.add(btnWarn);
        panel.add(btnError);
        panel.add(btnDebug);
        add(panel, "North");
        add(new JScrollPane(logArea), "Center");
        logArea.setEditable(false);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GuiLoggerDemo().setVisible(true));
    }
}
```