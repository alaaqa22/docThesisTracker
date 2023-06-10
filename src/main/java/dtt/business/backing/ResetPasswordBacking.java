package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.UserDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Backing bean for reset-password page.
 */
@RequestScoped
@Named
public class ResetPasswordBacking implements Serializable {
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

}
