package dtt.business.utlities;

import dtt.business.utilities.TokenManager;
import dtt.global.tansport.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class TokenManagerTest {

    private TokenManager tokenManager;

    @BeforeEach
    public void setUp() {
        tokenManager = new TokenManager();
        // Set a shorter token expiration duration for testing purposes
        tokenManager.setTokenExpirationDuration(Duration.ofSeconds(1));
    }

    @Test
    public void testGenerateToken() {
        User user = new User();
        String token = tokenManager.generateToken(user);
        Assertions.assertNotNull(token);
        Assertions.assertTrue(tokenManager.lookupToken(token));
    }

    @Test
    public void testLookupToken() {
        User user = new User();
        String token = tokenManager.generateToken(user);
        Assertions.assertTrue(tokenManager.lookupToken(token));
    }

    @Test
    public void testLookupExpiredToken() throws InterruptedException {
        User user = new User();
        String token = tokenManager.generateToken(user);

        // Wait for the token to expire
        Thread.sleep(1001);

        Assertions.assertFalse(tokenManager.lookupToken(token));
    }

    @Test
    public void testClearExpiredTokens() throws InterruptedException {
        User user1 = new User();
        String token1 = tokenManager.generateToken(user1);

        User user2 = new User();
        String token2 = tokenManager.generateToken(user2);

        // Wait for the tokens to expire
        Thread.sleep(1001);

        // Clear expired tokens
        tokenManager.clearExpiredTokens();

        Assertions.assertFalse(tokenManager.lookupToken(token1));
        Assertions.assertFalse(tokenManager.lookupToken(token2));
    }
}
