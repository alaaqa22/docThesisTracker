package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.UserState;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
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

@Named
@RequestScoped
public class NavigationBacking implements Serializable {

    @Inject
    SessionInfo sessionInfo;
    static String selectedFaculty;
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

    public void setSelectedUserState (String selectedfaculty) {
        this.selectedFaculty = selectedfaculty;
    }

    public SessionInfo getSessionInfo () {
        return sessionInfo;
    }

    public void changeUserState() {

        Map<Faculty, UserState> userStateMap = sessionInfo.getUser().getUserState();
        Faculty selectedFacultyObj = null;

        for (Faculty faculty : userStateMap.keySet()) {
            if (faculty.getName().equals(selectedFaculty)) {
                selectedFacultyObj = faculty;
                break;
            }
        }

        if (selectedFacultyObj != null) {
            UserState userState = userStateMap.get(selectedFacultyObj);
            sessionInfo.getUser ().setCurrentUserState (userState);

        }

        }

    public void setSelectedFaculty (String selectedFaculty) {
        this.selectedFaculty = selectedFaculty;
    }

    public String getSelectedFaculty () {
        return selectedFaculty;
    }
}
