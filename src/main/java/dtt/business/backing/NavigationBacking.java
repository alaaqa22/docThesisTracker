package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.UserState;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Map;

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
    private UserState selectedUserState;
    private static final Logger LOGGER = LogManager.getLogger(NavigationBacking.class);

    /**
     * Logs out the current user.
     * <p>
     * This method performs the necessary actions to log out the user,
     * such as clearing session information and performing any required cleanup.
     * After successful logout, the user will be redirected to the login page.
     */
    public String logout() {
        LOGGER.debug("logout() called for user: " + sessionInfo.getUser().getId() + "logged out.");
        sessionInfo.setUser(null);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();

        return "/views/anonymous/login?faces-redirect=true";

    }

    public void setSessionInfo (SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public void setSelectedUserState (UserState selectedUserState) {
        this.selectedUserState = selectedUserState;
    }

    public SessionInfo getSessionInfo () {
        return sessionInfo;
    }

    public UserState getSelectedUserState () {
        return selectedUserState;
    }
    public void changeUserState() {

        UserState selectedState = selectedUserState;

        if (sessionInfo.getUser () != null) {
            Map<Faculty, UserState> userStateMap = sessionInfo.getUser ().getUserState();
            Faculty selectedFaculty = null;

            for (Map.Entry<Faculty, UserState> entry : userStateMap.entrySet()) {
                if (entry.getValue() == selectedState) {
                    selectedFaculty = entry.getKey();
                    break;
                }
            }

            // Wenn eine passende Fakultät gefunden wurde, ändern Sie den Benutzerstatus für diese Fakultät
            if (selectedFaculty != null) {
                userStateMap.put(selectedFaculty, selectedState); // setzt den neuen Benutzerstatus
            }
        }
    }

}
