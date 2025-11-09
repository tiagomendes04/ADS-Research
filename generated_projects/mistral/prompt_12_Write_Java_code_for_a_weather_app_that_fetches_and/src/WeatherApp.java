import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {
    private static final String OPENWEATHER_API_KEY = "your_api_key";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    private static final String WEATHERBIT_API_KEY = "your_api_key";
    private static final String WEATHERBIT_API_URL = "https://api.weatherbit.io/v2.0/current?city=%s&key=%s";

    public static void main(String[] args) {
        String city = "London";
        List<WeatherData> weatherDataList = fetchWeatherData(city);
        displayWeatherData(weatherDataList);
    }

    public static List<WeatherData> fetchWeatherData(String city) {
        List<WeatherData> weatherDataList = new ArrayList<>();

        // Fetch from OpenWeatherMap
        String openWeatherJson = fetchData(String.format(WEATHER_API_URL, city, OPENWEATHER_API_KEY));
        if (openWeatherJson != null) {
            WeatherData openWeatherData = parseOpenWeatherData(openWeatherJson);
            if (openWeatherData != null) {
                weatherDataList.add(openWeatherData);
            }
        }

        // Fetch from Weatherbit
        String weatherbitJson = fetchData(String.format(WEATHERBIT_API_URL, city, WEATHERBIT_API_KEY));
        if (weatherbitJson != null) {
            WeatherData weatherbitData = parseWeatherbitData(weatherbitJson);
            if (weatherbitData != null) {
                weatherDataList.add(weatherbitData);
            }
        }

        return weatherDataList;
    }

    private static String fetchData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static WeatherData parseOpenWeatherData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);

            double temp = main.getDouble("temp");
            double humidity = main.getDouble("humidity");
            String description = weather.getString("description");

            return new WeatherData("OpenWeatherMap", temp, humidity, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static WeatherData parseWeatherbitData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data = jsonObject.getJSONArray("data");
            JSONObject weatherData = data.getJSONObject(0);

            double temp = weatherData.getDouble("temp");
            double humidity = weatherData.getDouble("rh");
            String description = weatherData.getString("weather").getJSONObject("description");

            return new WeatherData("Weatherbit", temp, humidity, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void displayWeatherData(List<WeatherData> weatherDataList) {
        for (WeatherData data : weatherDataList) {
            System.out.println("Source: " + data.getSource());
            System.out.println("Temperature: " + data.getTemperature() + "°C");
            System.out.println("Humidity: " + data.getHumidity() + "%");
            System.out.println("Description: " + data.getDescription());
            System.out.println("------------------");
        }
    }
}

class WeatherData {
    private String source;
    private double temperature;
    private double humidity;
    private String description;

    public WeatherData(String source, double temperature, double humidity, String description) {
        this.source = source;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }
}