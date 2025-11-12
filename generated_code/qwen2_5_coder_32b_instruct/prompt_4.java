```java
public class AuthenticationManager {
    public enum AuthMethod { PASSWORD, OAUTH, BIOMETRICS }

    private String username;
    private String password;
    private String oauthToken;
    private boolean biometricsEnabled;

    public AuthenticationManager(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public void setBiometricsEnabled(boolean biometricsEnabled) {
        this.biometricsEnabled = biometricsEnabled;
    }

    public boolean authenticate(AuthMethod method) {
        switch (method) {
            case PASSWORD:
                return authenticateWithPassword();
            case OAUTH:
                return authenticateWithOAuth();
            case BIOMETRICS:
                return authenticateWithBiometrics();
            default:
                return false;
        }
    }

    private boolean authenticateWithPassword() {
        return password != null && !password.isEmpty();
    }

    private boolean authenticateWithOAuth() {
        return oauthToken != null && !oauthToken.isEmpty();
    }

    private boolean authenticateWithBiometrics() {
        return biometricsEnabled;
    }
}
```