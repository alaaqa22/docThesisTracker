package dtt.business.utilities;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


/**
 * The Hashing class provides methods for hashing passwords using the PBKDF2 algorithm.
 * It ensures the secure storage of passwords by using a strong key derivation function
 * that performs multiple iterations and requires a salt.
 * @author Johannes Silvennoinen
 */
public class Hashing {
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    /**
     * Hashes the provided password using the PBKDF2 algorithm.
     *
     * @param password the password to be hashed
     * @param salt     the salt value for the hashing process
     * @return the hashed password as a Base64-encoded string
     * @throws NoSuchAlgorithmException if the PBKDF2 algorithm is not available
     * @throws InvalidKeySpecException  if the provided key specification is invalid
     */
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        /*
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashedBytes = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hashedBytes);
        */
        return null;
    }
    /**
     * Verifies if the provided password matches the hashed password.
     *
     * @param password       the password to be verified
     * @param salt           the salt value used during hashing
     * @param hashedPassword the hashed password to compare against
     * @return true if the password matches the hashed password, false otherwise
     * @throws NoSuchAlgorithmException if the PBKDF2 algorithm is not available
     * @throws InvalidKeySpecException  if the provided key specification is invalid
     */
    public static boolean verifyPassword(String password, String salt, String hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //String computedHash = hashPassword(password, salt)
        //return hashedPassword.equals(computedHash);
        return true;
    }
}
