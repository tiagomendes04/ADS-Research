```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class EventLogger extends JFrame {
    private JLabel messageLabel;
    private JTextField filterTextField;
    private JButton filterButton;
    private Map<String, Boolean> filterMap = new HashMap<>();

    public EventLogger() {
        super("Event Logger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageLabel = new JLabel("Press 'Filter' to view filtered logs.");
        add(messageLabel, BorderLayout.NORTH);

        filterTextField = new JTextField(20);
        filterTextField.addActionListener(e -> {
            String filter = filterTextField.getText();
            filterButton.setEnabled(!filter.isEmpty());
        });
        add(filterTextField, BorderLayout.CENTER);

        filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            String filter = filterTextField.getText();
            filterMap.forEach((key, value) -> {
                if (filter.isEmpty() || filter.equalsIgnoreCase(key)) {
                    if (value) {
                        messageLabel.setText(messageLabel.getText() + "\n" + key + ": true");
                    } else {
                        messageLabel.setText(messageLabel.getText() + "\n" + key + ": false");
                    }
                }
            });
        });
        add(filterButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
```