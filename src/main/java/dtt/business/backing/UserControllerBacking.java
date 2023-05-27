package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.global.tansport.User;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * @author Hadi Abou Hassoun
 */
public class UserControllerBacking {
    private User user;
    @Inject
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
    @ViewAction
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
