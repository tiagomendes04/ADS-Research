```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlantWateringScheduler {

    private List<Plant> plants = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();

    public static void main(String[] args) {
        PlantWateringScheduler scheduler = new PlantWateringScheduler();
        scheduler.addPlant("Tomato", LocalTime.of(8, 0), LocalTime.of(12, 0));
        scheduler.addPlant("Rose", LocalTime.of(9, 0), LocalTime.of(13, 0));
        scheduler.run();
    }

    public void addPlant(String name, LocalTime waterTime, LocalTime stopWateringTime) {
        plants.add(new Plant(name, waterTime, stopWateringTime));
    }

    public void run() {
        while (true) {
            LocalDateTime now = LocalDateTime.now();
            for (Plant plant : plants) {
                if (shouldWater(now, plant)) {
                    waterPlant(plant);
                }
            }
            try {
                Thread.sleep(60000); // Check every minute
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean shouldWater(LocalDateTime now, Plant plant) {
        LocalTime currentHour = now.toLocalTime();
        return currentHour.isAfter(plant.waterTime) && currentHour.isBefore(plant.stopWateringTime);
    }

    private void waterPlant(Plant plant) {
        System.out.println("Watering " + plant.name + " at " + LocalDateTime.now());
        notifications.add(new Notification(LocalDateTime.now(), "Watering " + plant.name));
    }

    public void showHistory() {
        for (Notification notification : notifications) {
            System.out.println(notification);
        }
    }

    private static class Plant {
        String name;
        LocalTime waterTime;
        LocalTime stopWateringTime;

        Plant(String name, LocalTime waterTime, LocalTime stopWateringTime) {
            this.name = name;
            this.waterTime = waterTime;
            this.stopWateringTime = stopWateringTime;
        }
    }

    private static class Notification {
        LocalDateTime time;
        String message;

        Notification(LocalDateTime time, String message) {
            this.time = time;
            this.message = message;
        }

        @Override
        public String toString() {
            return time + " - " + message;
        }
    }
}
```