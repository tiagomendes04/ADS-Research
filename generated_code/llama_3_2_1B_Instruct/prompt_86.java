```java
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Plant {
    private String name;
    private int waterLevel;
    private int wateringInterval;
    private List<Map<String, Object>> history;

    public Plant(String name, int wateringInterval) {
        this.name = name;
        this.waterLevel = 10;
        this.wateringInterval = wateringInterval;
        this.history = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void updateWaterLevel(int newLevel) {
        waterLevel = newLevel;
    }

    public List<Map<String, Object>> getHistory() {
        return history;
    }

    public void addNotification(Map<String, Object> notification) {
        history.add(notification);
    }

    public void notifyWatering() {
        System.out.println("Plants: " + name + " watering now.");
        System.out.println("Water level: " + waterLevel + "%");
        System.out.println("History:");
        for (Map<String, Object> notification : history) {
            System.out.println(notification);
        }
    }

    public void waterPlant() {
        updateWaterLevel(waterLevel + 1);
        notifyWatering();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Plant plant = new Plant("Flower", 3);
        Map<String, Object> notification = new HashMap<>();
        notification.put("watering_date", new Date());
        notification.put("water_level", plant.getWaterLevel());
        plant.addNotification(notification);

        while (true) {
            System.out.println("1. Water plant");
            System.out.println("2. Check watering history");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    plant.waterPlant();
                    break;
                case 2:
                    plant.notifyWatering();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
}
```