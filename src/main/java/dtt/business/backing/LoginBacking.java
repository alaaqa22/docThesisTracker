package dtt.business.backing;

import dtt.business.utilities.Hashing;
import dtt.business.utilities.SessionInfo;
import dtt.business.utilities.SystemInitializer;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;
import dtt.global.utilities.ConfigReader;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Backing bean for the login page.
 *
 * @author Alaa Qasem
 */
@FacesConfig
@RequestScoped
@Named
public class LoginBacking implements Serializable {
    @Inject
    private SessionInfo sessionInfo;
    @Inject
    private UserDAO userDAO;
    private User user;
    private final Logger logger = LogManager.getLogger();


    /**
     * Initializes the login backing bean.
     */
    @PostConstruct
    public void init(){
        user = new User();

    }

    /**
     * Check the entered email and password and either show an error message or go
     * to circulationList page.
     *
     * @return Go to circulation-list page on success or stay
     * in the same page on failure.
     */
    public String login() {
        Transaction transaction = new Transaction();

            User userDB = new User();
            userDB.setEmail(user.getEmail());
           boolean found = userDAO.findUserByEmail(userDB, transaction);
            if(found){
                boolean verified;
                try {
                    verified = Hashing.verifyPassword(user.getPassword(), userDB.getPasswordSalt(), userDB.getPasswordHashed());

                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    logger.error("Login attempt unsuccessful for user " + user.getId());
                    throw new IllegalStateException(e);
                }
                if (verified) {
                    sessionInfo.setUser(userDB);

                    // Password matches, login successful
                    return "/views/authenticated/circulationslist?faces-redirect=true";
                } else {
                    // Password does not match, show error message

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials!", null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return null;
                }
            } else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                logger.error("User not found ");
                return null;
            }

    }





    /**
     * Go to register-page.
     *
     * @return Go to register-page.
     */
    public String register(){
       // return "/view/authenticated/circulationDetails?faces-redirect=true";
        return null;
    }

    /**
     * Go to forget-password page.
     *
     * @return Go to forget-password page.
     */
    public String forgetPass(){
        return null;
        //return "/view/authenticated/forgetPass?faces-redirect=true";
    }


    public User getUser() {
        return user;
    }
}
