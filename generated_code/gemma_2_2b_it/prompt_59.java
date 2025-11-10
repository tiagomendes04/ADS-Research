```java
import java.util.Scanner;
import java.util.Random;

public class WeatherNotification {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your location: ");
        String location = scanner.nextLine();

        Random random = new Random();
        double temperature = random.nextDouble() * 30 + 10; // Generate random temperature between 10 and 40 degrees
        double humidity = random.nextDouble() * 100; // Generate random humidity between 0 and 100%
        double windSpeed = random.nextDouble() * 25 + 5; // Generate random wind speed between 5 and 25 km/h

        if (temperature < 10 || temperature > 40) {
            System.out.println("Warning: Temperature out of range. Check weather conditions!");
        }

        if (humidity < 30 || humidity > 90) {
            System.out.println("Warning: Humidity out of range. Check weather conditions!");
        }

        if (windSpeed < 5 || windSpeed > 25) {
            System.out.println("Warning: Wind speed out of range. Check weather conditions!");
        }

        // Add code for notification logic based on thresholds
    }
}
```