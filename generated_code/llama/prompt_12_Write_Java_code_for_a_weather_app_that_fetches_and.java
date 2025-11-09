**WeatherApp.java**
```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WeatherApp extends Activity {

    private static final String API_KEY = "YOUR_OPENWEATHERMAP_API_KEY";
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String FORECAST_API_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String DARK_SKY_API_URL = "https://api.darksky.net/forecast/YOUR_DARK_SKY_API_KEY/37.8267,-122.4233";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        TextView weatherTextView = findViewById(R.id.weather_text_view);
        TextView forecastTextView = findViewById(R.id.forecast_text_view);
        TextView darkSkyTextView = findViewById(R.id.dark_sky_text_view);

        fetchWeatherData(weatherTextView);
        fetchForecastData(forecastTextView);
        fetchDarkSkyData(darkSkyTextView);
    }

    private void fetchWeatherData(TextView weatherTextView) {
        String url = WEATHER_API_URL + "?q=London&appid=" + API_KEY + "&units=metric";
        try {
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                String weatherDescription = weatherObject.getString("description");
                weatherTextView.setText("Weather: " + weatherDescription);
            } else {
                Log.d("WeatherApp", "Failed to fetch weather data");
            }
        } catch (Exception e) {
            Log.d("WeatherApp", "Error fetching weather data: " + e.getMessage());
        }
    }

    private void fetchForecastData(TextView forecastTextView) {
        String url = FORECAST_API_URL + "?q=London&appid=" + API_KEY + "&units=metric";
        try {
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray listArray = jsonObject.getJSONArray("list");
                List<String> forecastList = new ArrayList<>();
                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject forecastObject = listArray.getJSONObject(i);
                    String forecast = forecastObject.getString("main");
                    forecastList.add(forecast);
                }
                forecastTextView.setText("Forecast: " + String.join(", ", forecastList));
            } else {
                Log.d("WeatherApp", "Failed to fetch forecast data");
            }
        } catch (Exception e) {
            Log.d("WeatherApp", "Error fetching forecast data: " + e.getMessage());
        }
    }

    private void fetchDarkSkyData(TextView darkSkyTextView) {
        String url = DARK_SKY_API_URL;
        try {
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                String currently = jsonObject.getString("currently");
                JSONObject currentlyObject = new JSONObject(currently);
                String summary = currentlyObject.getString("summary");
                darkSkyTextView.setText("Dark Sky: " + summary);
            } else {
                Log.d("WeatherApp", "Failed to fetch dark sky data");
            }
        } catch (Exception