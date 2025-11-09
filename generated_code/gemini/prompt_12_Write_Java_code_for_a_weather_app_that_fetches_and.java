```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.Gson;

public class WeatherApp {

    private static final String API_KEY_OPENWEATHER = "YOUR_OPENWEATHER_API_KEY";
    private static final String API_KEY_WEATHERAPI = "YOUR_WEATHERAPI_API_KEY";
    private static final String API_KEY_ACCUWEATHER = "YOUR_ACCUWEATHER_API_KEY";
    private static final String CITY_NAME = "London";


    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<WeatherData> openWeatherFuture = CompletableFuture.supplyAsync(() -> fetchWeatherDataFromOpenWeather(CITY_NAME), executor);
        CompletableFuture<WeatherData> weatherApiFuture = CompletableFuture.supplyAsync(() -> fetchWeatherDataFromWeatherAPI(CITY_NAME), executor);
        CompletableFuture<WeatherData> accuWeatherFuture = CompletableFuture.supplyAsync(() -> fetchWeatherDataFromAccuWeather(CITY_NAME), executor);


        CompletableFuture.allOf(openWeatherFuture, weatherApiFuture, accuWeatherFuture)
                .thenApply(Void -> {
                    List<WeatherData> weatherDataList = new ArrayList<>();
                    try {
                        weatherDataList.add(openWeatherFuture.get());
                        weatherDataList.add(weatherApiFuture.get());
                        weatherDataList.add(accuWeatherFuture.get());
                    } catch (Exception e) {
                        System.err.println("Error getting weather data: " + e.getMessage());
                        return null;
                    }
                    return weatherDataList;
                })
                .thenAccept(weatherDataList -> {
                    if (weatherDataList != null) {
                        displayWeatherData(weatherDataList);
                    }
                })
                .join();


        executor.shutdown();
    }


    private static WeatherData fetchWeatherDataFromOpenWeather(String city) {
        try {
            String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, API_KEY_OPENWEATHER);
            String jsonData = fetchData(apiUrl);
            Gson gson = new Gson();
            OpenWeatherResponse response = gson.fromJson(jsonData, OpenWeatherResponse.class);

            WeatherData weatherData = new WeatherData();
            weatherData.setSource("OpenWeatherMap");
            weatherData.setTemperature(response.getMain().getTemp());
            weatherData.setDescription(response.getWeather().get(0).getDescription());

            return weatherData;

        } catch (IOException e) {
            System.err.println("Error fetching data from OpenWeatherMap: " + e.getMessage());
            return null;
        }
    }


    private static WeatherData fetchWeatherDataFromWeatherAPI(String city) {
        try {
            String apiUrl = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s", API_KEY_WEATHERAPI, city);
            String jsonData = fetchData(apiUrl);
            Gson gson = new Gson();
            WeatherAPIResponse response = gson.fromJson(jsonData, WeatherAPIResponse.class);

            WeatherData weatherData = new WeatherData();
            weatherData.setSource("WeatherAPI");
            weatherData.setTemperature(response.getCurrent().getTemp_c());
            weatherData.setDescription(response.getCurrent().getCondition().getText());
            return weatherData;


        } catch (IOException e) {
            System.err.println("Error fetching data from WeatherAPI: " + e.getMessage());
            return null;
        }
    }

    private static WeatherData fetchWeatherDataFromAccuWeather(String city) {
        try {

            String locationKey = getLocationKey(city);
            if (locationKey == null) {
                return null;
            }

            String apiUrl = String.format("http://dataservice.accuweather.com/currentconditions/v1/%s?apikey=%s&details=true", locationKey, API_KEY_ACCUWEATHER);
            String jsonData = fetchData(apiUrl);

            Gson gson = new Gson();
            AccuWeatherResponse[] responses = gson.fromJson(jsonData, AccuWeatherResponse[].class);

            if (responses == null || responses.length == 0) {
                System.err.println("No weather data found for AccuWeather.");
                return null;
            }

            AccuWeatherResponse response = responses[0];

            WeatherData weatherData = new WeatherData();
            weatherData.setSource("AccuWeather");
            weatherData.setTemperature(response.getTemperature().getMetric().getValue());
            weatherData.setDescription(response.getWeatherText());
            return weatherData;


        } catch (IOException e) {
            System.err.println("Error fetching data from AccuWeather: " + e.getMessage());
            return null;
        }
    }

    private static String getLocationKey(String city) throws IOException {
          String encodedCity = java.net.URLEncoder.encode(city, "UTF-8");
          String apiUrl = String.format("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=%s&q=%s", API_KEY_ACCUWEATHER, encodedCity);
          String jsonData = fetchData(apiUrl);

          Gson gson = new Gson();
          AccuWeatherLocationResponse[] locationResponses = gson.fromJson(jsonData, AccuWeatherLocationResponse[].class);

        if (locationResponses == null || locationResponses.length == 0) {
            System.err.println("No location found for city: " + city + " in AccuWeather");
            return null;
        }

          return locationResponses[0].getKey();
    }



    private static String fetchData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

    private static void displayWeatherData(List<WeatherData> weatherDataList) {
        System.out.println("Weather Data:");
        for (WeatherData weatherData : weatherDataList) {
            System.out.println("Source: " + weatherData.getSource());
            System.out.println("Temperature: " + weatherData.getTemperature() + " °C");
            System.out.println("Description: " + weatherData.getDescription());
            System.out.println("---");
        }
    }
}


class WeatherData {
    private String source;
    private double temperature;
    private String description;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


// OpenWeatherMap POJOs
class OpenWeatherResponse {
    private Main main;
    private List<Weather> weather;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }
}

class Main {
    private double temp;

    public double getTemp() {
        return temp;
    }
}

class Weather {
    private String description;

    public String getDescription() {
        return description;
    }
}


// WeatherAPI POJOs
class WeatherAPIResponse {
    private Current current;

    public Current getCurrent() {
        return current;
    }
}

class Current {
    private double temp_c;
    private Condition condition;

    public double getTemp_c() {
        return temp_c;
    }

    public Condition getCondition() {
        return condition;
    }
}

class Condition {
    private String text;

    public String getText() {
        return text;
    }
}


//AccuWeather POJOs
class AccuWeatherResponse {
    private Temperature temperature;
    private String weatherText;

    public Temperature getTemperature() {
        return temperature;
    }

    public String getWeatherText() {
        return weatherText;
    }
}

class Temperature {
    private Metric metric;

    public Metric getMetric() {
        return metric;
    }
}

class Metric {
    private double value;

    public double getValue() {
        return value;
    }
}

class AccuWeatherLocationResponse {
  private String Key;

  public String getKey() {
    return Key;
  }

  public void setKey(String key) {
    Key = key;
  }
}
```