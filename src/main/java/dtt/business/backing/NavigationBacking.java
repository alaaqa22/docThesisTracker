package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * backing bean for navigation.
 *
 * @author Hadi Abou Hassoun
 */
@RequestScoped
@Named
public class NavigationBacking implements Serializable {

    @Inject
    SessionInfo sessionInfo;
    Logger logger = LogManager.getLogger();

    /**
     * Logs out the current user.
     * <p>
     * This method performs the necessary actions to log out the user,
     * such as clearing session information and performing any required cleanup.
     * After successful logout, the user will be redirected to the login page.
     */
    public String logout() {
        logger.info("User: " + sessionInfo.getUser().getId() + "logged out.");
        sessionInfo.setUser(null);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();

        return "/views/anonymous/login?faces-redirect=true";

    }


}
