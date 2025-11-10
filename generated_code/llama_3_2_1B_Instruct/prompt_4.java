```java
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    public static Map<String, User> users = new HashMap<>();
    public static Map<String, String> OAuth = new HashMap<>();
    public static Map<String, String> biometrics = new HashMap<>();
    public static Map<String, String> password = new HashMap<>();
    public static Map<String, String> oauth = new HashMap<>();

    public static void main(String[] args) {
        users.put("user1", new User("password1", true));
        users.put("user2", new User("password2", false));

        OAuth.put("client_id", "client_id1");
        OAuth.put("client_secret", "client_secret1");
        OAuth.put("redirect_uri", "redirect_uri1");

        biometrics.put("device_id", "device_id1");
        biometrics.put("client_id", "client_id1");
        biometrics.put("client_secret", "client_secret1");

        password.put("user1", "password1");
        password.put("user2", "password2");

        OAuth.put("client_id", "client_id1");
        OAuth.put("client_secret", "client_secret1");
        OAuth.put("redirect_uri", "redirect_uri1");

        biometrics.put("device_id", "device_id1");
        biometrics.put("client_id", "client_id1");
        biometrics.put("client_secret", "client_secret1");

        password.put("user1", "password1");
        password.put("user2", "password2");

        User user = authenticate("user1", "password1");
        System.out.println(user);
    }

    public static User authenticate(String username, String password) {
        if (password.equals(password)) {
            return users.get(username);
        } else if (oauth.get(username) != null) {
            return oauth.get(username);
        } else if (biometrics.get(username) != null) {
            return biometrics.get(username);
        } else if (oauth.containsKey(username)) {
            return oauth.get(username);
        }
        return null;
    }
}
```