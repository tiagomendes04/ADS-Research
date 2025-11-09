import java.util.HashMap;
import java.util.Map;

public class Authenticator {

    private Map<String, String> users;
    private Map<String, String> oauthTokens;

    public Authenticator() {
        this.users = new HashMap<>();
        this.oauthTokens = new HashMap<>();
    }

    public boolean authenticatePassword(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            return true;
        }
        return false;
    }

    public boolean authenticateOAuth(String username, String token) {
        if (oauthTokens.containsKey(username) && oauthTokens.get(username).equals(token)) {
            return true;
        }
        return false;
    }

    public boolean authenticateBiometrics(String username, String biometricData) {
        // For simplicity, assume biometric data is stored in a HashMap
        Map<String, String> biometrics = new HashMap<>();
        biometrics.put("12345", "JohnDoe");
        biometrics.put("67890", "JaneDoe");

        if (biometrics.containsKey(username) && biometrics.get(username).equals(biometricData)) {
            return true;
        }
        return false;
    }

    public void registerUser(String username, String password) {
        users.put(username, password);
    }

    public void registerOAuthUser(String username, String token) {
        oauthTokens.put(username, token);
    }

    public void registerBiometricUser(String username, String biometricData) {
        // For simplicity, assume biometric data is stored in a HashMap
        Map<String, String> biometrics = new HashMap<>();
        biometrics.put("12345", "JohnDoe");
        biometrics.put("67890", "JaneDoe");
        biometrics.put(username, biometricData);
    }
}