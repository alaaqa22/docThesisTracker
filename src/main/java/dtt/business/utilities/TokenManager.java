package dtt.business.utilities;

import dtt.global.tansport.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The TokenManager class manages tokens for resetting passwords.
 * It provides methods for generating tokens, looking up tokens,
 * and clearing expired tokens.
 * @author Johannes Silvennoinen
 */
public class TokenManager {
    private static final Logger LOGGER = LogManager.getLogger(TokenManager.class);
    private static final int TOKEN_LENGTH = 32;
    public final Map<String, TokenData> tokenStore = new ConcurrentHashMap<>();
    public Duration tokenExpirationDuration = Duration.ofHours(1);

    /**
     * Generates a token for the specified user.
     * If a token already exists for the user, it is deleted and replaced with a new one.
     *
     * @param user the user for whom the token is generated
     * @return the generated token string
     */
    public String generateToken(User user) {
        //Deletes the old token when a user generates a new one.
        tokenStore.remove(user);
        String token = generateUniqueToken();
        Instant expirationTime = Instant.now().plus(tokenExpirationDuration);
        tokenStore.put(token, new TokenData(user, expirationTime));
        return token;
    }
    /**
     * Checks if the given token exists and is not expired.
     *
     * @param token the token to lookup
     * @return true if the token exists and is not expired, false otherwise
     */
    public boolean lookupToken(String token) {
        return tokenStore.containsKey(token) && !isTokenExpired(token);
    }
    /**
     * Clears expired tokens from the token store.
     */
    public void clearExpiredTokens() {
        tokenStore.entrySet().removeIf(entry -> isTokenExpired(entry.getKey()));
    }
    /**
     * Generates a unique token string.
     *
     * @return the generated token string
     */
    private String generateUniqueToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
    /**
     * Checks if the given token is expired.
     *
     * @param token the token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        TokenData tokenData = tokenStore.get(token);
        Instant expirationTime = tokenData.getExpirationTime();

        return expirationTime.isBefore(Instant.now());
    }

    public void setTokenExpirationDuration(Duration ofMinutes) {
        tokenExpirationDuration = ofMinutes;
    }

    /**
     * Represents the token data associated with a user.
     */
    private static class TokenData {
        private final User user;
        private final Instant expirationTime;
        /**
         * Constructs a new TokenData object.
         *
         * @param user            the user associated with the token
         * @param expirationTime  the expiration time of the token
         */
        TokenData(User user, Instant expirationTime) {
            this.user = user;
            this.expirationTime = expirationTime;
        }

        public User getUser() {
            return user;
        }

        public Instant getExpirationTime() {
            return expirationTime;
        }
    }
}
