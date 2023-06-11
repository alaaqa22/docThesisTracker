package dtt.business.backing;

import dtt.business.utilities.Hashing;
import dtt.business.utilities.SessionInfo;
import dtt.business.utilities.SystemInitializer;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Backing bean for the login page.
 *
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class LoginBacking implements Serializable {
    @Inject
    private SessionInfo sessionInfo;
    private UserDAO userDAO;
    private User user;



    /**
     * Initializes the login backing bean.
     */
    @PostConstruct
    public void init(){
        user = new User();
        userDAO = new UserDAO(); // Instantiate UserDAO

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

        try {
            // Retrieve user from the database using user id
            User userDB = new User();
            userDB.setId(user.getId()); // Set the user id for retrieval
            //the user to retrieve by id, here userDB will be fulled
            userDAO.getUserById(userDB, transaction);

            boolean verified;
            try {
                verified = Hashing.verifyPassword(user.getPassword(),userDB.getPasswordSalt(),userDB.getPasswordHashed());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }


            if (verified) {
                sessionInfo.setUser(userDB);

                // Password matches, login successful
                return "/view/authenticated/circulationslist?faces-redirect=true";
            } else {
                // Password does not match, show error message
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }
        } catch (DataNotFoundException e) {
            // User not found in the database, show error message
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        } finally {
            try {
                transaction.commit();
            } catch (SQLException e) {
                //TODO handle exception
            }
        }
    }





    /**
     * Go to register-page.
     *
     * @return Go to register-page.
     */
    public String register(){
        return "/view/authenticated/circulationslist?faces-redirect=true";
    }

    /**
     * Go to forget-password page.
     *
     * @return Go to forget-password page.
     */
    public String forgetPass(){
        return "/view/authenticated/forgetPass?faces-redirect=true";
    }


    public User getUser() {
        return user;
    }




}
