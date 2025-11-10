```java
import java.util.HashMap;
import java.util.Map;

public class WeatherNotificationSystem {
    private Map<String, Integer> thresholds;
    private Map<String, Runnable> alerts;

    public WeatherNotificationSystem() {
        thresholds = new HashMap<>();
        alerts = new HashMap<>();
        thresholds.put("temperature", 30);
        thresholds.put("humidity", 80);
        thresholds.put("windSpeed", 50);

        alerts.put("temperature", () -> System.out.println("Temperature is too high!"));
        alerts.put("humidity", () -> System.out.println("Humidity is too high!"));
        alerts.put("windSpeed", () -> System.out.println("Wind speed is too high!"));
    }

    public void checkWeather(String metric, int value) {
        if (thresholds.containsKey(metric)) {
            int threshold = thresholds.get(metric);
            if (value > threshold) {
                alerts.get(metric).run();
            }
        }
    }

    public static void main(String[] args) {
        WeatherNotificationSystem system = new WeatherNotificationSystem();
        system.checkWeather("temperature", 35); // Should trigger alert
        system.checkWeather("humidity", 78); // Should not trigger alert
        system.checkWeather("windSpeed", 60); // Should trigger alert
    }
}
```