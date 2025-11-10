```java
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Plant Watering Scheduler
 * - Add plants with watering interval (days)
 * - Scheduler runs in background and prints notifications when watering is due
 * - History of watering events is saved to "history.json"
 * - Simple console menu for interaction
 */
public class PlantWateringScheduler {

    // ---------- Data Models ----------
    static class Plant {
        String id;
        String name;
        int intervalDays; // watering interval
        LocalDate lastWatered; // date of last watering

        Plant(String id, String name, int intervalDays, LocalDate lastWatered) {
            this.id = id;
            this.name = name;
            this.intervalDays = intervalDays;
            this.lastWatered = lastWatered;
        }

        LocalDate nextWateringDate() {
            return lastWatered.plusDays(intervalDays);
        }
    }

    static class WateringEvent {
        String plantId;
        String plantName;
        LocalDate date;

        WateringEvent(String plantId, String plantName, LocalDate date) {
            this.plantId = plantId;
            this.plantName = plantName;
            this.date = date;
        }
    }

    // ---------- Persistence ----------
    static class HistoryStore {
        private static final Path FILE = Paths.get("history.json");
        private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<WateringEvent> load() {
            if (!Files.exists(FILE)) return new ArrayList<>();
            try (Reader r = Files.newBufferedReader(FILE)) {
                return gson.fromJson(r, new TypeToken<List<WateringEvent>>() {}.getType());
            } catch (IOException e) {
                System.err.println("Failed to load history: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        void save(List<WateringEvent> events) {
            try (Writer w = Files.newBufferedWriter(FILE)) {
                gson.toJson(events, w);
            } catch (IOException e) {
                System.err.println("Failed to save history: " + e.getMessage());
            }
        }
    }

    // ---------- Scheduler ----------
    static class Scheduler {
        private final Map<String, Plant> plants = new ConcurrentHashMap<>();
        private final List<WateringEvent> history = new CopyOnWriteArrayList<>();
        private final HistoryStore store = new HistoryStore();
        private final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

        Scheduler() {
            history.addAll(store.load());
            exec.scheduleAtFixedRate(this::checkDuePlants, 0, 1, TimeUnit.MINUTES);
        }

        void shutdown() {
            exec.shutdownNow();
            store.save(history);
        }

        void addPlant(String name, int intervalDays) {
            String id = UUID.randomUUID().toString();
            Plant p = new Plant(id, name, intervalDays, LocalDate.now());
            plants.put(id, p);
            System.out.println("Plant added with ID: " + id);
        }

        void listPlants() {
            if (plants.isEmpty()) {
                System.out.println("No plants registered.");
                return;
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("\nRegistered Plants:");
            plants.values().forEach(p -> {
                System.out.printf("ID: %s | Name: %s | Interval: %d days | Last Watered: %s | Next: %s%n",
                        p.id, p.name, p.intervalDays,
                        p.lastWatered.format(fmt),
                        p.nextWateringDate().format(fmt));
            });
        }

        void recordWatering(String plantId) {
            Plant p = plants.get(plantId);
            if (p == null) {
                System.out.println("Plant not found.");
                return;
            }
            p.lastWatered = LocalDate.now();
            WateringEvent ev = new WateringEvent(p.id, p.name, p.lastWatered);
            history.add(ev);
            System.out.println("Watering recorded for " + p.name + " on " + p.lastWatered);
        }

        void showHistory() {
            if (history.isEmpty()) {
                System.out.println("No watering history.");
                return;
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("\nWatering History:");
            history.forEach(ev -> System.out.printf("%s | %s | %s%n",
                    ev.date.format(fmt), ev.plantId, ev.plantName));
        }

        private void checkDuePlants() {
            LocalDate today = LocalDate.now();
            plants.values().forEach(p -> {
                if (!p.lastWatered.isAfter(today.minusDays(p.intervalDays))) {
                    if (p.nextWateringDate().isBefore(today) || p.nextWateringDate().isEqual(today)) {
                        System.out.println("\n[NOTIFICATION] Plant \"" + p.name + "\" (ID: " + p.id + ") needs watering today!");
                    }
                }
            });
        }
    }

    // ---------- Main UI ----------
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        Scanner scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(scheduler::shutdown));

        while (true) {
            System.out.println("\n--- Plant Watering Scheduler ---");
            System.out.println("1) Add Plant");
            System.out.println("2) List Plants");
            System.out.println("3) Record Watering");
            System.out.println("4) View History");
            System.out.println("5) Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Plant name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Watering interval (days): ");
                    int interval;
                    try {
                        interval = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                        break;
                    }
                    scheduler.addPlant(name, interval);
                    break;
                case "2":
                    scheduler.listPlants();
                    break;
                case "3":
                    System.out.print("Enter Plant ID to record watering: ");
                    String id = scanner.nextLine().trim();
                    scheduler.recordWatering(id);
                    break;
                case "4":
                    scheduler.showHistory();
                    break;
                case "5":
                    System.out.println("Exiting...");
                    scheduler.shutdown();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
```