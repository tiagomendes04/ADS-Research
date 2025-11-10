```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class WeatherApp {

    public static void main(String[] args) throws Exception {
        String apiKey = "YOUR_API_KEY";
        String url = "https://api.openweathermap.org/data/2.5/weather?appid=" + apiKey;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode() == 200) {
            Scanner in = new Scanner(con.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            System.out.println(jsonObject.getString("main.temp"));
            System.out.println(jsonObject.getString("main.humidity"));
        }
    }
}
```