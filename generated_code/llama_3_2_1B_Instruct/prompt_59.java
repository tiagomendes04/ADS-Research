```java
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

class WeatherNotification {
    private String city;
    private String country;
    private int thresholdTemperature;
    private int thresholdHumidity;
    private int thresholdWindSpeed;
    private int thresholdPressure;
    private double thresholdTemperatureAlertThreshold;
    private double thresholdHumidityAlertThreshold;
    private double thresholdWindSpeedAlertThreshold;
    private double thresholdPressureAlertThreshold;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public WeatherNotification(String city, String country, int thresholdTemperature, int thresholdHumidity, int thresholdWindSpeed, int thresholdPressure) {
        this.city = city;
        this.country = country;
        this.thresholdTemperature = thresholdTemperature;
        this.thresholdHumidity = thresholdHumidity;
        this.thresholdWindSpeed = thresholdWindSpeed;
        this.thresholdPressure = thresholdPressure;
        this.thresholdTemperatureAlertThreshold = 10;
        this.thresholdHumidityAlertThreshold = 60;
        this.thresholdWindSpeedAlertThreshold = 50;
        this.thresholdPressureAlertThreshold = 800;
    }

    public void checkThresholds() {
        if (getThresholdTemperature() > thresholdTemperature) {
            sendAlert("Temperature alert: " + city + " is expected to be above " + thresholdTemperature + " degrees");
        } else if (getThresholdHumidity() > thresholdHumidity) {
            sendAlert("Humidity alert: " + city + " is expected to be above " + thresholdHumidity + "%");
        } else if (getThresholdWindSpeed() > thresholdWindSpeed) {
            sendAlert("Wind speed alert: " + city + " is expected to be above " + thresholdWindSpeed + " km/h");
        } else if (getThresholdPressure() > thresholdPressure) {
            sendAlert("Pressure alert: " + city + " is expected to be above " + thresholdPressure + " hPa");
        } else {
            sendNoAlert();
        }
    }

    private int getThresholdTemperature() {
        int threshold = thresholdTemperatureAlertThreshold;
        if (city != null && city.length() > 0) {
            try {
                return Integer.parseInt(city + ", " + country + ", " + threshold);
            } catch (NumberFormatException e) {
                return Integer.parseInt(country + ", " + threshold);
            }
        } else if (country != null && country.length() > 0) {
            try {
                return Integer.parseInt(country + ", " + threshold);
            } catch (NumberFormatException e) {
                return threshold;
            }
        } else {
            return threshold;
        }
    }

    private int getThresholdHumidity() {
        int threshold = thresholdHumidityAlertThreshold;
        if (city != null && city.length() > 0) {
            try {
                return Integer.parseInt(city + ", " + country + ", " + threshold);
            } catch (NumberFormatException e) {
                return Integer.parseInt(country + ", " + threshold);
            }
        } else if (country != null && country.length() > 0) {
            try {
                return Integer.parseInt(country + ", " + threshold);
            } catch (NumberFormatException e) {
                return threshold;
            }
        } else {
            return threshold;
        }
    }

    private int getThresholdWindSpeed() {
        int threshold = thresholdWindSpeedAlertThreshold;
        if (city != null && city.length() > 0) {
            try {
                return Integer.parseInt(city + ", " + country + ", " + threshold);
            } catch (NumberFormatException e) {
                return Integer.parseInt(country + ", " + threshold);
            }
        } else if (country != null && country.length() > 0) {
            try {
                return Integer.parseInt(country + ", " + threshold);
            } catch (NumberFormatException e) {
                return threshold;
            }
        } else {
            return threshold;
        }
    }

    private int getThresholdPressure() {
        int threshold = thresholdPressureAlertThreshold;
        if (city != null && city.length() > 0) {
            try {
                return Integer.parseInt(city + ", " + country + ", " + threshold);
            } catch (NumberFormatException e) {
                return Integer.parseInt(country + ", " + threshold);
            }
        } else if (country != null && country.length() > 0) {
            try {
                return Integer.parseInt(country + ", " + threshold);
            } catch (NumberFormatException e) {
                return threshold;
            }
        } else {
            return threshold;
        }
    }

    private void sendAlert(String message) {
        System.out.println("Sending alert: " + message);
    }

    private void sendNoAlert() {
        System.out.println("No alert sent.");
    }
}

public class WeatherNotificationSystem {
    public static WeatherNotificationSystem init() {
        return new WeatherNotificationSystem();
    }

    public static void main(String[] args) {
        WeatherNotificationSystem system = init();
        system.checkThresholds();
    }
}
```