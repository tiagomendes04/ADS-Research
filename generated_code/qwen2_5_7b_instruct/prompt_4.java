```java
import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {

    private Map<String, String> passwordMap = new HashMap<>();
    private Map<String, String> oauthMap = new HashMap<>();
    private Map<String, String> biometricMap = new HashMap<>();

    public void setPassword(String userId, String password) {
        passwordMap.put(userId, password);
    }

    public boolean authenticatePassword(String userId, String password) {
        return passwordMap.get(userId) != null && passwordMap.get(userId).equals(password);
    }

    public void setOAuth(String userId, String token) {
        oauthMap.put(userId, token);
    }

    public boolean authenticateOAuth(String userId, String token) {
        return oauthMap.get(userId) != null && oauthMap.get(userId).equals(token);
    }

    public void setBiometric(String userId, String biometricId) {
        biometricMap.put(userId, biometricId);
    }

    public boolean authenticateBiometric(String userId, String biometricId) {
        return biometricMap.get(userId) != null && biometricMap.get(userId).equals(biometricId);
    }
}
```