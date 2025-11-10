```java
// WeatherNotificationSystem.java
import java.util.*;
import java.util.concurrent.*;
import java.time.*;

public class WeatherNotificationSystem {
    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService();
        NotificationService notificationService = new NotificationService();
        AlertManager alertManager = new AlertManager(notificationService);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            WeatherData data = weatherService.fetchCurrentWeather();
            alertManager.evaluate(data);
        }, 0, 5, TimeUnit.MINUTES);
    }
}

// WeatherData.java
class WeatherData {
    private final double temperatureCelsius;
    private final double humidityPercent;
    private final double windSpeedKmh;
    private final LocalDateTime timestamp;

    public WeatherData(double temperatureCelsius, double humidityPercent, double windSpeedKmh, LocalDateTime timestamp) {
        this.temperatureCelsius = temperatureCelsius;
        this.humidityPercent = humidityPercent;
        this.windSpeedKmh = windSpeedKmh;
        this.timestamp = timestamp;
    }

    public double getTemperatureCelsius() { return temperatureCelsius; }
    public double getHumidityPercent() { return humidityPercent; }
    public double getWindSpeedKmh() { return windSpeedKmh; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

// WeatherService.java
class WeatherService {
    private final Random random = new Random();

    public WeatherData fetchCurrentWeather() {
        double temp = -10 + random.nextDouble() * 45; // -10°C to 35°C
        double humidity = 10 + random.nextDouble() * 90; // 10% to 100%
        double wind = random.nextDouble() * 100; // 0 to 100 km/h
        return new WeatherData(temp, humidity, wind, LocalDateTime.now());
    }
}

// NotificationService.java
class NotificationService {
    public void sendAlert(String message) {
        // In a real system this could be email, SMS, push notification, etc.
        System.out.println("[ALERT] " + LocalDateTime.now() + " - " + message);
    }
}

// AlertManager.java
class AlertManager {
    private final NotificationService notifier;
    private final Map<String, Boolean> lastAlertState = new HashMap<>();

    // Thresholds (could be loaded from config)
    private static final double TEMP_HIGH = 30.0; // °C
    private static final double TEMP_LOW = 0.0;   // °C
    private static final double HUMIDITY_HIGH = 80.0; // %
    private static final double WIND_SPEED_HIGH = 70.0; // km/h

    public AlertManager(NotificationService notifier) {
        this.notifier = notifier;
    }

    public void evaluate(WeatherData data) {
        checkThreshold("TEMP_HIGH", data.getTemperatureCelsius() > TEMP_HIGH,
                () -> "High temperature alert: " + data.getTemperatureCelsius() + "°C");
        checkThreshold("TEMP_LOW", data.getTemperatureCelsius() < TEMP_LOW,
                () -> "Low temperature alert: " + data.getTemperatureCelsius() + "°C");
        checkThreshold("HUMIDITY_HIGH", data.getHumidityPercent() > HUMIDITY_HIGH,
                () -> "High humidity alert: " + data.getHumidityPercent() + "%");
        checkThreshold("WIND_SPEED_HIGH", data.getWindSpeedKmh() > WIND_SPEED_HIGH,
                () -> "High wind speed alert: " + data.getWindSpeedKmh() + " km/h");
    }

    private void checkThreshold(String key, boolean condition, Supplier<String> messageSupplier) {
        boolean previouslyAlerted = lastAlertState.getOrDefault(key, false);
        if (condition && !previouslyAlerted) {
            notifier.sendAlert(messageSupplier.get());
            lastAlertState.put(key, true);
        } else if (!condition && previouslyAlerted) {
            // Reset state when condition no longer true
            lastAlertState.put(key, false);
        }
    }
}
```