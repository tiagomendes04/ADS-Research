```java
import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private static final Map<String, Authenticator> instances = new HashMap<>();
    private final String id;
    private final String password;
    private final String oauthToken;
    private final String biometric;

    private Authenticator(String id, String password, String oauthToken, String biometric) {
        this.id = id;
        this.password = password;
        this.oauthToken = oauthToken;
        this.biometric = biometric;
    }

    public static Authenticator getInstance(String id, String password, String oauthToken, String biometric) {
        if (instances.containsKey(id)) {
            return instances.get(id);
        } else {
            Authenticator authenticator = new Authenticator(id, password, oauthToken, biometric);
            instances.put(id, authenticator);
            return authenticator;
        }
    }

    public boolean authenticatePassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public boolean authenticateOAuth(String inputToken) {
        return oauthToken.equals(inputToken);
    }

    public boolean authenticateBiometric() {
        return biometric != null;
    }

    public boolean authenticate(String inputPassword, String inputToken, String biometricInput) {
        return authenticatePassword(inputPassword) || authenticateOAuth(inputToken) || authenticateBiometric();
    }

    public String getId() {
        return id;
    }
}

class OAuthAuthenticator extends Authenticator {
    public OAuthAuthenticator(String id, String oauthToken) {
        super(id, null, oauthToken, null);
    }

    @Override
    public boolean authenticateOAuth(String inputToken) {
        return false; // OAuth is already handled by the parent class
    }
}

class BiometricAuthenticator extends Authenticator {
    public BiometricAuthenticator(String id, String biometric) {
        super(id, null, null, biometric);
    }

    @Override
    public boolean authenticateBiometric() {
        return true;
    }
}
```