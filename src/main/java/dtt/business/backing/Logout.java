package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@RequestScoped
public class Logout {
    @Inject
    SessionInfo sessionInfo;
    private static final Logger LOGGER = LogManager.getLogger (NavigationBacking.class);

    /**
     * Logs out the current user.
     * <p>
     * This method performs the necessary actions to log out the user,
     * such as clearing session information and performing any required cleanup.
     * After successful logout, the user will be redirected to the login page.
     */
    public String logout () {
        LOGGER.debug ("logout() called for user: " + sessionInfo.getUser ().getId () + "logged out.");
        sessionInfo.setUser (null);
        ExternalContext externalContext = FacesContext.getCurrentInstance ().getExternalContext ();
        externalContext.invalidateSession ();

        return "/views/anonymous/login?faces-redirect=true";

    }
}
