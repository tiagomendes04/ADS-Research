```java
import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {

    private Map<String, User> users;

    public AuthenticationManager() {
        users = new HashMap<>();
    }

    public void addUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public boolean authenticateOAuth(String username, String accessToken) {
        // OAuth authentication logic
        return false;
    }

    public boolean authenticateBiometric(String username) {
        // Biometric authentication logic
        return false;
    }

    public class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }
}
```