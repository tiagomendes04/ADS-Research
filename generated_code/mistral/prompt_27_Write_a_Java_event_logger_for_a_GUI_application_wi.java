```java
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventLoggerGUI extends JFrame {
    private JTextArea logArea;
    private JTextField filterField;
    private JList<String> eventList;
    private DefaultListModel<String> eventModel;
    private List<String> allEvents;
    private DateTimeFormatter formatter;

    public EventLoggerGUI() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        allEvents = new ArrayList<>();
        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);

        setTitle("Event Logger");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logArea);

        filterField = new JTextField();
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterEvents(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterEvents(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterEvents(); }
        });

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Filter:"), BorderLayout.WEST);
        filterPanel.add(filterField, BorderLayout.CENTER);

        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = eventList.getSelectedValue();
                if (selected != null) {
                    logArea.setText(selected);
                }
            }
        });

        JScrollPane eventScroll = new JScrollPane(eventList);

        add(logScroll, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.NORTH);
        add(eventScroll, BorderLayout.EAST);

        // Add sample events
        logEvent("Application started");
        logEvent("User logged in");
        logEvent("Configuration loaded");
        logEvent("Data processing started");
    }

    public void logEvent(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String event = String.format("[%s] %s", timestamp, message);
        allEvents.add(event);
        eventModel.addElement(event);
        logArea.setText(event);
    }

    private void filterEvents() {
        String filter = filterField.getText().toLowerCase();
        eventModel.clear();
        for (String event : allEvents) {
            if (event.toLowerCase().contains(filter)) {
                eventModel.addElement(event);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EventLoggerGUI gui = new EventLoggerGUI();
            gui.setVisible(true);
        });
    }
}
```