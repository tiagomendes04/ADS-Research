```java
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PlantWateringScheduler {
    private List<Plant> plants;
    private List<Notification> notifications;
    private List<History> history;

    public PlantWateringScheduler() {
        plants = new ArrayList<>();
        notifications = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void addPlant(String name, LocalDate nextWatering) {
        plants.add(new Plant(name, nextWatering));
        notifyNextWatering(name);
    }

    public void waterPlant(String plantName) {
        for (Plant plant : plants) {
            if (plant.getName().equals(plantName)) {
                plant.setLastWatered(LocalDate.now());
                plant.setNextWatering(calculateNextWatering(plant.getDaysBetweenWaterings()));
                notifyNextWatering(plantName);
                history.add(new History(plantName, LocalDate.now(), "Watered"));
                break;
            }
        }
    }

    private void notifyNextWatering(String plantName) {
        for (Plant plant : plants) {
            if (plant.getName().equals(plantName)) {
                if (plant.getNextWatering().isBefore(LocalDate.now().plusDays(7))) {
                    JOptionPane.showMessageDialog(null, "Reminder: Water " + plant.getName() + " on " + plant.getNextWatering());
                }
                break;
            }
        }
    }

    private LocalDate calculateNextWatering(int daysBetweenWaterings) {
        return LocalDate.now().plus(daysBetweenWaterings, ChronoUnit.DAYS);
    }

    public void viewHistory() {
        StringBuilder historyString = new StringBuilder("Plant Watering History:\n");
        for (History entry : history) {
            historyString.append(entry.getPlantName()).append(": ").append(entry.getWateringDate()).append(" - ").append(entry.getEvent()).append("\n");
        }
        JOptionPane.showMessageDialog(null, historyString.toString());
    }

    private static class Plant {
        private String name;
        private LocalDate nextWatering;
        private LocalDate lastWatered;
        private int daysBetweenWaterings;

        public Plant(String name, LocalDate nextWatering) {
            this.name = name;
            this.nextWatering = nextWatering;
            this.daysBetweenWaterings = 7; // default days between waterings
        }

        public String getName() {
            return name;
        }

        public LocalDate getNextWatering() {
            return nextWatering;
        }

        public LocalDate getLastWatered() {
            return lastWatered;
        }

        public void setNextWatering(LocalDate nextWatering) {
            this.nextWatering = nextWatering;
        }

        public void setLastWatered(LocalDate lastWatered) {
            this.lastWatered = lastWatered;
        }

        public int getDaysBetweenWaterings() {
            return daysBetweenWaterings;
        }
    }

    private static class Notification {
        private String plantName;
        private LocalDate nextWatering;

        public Notification(String plantName, LocalDate nextWatering) {
            this.plantName = plantName;
            this.nextWatering = nextWatering;
        }
    }

    private static class History {
        private String plantName;
        private LocalDate wateringDate;
        private String event;

        public History(String plantName, LocalDate wateringDate, String event) {
            this.plantName = plantName;
            this.wateringDate = wateringDate;
            this.event = event;
        }

        public String getPlantName() {
            return plantName;
        }

        public LocalDate getWateringDate() {
            return wateringDate;
        }

        public String getEvent() {
            return event;
        }
    }

    public static void main(String[] args) {
        PlantWateringScheduler scheduler = new PlantWateringScheduler();
        scheduler.addPlant("Snake Plant", LocalDate.of(2024, 7, 1));
        scheduler.waterPlant("Snake Plant");
        scheduler.viewHistory();
    }
}
```

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class PlantWateringSchedulerGUI {
    private JFrame frame;
    private JTextField plantNameField;
    private JTextField nextWateringField;
    private JTextArea historyArea;
    private PlantWateringScheduler scheduler;

    public PlantWateringSchedulerGUI() {
        scheduler = new PlantWateringScheduler();
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Plant Watering Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        panel.add(new JLabel("Plant Name:"));
        plantNameField = new JTextField();
        panel.add(plantNameField);

        panel.add(new JLabel("Next Watering (yyyy-MM-dd):"));
        nextWateringField = new JTextField();
        panel.add(nextWateringField);

        JButton addButton = new JButton("Add Plant");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String plantName = plantNameField.getText();
                String nextWateringStr = nextWateringField.getText();
                LocalDate nextWatering = LocalDate.parse(nextWateringStr);
                scheduler.addPlant(plantName, nextWatering);
            }
        });
        panel.add(addButton);

        JButton waterButton = new JButton("Water Plant");
        waterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String plantName = plantNameField.getText();
                scheduler.waterPlant(plantName);
            }
        });
        panel.add(waterButton);

        JButton historyButton = new JButton("View History");
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduler.viewHistory();
                historyArea.setText(scheduler.getHistoryString());
            }
        });
        panel.add(historyButton);

        historyArea = new JTextArea(10, 20);
        panel.add(new JLabel("History:"));
        panel.add(new JScrollPane(historyArea));

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private String getHistoryString() {
        StringBuilder historyString = new StringBuilder("Plant Watering History:\n");
        for (History entry : scheduler.getHistory()) {
            historyString.append(entry.getPlantName()).append(": ").append(entry.getWateringDate()).append(" - ").append(entry.getEvent()).append("\n");
        }
        return historyString.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PlantWateringSchedulerGUI();
            }
        });
    }
}