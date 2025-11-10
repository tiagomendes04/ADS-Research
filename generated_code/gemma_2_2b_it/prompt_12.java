```java
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WeatherApp {

    public static void main(String[] args) {

        // Replace with actual API keys
        String apiKey = "YOUR_API_KEY";
        String city = "London";

        // Fetch data from OpenWeatherMap
        String openWeatherMapData = fetchWeatherData(apiKey, city);
        
        // Fetch data from OpenWeatherMap
        String weatherData = fetchWeatherData(apiKey, city);

        // Print data
        System.out.println(weatherData);
    }

    private static String fetchWeatherData(String apiKey, String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        try {
            URL oracle = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) oracle.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
```