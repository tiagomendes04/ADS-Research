```java
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

// Simplified external dependencies placeholders
interface OAuthProvider {
    String getAuthorizationUrl(String clientId, String redirectUri, String state);
    String exchangeCodeForToken(String clientId, String clientSecret, String code, String redirectUri);
    boolean validateToken(String accessToken);
}

interface BiometricScanner {
    boolean scan(); // Returns true if biometric data matches
}

public class AuthManager {

    // ---------- Configuration ----------
    private final Map<String, String> passwordStore = new ConcurrentHashMap<>(); // username -> hashed password
    private final OAuthProvider oauthProvider;
    private final BiometricScanner biometricScanner;
    private final Set<String> activeTokens = Collections.synchronizedSet(new HashSet<>());

    // ---------- Constructor ----------
    public AuthManager(OAuthProvider oauthProvider, BiometricScanner biometricScanner) {
        this.oauthProvider = Objects.requireNonNull(oauthProvider);
        this.biometricScanner = Objects.requireNonNull(biometricScanner);
    }

    // ---------- Password Authentication ----------
    public void registerUser(String username, String rawPassword, Supplier<String> hashFunction) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(rawPassword);
        Objects.requireNonNull(hashFunction);
        String hashed = hashFunction.get();
        passwordStore.put(username, hashed);
    }

    public boolean loginWithPassword(String username, String rawPassword, Supplier<String> hashFunction) {
        String storedHash = passwordStore.get(username);
        if (storedHash == null) return false;
        String providedHash = hashFunction.get();
        return storedHash.equals(providedHash);
    }

    // ---------- OAuth Authentication ----------
    public String getOAuthLoginUrl(String clientId, String redirectUri, String state) {
        return oauthProvider.getAuthorizationUrl(clientId, redirectUri, state);
    }

    public boolean loginWithOAuth(String clientId,
                                  String clientSecret,
                                  String code,
                                  String redirectUri) {
        String token = oauthProvider.exchangeCodeForToken(clientId, clientSecret, code, redirectUri);
        if (token == null) return false;
        boolean valid = oauthProvider.validateToken(token);
        if (valid) activeTokens.add(token);
        return valid;
    }

    // ---------- Biometric Authentication ----------
    public boolean loginWithBiometrics() {
        return biometricScanner.scan();
    }

    // ---------- Token Management ----------
    public boolean isTokenActive(String token) {
        return activeTokens.contains(token);
    }

    public void revokeToken(String token) {
        activeTokens.remove(token);
    }

    // ---------- Utility ----------
    public static String defaultHash(String password) {
        // Simple placeholder hash (DO NOT use in production)
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    // Example usage (remove or replace in production)
    public static void main(String[] args) {
        // Mock implementations
        OAuthProvider mockOAuth = new OAuthProvider() {
            public String getAuthorizationUrl(String clientId, String redirectUri, String state) {
                return "https://example.com/auth?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&state=" + state;
            }
            public String exchangeCodeForToken(String clientId, String clientSecret, String code, String redirectUri) {
                return "mock-access-token-" + code;
            }
            public boolean validateToken(String accessToken) {
                return accessToken != null && accessToken.startsWith("mock-access-token-");
            }
        };
        BiometricScanner mockBiometric = () -> true; // always succeeds

        AuthManager auth = new AuthManager(mockOAuth, mockBiometric);

        // Password flow
        auth.registerUser("alice", "password123", () -> defaultHash("password123"));
        boolean pwdOk = auth.loginWithPassword("alice", "password123", () -> defaultHash("password123"));
        System.out.println("Password login success: " + pwdOk);

        // OAuth flow
        String authUrl = auth.getOAuthLoginUrl("client-id", "https://app/callback", "xyz");
        System.out.println("Visit: " + authUrl);
        boolean oauthOk = auth.loginWithOAuth("client-id", "client-secret", "authcode", "https://app/callback");
        System.out.println("OAuth login success: " + oauthOk);

        // Biometric flow
        boolean bioOk = auth.loginWithBiometrics();
        System.out.println("Biometric login success: " + bioOk);
    }
}
```