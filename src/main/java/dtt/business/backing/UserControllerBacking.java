package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author Hadi Abou Hassoun
 */
@RequestScoped
@Named
public class UserControllerBacking {
    private User user;

    private UserDAO userDAO;
    @Inject
    private SessionInfo sessionInfo;

    /**
     * Initialize the UserDTO.
     */
    @PostConstruct
    public void init(){
    }

    /**
      * Loads the user data
     */
    public void loadUser(){}

    /**
     * Aborts the current operation.
     */
    public void abort(){}

    /**
     *  Saves the user data.
     */
    public void save(){}

    /**
     * Sets the account state of users.
     */
    public void setAccountStateOfUsers(){}

}
