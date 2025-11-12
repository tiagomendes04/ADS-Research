```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "your_api_key_here";
    private static final String WEATHER_URL = "http://api.weatherapi.com/v1/current.json?key=%s&q=%s";
    private static final String FORECAST_URL = "http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=3";

    public static void main(String[] args) {
        try {
            String location = "London";
            JSONObject currentWeather = fetchWeatherData(WEATHER_URL, location);
            JSONObject forecastWeather = fetchWeatherData(FORECAST_URL, location);

            displayCurrentWeather(currentWeather);
            displayForecastWeather(forecastWeather);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject fetchWeatherData(String urlTemplate, String location) throws Exception {
        URL url = new URL(String.format(urlTemplate, API_KEY, location));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        return new JSONObject(content.toString());
    }

    private static void displayCurrentWeather(JSONObject weatherData) {
        JSONObject current = weatherData.getJSONObject("current");
        System.out.println("Current Weather:");
        System.out.println("Temperature: " + current.getDouble("temp_c") + "°C");
        System.out.println("Condition: " + current.getJSONObject("condition").getString("text"));
        System.out.println("Wind Speed: " + current.getDouble("wind_kph") + " kph");
        System.out.println("Humidity: " + current.getInt("humidity") + "%");
        System.out.println("Precipitation: " + current.getDouble("precip_mm") + " mm");
    }

    private static void displayForecastWeather(JSONObject weatherData) {
        System.out.println("\n3 Day Forecast:");
        for (int i = 0; i < 3; i++) {
            JSONObject day = weatherData.getJSONObject("forecast")
                    .getJSONArray("forecastday").getJSONObject(i);
            String date = day.getString("date");
            JSONObject dayCondition = day.getJSONObject("day");
            System.out.println("Date: " + date);
            System.out.println("Max Temp: " + dayCondition.getDouble("maxtemp_c") + "°C");
            System.out.println("Min Temp: " + dayCondition.getDouble("mintemp_c") + "°C");
            System.out.println("Average Temp: " + dayCondition.getDouble("avgtemp_c") + "°C");
            System.out.println("Condition: " + dayCondition.getJSONObject("condition").getString("text"));
            System.out.println("Total Precipitation: " + dayCondition.getDouble("totalprecip_mm") + " mm");
            System.out.println("----------------------------------------");
        }
    }
}
```