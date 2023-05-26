package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.UserDAO;
import jakarta.inject.Inject;

/**
 * Backing bean for reset Password.
 */
public class ResetPasswordBacking {
    @Inject
    private UserDAO userDAO;
    private String email;


    /**
     * Method to send a link to the user´s Email to reset the password.
     */

    public void sendResetPasswordEmail(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
