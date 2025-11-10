```java
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherApp {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city name: ");
        String city = scanner.nextLine();

        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(getWeatherFromOpenWeatherMap(city));
        weatherList.add(getWeatherFromWeatherAPI(city));
        weatherList.add(getWeatherFromDarkSkyAPI(city));

        printWeatherData(weatherList);
    }

    private static Weather getWeatherFromOpenWeatherMap(String city) throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=YOUR_OPENWEATHERMAP_API_KEY";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return new Weather(node.get("main").get("temp").asDouble(),
                node.get("main").get("humidity").asInt(),
                node.get("weather").get(0).get("description").asText());
    }

    private static Weather getWeatherFromWeatherAPI(String city) throws IOException {
        String url = "http://api.weatherapi.com/v1/current.json?key=YOUR_WEATHER_API_KEY&q=" + city;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return new Weather(node.get("current").get("temp_c").asDouble(),
                node.get("current").get("humidity").asInt(),
                node.get("current").get("condition").get("text").asText());
    }

    private static Weather getWeatherFromDarkSkyAPI(String city) throws IOException {
        String url = "https://api.darksky.net/forecast/YOUR_DARKSKY_API_KEY/37.8267,-122.4233";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return new Weather(node.get("currently").get("temperature").asDouble(),
                node.get("currently").get("humidity").asInt(),
                node.get("currently").get("summary").asText());
    }

    private static void printWeatherData(List<Weather> weatherList) {
        for (int i = 0; i < weatherList.size(); i++) {
            System.out.println("Weather Source " + (i + 1) + ":");
            System.out.println("Temperature: " + weatherList.get(i).getTemperature());
            System.out.println("Humidity: " + weatherList.get(i).getHumidity());
            System.out.println("Description: " + weatherList.get(i).getDescription());
            System.out.println();
        }
    }

    private static class Weather {
        private double temperature;
        private int humidity;
        private String description;

        public Weather(double temperature, int humidity, String description) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.description = description;
        }

        public double getTemperature() {
            return temperature;
        }

        public int getHumidity() {
            return humidity;
        }

        public String getDescription() {
            return description;
        }
    }
}
```