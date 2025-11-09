```java
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID; // For simulating user IDs

/**
 * Represents a user in the authentication system.
 * Stores various authentication-related data for different methods.
 */
class User {
    private String userId;
    private String username;
    private String hashedPassword; // For password authentication (simulated hash)
    private String oauthProvider;  // For OAuth linkage (e.g., "google")
    private String oauthId;        // User's ID from the OAuth provider
    private byte[] biometricHash;  // For biometrics (simulated hash of biometric data)

    public User(String username) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public byte[] getBiometricHash() {
        return biometricHash;
    }

    public void setBiometricHash(byte[] biometricHash) {
        this.biometricHash = biometricHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}

/**
 * Represents the result of an authentication attempt.
 */
class AuthenticationResult {
    private boolean success;
    private String userId;
    private String message;

    public AuthenticationResult(boolean success, String userId, String message) {
        this.success = success;
        this.userId = userId;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}

/**
 * A Java class for managing user authentication with options for password, OAuth, or biometrics.
 * This class provides a simulated authentication service; actual security measures
 * (like strong hashing, real OAuth flows, and hardware biometrics) are represented by placeholders.
 */
public class UserAuthenticationService {

    // Simulating user storage (username -> User object)
    private final Map<String, User> userDatabase = new HashMap<>();
    // Simulating OAuth token validation (token -> userId)
    private final Map<String, String> validOAuthTokens = new HashMap<>();

    public UserAuthenticationService() {
        // Initialize with some dummy users for demonstration
        User user1 = new User("alice");
        user1.setHashedPassword(hashPassword("password123"));
        userDatabase.put(user1.getUsername(), user1);

        User user2 = new User("bob");
        user2.setOauthProvider("google");
        user2.setOauthId("google-bob-id-123");
        userDatabase.put(user2.getUsername(), user2);
        // Simulate a valid OAuth token for bob
        validOAuthTokens.put("google-token-bob-xyz", user2.getUserId());

        User user3 = new User("charlie");
        user3.setBiometricHash(simulateBiometricHash("charlie-fingerprint"));
        userDatabase.put(user3.getUsername(), user3);
    }

    /**
     * Registers a new user with a username and password.
     * @param username The desired username.
     * @param password The desired password (will be hashed).
     * @return The created User object if successful, null if username already exists.
     */
    public User registerUser(String username, String password) {
        if (userDatabase.containsKey(username)) {
            return null; // Username already exists
        }
        User newUser = new User(username);
        newUser.setHashedPassword(hashPassword(password));
        userDatabase.put(username, newUser);
        return newUser;
    }

    /**
     * Associates biometric data with an existing user.
     * @param username The username of the user.
     * @param biometricData The raw biometric data (simulated).
     * @return true if data was set, false if user not found.
     */
    public boolean setBiometricData(String username, byte[] biometricData) {
        User user = userDatabase.get(username);
        if (user != null) {
            user.setBiometricHash(biometricData);
            return true;
        }
        return false;
    }

    /**
     * Associates OAuth provider and ID with an existing user.
     * @param username The username of the user.
     * @param provider The OAuth provider (e.g., "google", "github").
     * @param oauthId The user's ID from the OAuth provider.
     * @return true if data was set, false if user not found.
     */
    public boolean setOAuthData(String username, String provider, String oauthId) {
        User user = userDatabase.get(username);
        if (user != null) {
            user.setOauthProvider(provider);
            user.setOauthId(oauthId);
            return true;
        }
        return false;
    }

    /**
     * Authenticates a user using username and password.
     * @param username The user's username.
     * @param password The user's plain-text password.
     * @return AuthenticationResult indicating success/failure and user ID.
     */
    public AuthenticationResult authenticatePassword(String username, String password) {
        User user = userDatabase.get(username);
        if (user == null || user.getHashedPassword() == null) {
            return new AuthenticationResult(false, null, "User not found or no password set.");
        }

        if (verifyPassword(password, user.getHashedPassword())) {
            return new AuthenticationResult(true, user.getUserId(), "Password authentication successful.");
        } else {
            return new AuthenticationResult(false, null, "Incorrect password.");
        }
    }

    /**
     * Authenticates a user using an OAuth token.
     * In a real scenario, this would involve contacting the OAuth provider for token validation.
     * Here, it's simulated by checking a known valid token map.
     * @param oauthProvider The OAuth provider (e.g., "google", "github").
     * @param oauthToken The OAuth access token obtained from the provider.
     * @return AuthenticationResult indicating success/failure and user ID.
     */
    public AuthenticationResult authenticateOAuth(String oauthProvider, String oauthToken) {
        String userId = validOAuthTokens.get(oauthToken);
        if (userId != null) {
            for (User user : userDatabase.values()) {
                if (user.getUserId().equals(userId) && Objects.equals(user.getOauthProvider(), oauthProvider)) {
                    return new AuthenticationResult(true, userId, "OAuth authentication successful for provider: " + oauthProvider);
                }
            }
        }
        return new AuthenticationResult(false, null, "Invalid OAuth token or user not linked to this provider.");
    }

    /**
     * Authenticates a user using biometric data.
     * In a real scenario, this involves device-specific hardware and APIs.
     * Here, it's simulated by comparing a stored hash.
     * @param username The user's username.
     * @param providedBiometricData The biometric data captured from the user (e.g., a fingerprint scan).
     * @return AuthenticationResult indicating success/failure and user ID.
     */
    public AuthenticationResult authenticateBiometrics(String username, byte[] providedBiometricData) {
        User user = userDatabase.get(username);
        if (user == null || user.getBiometricHash() == null) {
            return new AuthenticationResult(false, null, "User not found or no biometric data registered.");
        }

        if (compareBiometricData(user.getBiometricHash(), providedBiometricData)) {
            return new AuthenticationResult(true, user.getUserId(), "Biometric authentication successful.");
        } else {
            return new AuthenticationResult(false, null, "Biometric data mismatch.");
        }
    }

    // --- Private Helper Methods (Simulations) ---

    /**
     * Simulates password hashing. DO NOT USE IN PRODUCTION.
     * @param password The plain-text password.
     * @return A simulated hashed password.
     */
    private String hashPassword(String password) {
        // In a real application, use strong, industry-standard hashing with salts (e.g., BCrypt, PBKDF2).
        return "hashed_" + password;
    }

    /**
     * Simulates password verification. DO NOT USE IN PRODUCTION.
     * @param plainPassword The plain-text password provided by the user.
     * @param hashedPassword The stored hashed password.
     * @return true if passwords match, false otherwise.
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        // In a real application, compare hashes using a secure library.
        return hashedPassword.equals(hashPassword(plainPassword));
    }

    /**
     * Simulates generating a hash from raw biometric data.
     * @param rawData A string representing raw biometric data.
     * @return A byte array representing the simulated biometric hash.
     */
    private byte[] simulateBiometricHash(String rawData) {
        // In a real application, this would involve complex feature extraction and secure hashing.
        return rawData.getBytes();
    }

    /**
     * Simulates comparing stored biometric data with newly provided data.
     * @param storedHash The stored biometric hash.
     * @param providedData The newly captured biometric data.
     * @return true if the data matches, false otherwise.
     */
    private boolean compareBiometricData(byte[] storedHash, byte[] providedData) {
        // In a real application, this involves advanced biometric matching algorithms.
        return Objects.deepEquals(storedHash, providedData);
    }
}
```