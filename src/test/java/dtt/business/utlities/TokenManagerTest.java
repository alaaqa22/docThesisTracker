package dtt.business.utlities;

import dtt.business.utilities.TokenManager;
import dtt.global.tansport.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TokenManagerTest {

    private TokenManager tokenManager;
    private User user;

    @BeforeEach
    public void setUp() {
        tokenManager = new TokenManager();
        user = new User();
        user.setEmail("example@example.com");

    }

    @Test
    public void testGetUserForToken() {
        String token = tokenManager.generateToken(user);
        assertEquals(user, tokenManager.getUserForToken(token));
    }

    @Test
    public void testGetUserForTokenMisMatch() {
        String token = tokenManager.generateToken(user);
        User user = new User();
        assertNotEquals(user, tokenManager.getUserForToken(token));
    }

    @Test
    public void testGenerateToken() {
        User user = new User();
        String token = tokenManager.generateToken(user);
        Assertions.assertNotNull(token);
        assertTrue(tokenManager.lookupToken(token));
    }

    @Test
    public void testLookupToken() {
        User user = new User();
        String token = tokenManager.generateToken(user);
        assertTrue(tokenManager.lookupToken(token));
    }

    @Test
    public void testLookupExpiredToken() throws InterruptedException {
        // Set a shorter token expiration duration for testing purposes
        tokenManager.setTokenExpirationDuration(Duration.ofSeconds(1));
        User user = new User();
        String token = tokenManager.generateToken(user);

        // Wait for the token to expire
        Thread.sleep(1001);

        assertFalse(tokenManager.lookupToken(token));
    }

    @Test
    public void testClearExpiredTokens() throws InterruptedException {
        // Set a shorter token expiration duration for testing purposes
        tokenManager.setTokenExpirationDuration(Duration.ofSeconds(1));
        User user1 = new User();
        String token1 = tokenManager.generateToken(user1);

        User user2 = new User();
        String token2 = tokenManager.generateToken(user2);

        // Wait for the tokens to expire
        Thread.sleep(1001);

        // Clear expired tokens
        tokenManager.clearExpiredTokens();

        assertFalse(tokenManager.lookupToken(token1));
        assertFalse(tokenManager.lookupToken(token2));
    }

    @Test
    void testLookupTokenForUser_WithValidTokenAndMatchingUser() {
        // Generate a token for user
        String token = tokenManager.generateToken(user);
        boolean result = tokenManager.lookupTokenForUser(token, user);
        assertTrue(result, "Token lookup for a valid token and matching user should return true");
    }

    @Test
    void testLookupTokenForUser_WithValidTokenAndNonMatchingUser() {
        // Generate a token for the user
        String token = tokenManager.generateToken(user);

        // Create a different user with a different email address
        User differentUser = new User();
        differentUser.setEmail("different@exampe.com");

        // Perform the lookup for the token and non-matching user
        boolean result = tokenManager.lookupTokenForUser(token, differentUser);

        assertFalse(result, "Token lookup for a valid token and non-matching user should return false.");
    }

    @Test
    void testLookUpTokenForUser_WithInvalidToken() {
        // Perform the lookup for an invalid token
        boolean result = tokenManager.lookupTokenForUser("invalidToken", user);
        assertFalse(result, "Token lookup for an invalid token should return false.");
    }

    @Test
    void testLookupTokenForUser_WithExpiredTokenAndMatchingUser() throws InterruptedException {
        // Set a shorter token expiration duration for testing purposes
        tokenManager.setTokenExpirationDuration(Duration.ofSeconds(1));
        String token = tokenManager.generateToken(user);
        // Wait for the token to expire
        Thread.sleep(1001);
        // Perform the lookup for the expired token and matching user
        boolean result = tokenManager.lookupTokenForUser(token, user);

        assertFalse(result, "Token lookup for an expired token and matching user should return false.");
    }
}
