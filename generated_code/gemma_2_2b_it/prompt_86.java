```java
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class PlantWateringScheduler {

    private String plantName;
    private String wateringTime;
    private boolean wateringReminder;
    private String notificationMessage;
    private String history;
    
    public PlantWateringScheduler(String plantName, String wateringTime, boolean wateringReminder, String notificationMessage) {
        this.plantName = plantName;
        this.wateringTime = wateringTime;
        this.wateringReminder = wateringReminder;
        this.notificationMessage = notificationMessage;
        this.history = "";
    }

    public void scheduleWatering() {
        Calendar calendar = new GregorianCalendar();
        
        if (wateringReminder) {
            if (calendar.get(Calendar.MINUTE) % 10 == 0) {
                System.out.println("Watering reminder: " + notificationMessage);
            }
        }
        
        history += "Watering scheduled for " + wateringTime + " on " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + "\n";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter plant name: ");
        String plantName = scanner.nextLine();
        
        System.out.print("Enter watering time (HH:mm): ");
        String wateringTime = scanner.nextLine();
        
        System.out.print("Enable watering reminder (yes/no): ");
        String wateringReminder = scanner.nextLine();
        
        System.out.print("Enter notification message: ");
        String notificationMessage = scanner.nextLine();
        
        PlantWateringScheduler scheduler = new PlantWateringScheduler(plantName, wateringTime, Boolean.parseBoolean(wateringReminder), notificationMessage);
        scheduler.scheduleWatering();
        System.out.println("History: \n" + scheduler.history);
    }
}
```