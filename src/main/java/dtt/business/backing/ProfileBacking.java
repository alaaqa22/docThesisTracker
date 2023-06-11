package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.UserDAO;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Backing bean for the profile page.
 * @author Alaa Qasem
 */
@ViewScoped
@Named
public class ProfileBacking implements Serializable {
    private User user;
    @Inject
    private UserDAO userDAO;
    @Inject
    private SessionInfo sessionInfo;


    /**
     * Initializes the dto objects.
     */
    @PostConstruct
    private void init(){

    }

    /**
     * Load the user's information.
     */
    private void load(){

    }

    /**
     * Save the updated data to the user profile.
     */
    private void save(){

    }

    public User getUser() {
        return user;
    }

    /**
     * Method to delete the profile.
     */
    public void deleteProfile(){

    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }



}
