package dtt.business.utilities;

import dtt.global.tansport.User;
import jakarta.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
public class TokenManager {
    private static final Logger LOGGER = LogManager.getLogger(TokenManager.class);
    private static final int TOKEN_LENGTH = 32;
    private final Map<String, TokenData> tokenStore = new ConcurrentHashMap<>();
    private Duration tokenExpirationDuration = Duration.ofHours(1);

    /**
     * Generates a token for the specified user.
     * If a token already exists for the user, it is deleted and replaced with a new one.
     *
     * @param user the user for whom the token is generated
     * @return the generated token string
     */
    public String generateToken(User user) {
        // Remove any existing tokens associated with the user.
        if (user.getEmail() != null) {
            deleteTokenForUser(user);
        }
        // Generate a new unique token.
        String token = generateUniqueToken();
        LOGGER.debug("TOKEN: " + token);
        // Set the expiration time.
        Instant expirationTime = Instant.now().plus(tokenExpirationDuration);
        // Put the token and the tokendata in to the token store.
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
        LOGGER.debug("lookupToken called for: " + token);
        return tokenStore.containsKey(token) && !isTokenExpired(token);
    }

    /**
     * Returns a User for a token.
     * @param token
     * @return
     */
    public User getUserForToken(String token) {
        return tokenStore.get(token).user;
    }
    /**
     * Looks up if a token exists for the user. Only checks if the email matches.
     *
     * @param token The token to be checked
     * @param user The user that checks the token.
     * @return True when the given token is mapped to the user in the tokenStore.
     */
    public boolean lookupTokenForUser(String token, User user) {
        if (lookupToken(token)) {
            return user.getEmail().equals(tokenStore.get(token).getUser().getEmail());
        }
        return false;
    }
    /**
     * Clears expired tokens from the token store.
     */
    public void clearExpiredTokens() {
        LOGGER.debug("clearExpiredTokens() called.");
        tokenStore.entrySet().removeIf(entry -> isTokenExpired(entry.getKey()));
    }
    /**
     * Generates a unique token string with SecureRandom.
     *
     * @return the generated token string.
     */
    private String generateUniqueToken() {
        LOGGER.debug("generateUniqueToken() called.");
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
    /**
     * Checks if the given token is expired.
     *
     * @param token the token to check.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        LOGGER.debug("isTokenExpired() called for token: " + token);
        TokenData tokenData = tokenStore.get(token);
        Instant expirationTime = tokenData.getExpirationTime();

        return expirationTime.isBefore(Instant.now());
    }

    /**
     * Sets the duration in minutes, how long the token lives.
     * @param ofMinutes Minutes the token is valid.
     */
    public void setTokenExpirationDuration(Duration ofMinutes) {
        // This class is mostly needed to test token expiry.
        tokenExpirationDuration = ofMinutes;
    }

    /**
     * Deletes the token out of the tokenstore if the user has a token.
     * @param user the user whose token should be deleted.
     */
    public void deleteTokenForUser(User user) {
        for (Map.Entry<String, TokenData> entry: tokenStore.entrySet()) {
            String token = entry.getKey();
            TokenData value = entry.getValue();
            if (value.user.getEmail().equals(user.getEmail())) {
                LOGGER.debug("Deleting token for user: " + user.getEmail());
                tokenStore.remove(token);
            }
        }
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
         * @param user            the user associated with the token.
         * @param expirationTime  the expiration time of the token.
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
