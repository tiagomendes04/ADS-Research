```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "your_api_key_here";

    public static void main(String[] args) {
        fetchWeatherData("London");
        fetchWeatherData("New York");
        fetchWeatherData("Tokyo");
    }

    private static void fetchWeatherData(String city) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject weatherData = new JSONObject(response.toString());
            System.out.println("Weather in " + city + ":");
            System.out.println("Temperature: " + weatherData.getJSONObject("main").getString("temp") + "°C");
            System.out.println("Humidity: " + weatherData.getJSONObject("main").getString("humidity") + "%");
            System.out.println("Description: " + weatherData.getJSONArray("weather").getJSONObject(0).getString("description"));
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```