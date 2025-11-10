```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class WeatherNotificationSystem {

    private Map<String, Double> thresholds;
    private Map<String, Double> currentConditions;
    private Map<String, String> alerts;

    public WeatherNotificationSystem() {
        thresholds = new HashMap<>();
        thresholds.put("temperature", 30.0);
        thresholds.put("humidity", 80.0);
        thresholds.put("windSpeed", 50.0);

        currentConditions = new HashMap<>();
        alerts = new HashMap<>();
    }

    public void updateConditions(String condition, double value) {
        currentConditions.put(condition, value);
        checkAlerts();
    }

    private void checkAlerts() {
        for (String condition : thresholds.keySet()) {
            if (currentConditions.get(condition) >= thresholds.get(condition)) {
                if (!alerts.containsKey(condition)) {
                    alerts.put(condition, "ALERT: " + condition + " exceeds threshold of " + thresholds.get(condition));
                }
            } else {
                if (alerts.containsKey(condition)) {
                    alerts.remove(condition);
                }
            }
        }
    }

    public void printAlerts() {
        System.out.println("ALERTS:");
        for (String alert : alerts.values()) {
            System.out.println(alert);
        }
    }

    public static void main(String[] args) {
        WeatherNotificationSystem wns = new WeatherNotificationSystem();

        wns.updateConditions("temperature", 35.0);
        wns.updateConditions("humidity", 90.0);
        wns.updateConditions("windSpeed", 60.0);
        wns.updateConditions("temperature", 25.0);
        wns.updateConditions("humidity", 70.0);
        wns.updateConditions("windSpeed", 40.0);

        wns.printAlerts();
    }
}
```

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherData {

    private double temperature;
    private double humidity;
    private double windSpeed;
    private LocalDateTime timestamp;

    public WeatherData(double temperature, double humidity, double windSpeed) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.timestamp = LocalDateTime.now();
    }

    public void updateTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void updateHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void updateWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void printData() {
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Wind Speed: " + windSpeed + " km/h");
        System.out.println("Timestamp: " + timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public static void main(String[] args) {
        WeatherData wd = new WeatherData(30.0, 60.0, 30.0);
        wd.updateTemperature(35.0);
        wd.updateHumidity(80.0);
        wd.updateWindSpeed(50.0);
        wd.printData();
    }
}
```