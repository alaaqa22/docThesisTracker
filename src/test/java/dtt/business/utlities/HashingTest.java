package dtt.business.utlities;


import dtt.business.utilities.Hashing;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class HashingTest {

    @Test
    public void testHashPassword() {
        String password = "password123";
        String salt = "somesaltvalue";

        try {
            String hashedPassword = Hashing.hashPassword(password, salt);
            Assert.assertNotNull(hashedPassword);
            Assert.assertEquals("Vuy8xT+3S0aHMp8XCn1b9WeYvuLvklmPWqpRcWNC1fE=", hashedPassword); // Assuming the expected length of the hashed password is 44 characters
        } catch (Exception e) {
            Assert.fail("An exception occurred during password hashing: " + e.getMessage());
        }
    }
    @Test
    public void testVerifyPasswordMatching() {
        String password = "SuperDuperSecurePassword1";
        String salt = "somesuperlongsalt";
        String hashedPassword;
        try {
            hashedPassword = Hashing.hashPassword(password, salt);
            boolean result = Hashing.verifyPassword(password, salt, hashedPassword);
            Assert.assertTrue(result);
        } catch (Exception e) {
            Assert.fail("An exception occurred during password verification: " + e.getMessage());
        }
    }

    @Test
    public void testVerifyPasswordMismatched() {
        String password = "password123";
        String salt = "somesaltvalue";
        String invalidHash = "invalidhash";

        try {
            boolean result = Hashing.verifyPassword(password, salt, invalidHash);
            Assert.assertFalse(result);
        } catch (Exception e) {
            Assert.fail("An exception occurred during password verification: " + e.getMessage());
        }
    }

    @Test
    public void testVerifyPasswordIncorrectSalt() {
        String password = "password123";
        String salt = "somesaltvalue";
        String hashedPassword = null;

        try {
            hashedPassword = Hashing.hashPassword(password, salt);
            boolean result = Hashing.verifyPassword(password, "invalidsalt", hashedPassword);
            Assert.assertFalse(result);
        } catch (Exception e) {
            Assert.fail("An exception occurred during password verification: " + e.getMessage());
        }
    }

    @Test
    public void testVerifyPasswordIncorrectPassword() {
        String password = "password123";
        String salt = "somesaltvalue";
        String hashedPassword = null;

        try {
            hashedPassword = Hashing.hashPassword(password, salt);
            boolean result = Hashing.verifyPassword("incorrectpassword", salt, hashedPassword);
            Assert.assertFalse(result);
        } catch (Exception e) {
            Assert.fail("An exception occurred during password verification: " + e.getMessage());
        }
    }
}
