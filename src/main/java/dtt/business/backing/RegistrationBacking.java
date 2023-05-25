package dtt.business.backing;

import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.global.tansport.User;
import dtt.global.tansport.Verification;
import jakarta.annotation.PostConstruct;

/**
 * Backing bean for the registration page.
 * @author Alaa Qasem
 */
public class RegistrationBacking {

    private User regUser;
    private UserDAO userDAO;
    private Verification verification;


    /**
     *  Initializes the data transfer object for the new registered user.
     */
    @PostConstruct
    public void init(){

    }

    /**
     * Register a new user, will send the verification link to the email
     * that the user has specified.
     *
     * @return Login page.
     */
    public String register(){
        return null;


    }

    public User getRegUser() {
        return regUser;
    }

    public void setRegUser(User regUser) {
        this.regUser = regUser;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }
}
