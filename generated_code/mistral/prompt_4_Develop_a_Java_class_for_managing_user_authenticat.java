```java
import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {
    private Map<String, String> userCredentials;
    private Map<String, String> oauthTokens;
    private Map<String, String> biometricData;

    public AuthenticationManager() {
        this.userCredentials = new HashMap<>();
        this.oauthTokens = new HashMap<>();
        this.biometricData = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        userCredentials.put(username, password);
    }

    public boolean authenticateWithPassword(String username, String password) {
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public void registerOAuthToken(String username, String token) {
        oauthTokens.put(username, token);
    }

    public boolean authenticateWithOAuth(String username, String token) {
        String storedToken = oauthTokens.get(username);
        return storedToken != null && storedToken.equals(token);
    }

    public void registerBiometricData(String username, String biometricHash) {
        biometricData.put(username, biometricHash);
    }

    public boolean authenticateWithBiometrics(String username, String biometricHash) {
        String storedBiometric = biometricData.get(username);
        return storedBiometric != null && storedBiometric.equals(biometricHash);
    }

    public void updatePassword(String username, String newPassword) {
        if (userCredentials.containsKey(username)) {
            userCredentials.put(username, newPassword);
        }
    }

    public void updateOAuthToken(String username, String newToken) {
        if (oauthTokens.containsKey(username)) {
            oauthTokens.put(username, newToken);
        }
    }

    public void updateBiometricData(String username, String newBiometricHash) {
        if (biometricData.containsKey(username)) {
            biometricData.put(username, newBiometricHash);
        }
    }

    public boolean isUserRegistered(String username) {
        return userCredentials.containsKey(username) ||
               oauthTokens.containsKey(username) ||
               biometricData.containsKey(username);
    }
}
```