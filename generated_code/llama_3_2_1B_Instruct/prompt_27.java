```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EventLogger {
    private static final Logger logger = Logger.getLogger(EventLogger.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Logger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Console Handler
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(consoleHandler, BorderLayout.PAGE_END);

        // Event Filter
        EventFilter[] eventFilters = new EventFilter[]{new EventFilter("info", Level.INFO), new EventFilter("debug", Level.SEVERE)};
        Handler[] eventHandlers = new Handler[]{consoleHandler};

        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
        for (EventFilter filter : eventFilters) {
            eventPanel.add(new JLabel(filter.getFilterName() + " - " + filter.getLevel()));
        }
        frame.add(eventPanel, BorderLayout.SOUTH);

        // Event Filter Listeners
        for (Handler handler : eventHandlers) {
            handler.setLevel(Level.INFO);
            handler.setFormatter(new SimpleFormatter());
            frame.add(handler, BorderLayout.PAGE_START);
        }

        frame.pack();
        frame.setVisible(true);
    }

    private static class EventFilter implements Filter {
        private final String filterName;
        private final Level level;

        public EventFilter(String filterName, Level level) {
            this.filterName = filterName;
            this.level = level;
        }

        @Override
        public int[] getMinDisplacement() {
            return new int[]{0, 0, 0};
        }

        @Override
        public int getEventMask() {
            return 0x00000000; // Don't filter events
        }

        @Override
        public void processEvent(Event event) {
            if (event.getEventType().equals(EventType.INFO) || event.getEventType().equals(EventType.DEBUG)) {
                logger.log(level, event);
            }
        }

        @Override
        public void removeFilter() {
            logger.removeFilter(filterName);
        }

        @Override
        public void addFilter() {
            logger.addFilter(filterName, this);
        }
    }
}
```