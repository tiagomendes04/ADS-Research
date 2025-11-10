```java
import java.util.ArrayList;
import java.util.List;

class WeatherCondition {
    private String condition;
    private double temperatureThreshold;

    public WeatherCondition(String condition, double temperatureThreshold) {
        this.condition = condition;
        this.temperatureThreshold = temperatureThreshold;
    }

    public String getCondition() {
        return condition;
    }

    public double getTemperatureThreshold() {
        return temperatureThreshold;
    }
}

class WeatherAlert {
    private String message;

    public WeatherAlert(String message) {
        this.message = message;
    }

    public void sendMessage() {
        System.out.println(message);
    }
}

class WeatherStation {
    private List<WeatherCondition> conditions;
    private List<WeatherAlert> alerts;

    public WeatherStation() {
        this.conditions = new ArrayList<>();
        this.alerts = new ArrayList<>();
    }

    public void addCondition(WeatherCondition condition) {
        conditions.add(condition);
    }

    public void addAlert(WeatherAlert alert) {
        alerts.add(alert);
    }

    public void checkWeather(double currentTemperature, String currentCondition) {
        for (WeatherCondition condition : conditions) {
            if (currentCondition.equals(condition.getCondition()) &&
                currentTemperature > condition.getTemperatureThreshold()) {
                for (WeatherAlert alert : alerts) {
                    alert.sendMessage();
                }
            }
        }
    }
}

public class WeatherNotificationSystem {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        station.addCondition(new WeatherCondition("Sunny", 30.0));
        station.addCondition(new WeatherCondition("Rainy", 25.0));
        station.addAlert(new WeatherAlert("High Temperature Alert!"));
        station.addAlert(new WeatherAlert("Stay Indoors during Rain!"));

        station.checkWeather(32.0, "Sunny");
        station.checkWeather(26.0, "Rainy");
    }
}
```