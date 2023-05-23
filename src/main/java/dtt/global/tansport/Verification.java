package dtt.global.tansport;

import java.time.LocalDateTime;

/**
 *  represents a verification of the email of a user entity.
 */
public class Verification {
    private User user;  // The user being verified
    private String token;  // The verification token
    private LocalDateTime expiryDate;  // The expiry date of the verification token

    /**
     * Default constructor for the Verification class.
     */
    public Verification() {
    }

    /**
     * Constructs a Verification object with the specified user, token, and expiry date.
     * @param user The user being verified
     * @param token The verification token
     * @param expiryDate The expiry date of the verification token
     */
    public Verification(User user, String token, LocalDateTime expiryDate) {
        this.user = user;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    /**
     * Sets the user being verified.
     * @param user The user being verified
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the verification token.
     * @param token The verification token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Sets the expiry date of the verification token.
     * @param expiryDate The expiry date of the verification token
     */
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Returns the user being verified.
     * @return The user being verified
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the verification token.
     * @return The verification token
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the expiry date of the verification token.
     * @return The expiry date of the verification token
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}
