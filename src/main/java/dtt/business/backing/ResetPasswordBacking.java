package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.global.tansport.Verification;
import jakarta.inject.Inject;

/**
 * Backing bean for reset Password.
 */
public class ResetPasswordBacking {
    @Inject
    private Verification verification;
    @Inject
    private UserDAO userDAO;
    private String email;



    public void sendResetPasswordEmail(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
