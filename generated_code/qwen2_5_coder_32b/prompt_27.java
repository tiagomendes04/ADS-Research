```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class EventLogger {
    private List<String> logs = new ArrayList<>();
    private List<String> filteredLogs = new ArrayList<>();
    private String filter = "";

    public void logEvent(String event) {
        logs.add(event);
        updateFilteredLogs();
    }

    public void setFilter(String filter) {
        this.filter = filter.toLowerCase();
        updateFilteredLogs();
    }

    public List<String> getFilteredLogs() {
        return filteredLogs;
    }

    private void updateFilteredLogs() {
        filteredLogs.clear();
        for (String log : logs) {
            if (log.toLowerCase().contains(filter)) {
                filteredLogs.add(log);
            }
        }
    }
}

class LogPanel extends JPanel {
    private JTextArea logTextArea = new JTextArea();
    private JTextField filterField = new JTextField();
    private JButton applyFilterButton = new JButton("Apply Filter");
    private EventLogger eventLogger = new EventLogger();

    public LogPanel() {
        setLayout(new BorderLayout());
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel("Filter:"));
        filterPanel.add(filterField);
        filterPanel.add(applyFilterButton);
        add(filterPanel, BorderLayout.SOUTH);

        applyFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventLogger.setFilter(filterField.getText());
                updateLogDisplay();
            }
        });
    }

    public void logEvent(String event) {
        eventLogger.logEvent(event);
        updateLogDisplay();
    }

    private void updateLogDisplay() {
        StringBuilder sb = new StringBuilder();
        for (String log : eventLogger.getFilteredLogs()) {
            sb.append(log).append("\n");
        }
        logTextArea.setText(sb.toString());
    }
}

public class GUIEventLogger extends JFrame {
    private LogPanel logPanel = new LogPanel();

    public GUIEventLogger() {
        setTitle("GUI Event Logger");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(logPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIEventLogger logger = new GUIEventLogger();
            logger.logPanel.logEvent("Application started");
            logger.logPanel.logEvent("Button clicked");
            logger.logPanel.logEvent("Error occurred: File not found");
            logger.logPanel.logEvent("User logged in");
            logger.logPanel.logEvent("User logged out");
        });
    }
}
```