```java
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Plant {
    private String name;
    private LocalDateTime lastWatered;
    private int wateringFrequencyDays;

    public Plant(String name, int wateringFrequencyDays) {
        this.name = name;
        this.wateringFrequencyDays = wateringFrequencyDays;
        this.lastWatered = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(LocalDateTime lastWatered) {
        this.lastWatered = lastWatered;
    }

    public int getWateringFrequencyDays() {
        return wateringFrequencyDays;
    }
}

class WateringScheduler {
    private List<Plant> plants;
    private List<String> history;

    public WateringScheduler() {
        plants = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void removePlant(String plantName) {
        plants.removeIf(plant -> plant.getName().equals(plantName));
    }

    public void checkWatering() {
        LocalDateTime now = LocalDateTime.now();
        for (Plant plant : plants) {
            if (now.minusDays(plant.getWateringFrequencyDays()).isAfter(plant.getLastWatered())) {
                notifyUser(plant);
                plant.setLastWatered(now);
            }
        }
    }

    private void notifyUser(Plant plant) {
        String notification = "It's time to water " + plant.getName() + "!";
        System.out.println(notification);
        history.add(notification + " - " + LocalDateTime.now());
    }

    public List<String> getHistory() {
        return history;
    }
}

public class Main {
    public static void main(String[] args) {
        WateringScheduler scheduler = new WateringScheduler();
        scheduler.addPlant(new Plant("Fern", 3));
        scheduler.addPlant(new Plant("Cactus", 10));

        // Simulate checking watering every day for a week
        for (int i = 0; i < 7; i++) {
            scheduler.checkWatering();
            try {
                Thread.sleep(24 * 60 * 60 * 1000); // Sleep for one day
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print history
        for (String entry : scheduler.getHistory()) {
            System.out.println(entry);
        }
    }
}
```