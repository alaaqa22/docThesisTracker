package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * backing bean for navigation.
 * @author Hadi Abou Hassoun
 */
@RequestScoped
@Named
public class NavigationBacking {

    @Inject
    SessionInfo sessionInfo;

    /**
     * Logs out the current user.
     *
     * This method performs the necessary actions to log out the user,
     * such as clearing session information and performing any required cleanup.
     * After successful logout, the user will be redirected to the login page.
     */
     public void logout(){}


}
