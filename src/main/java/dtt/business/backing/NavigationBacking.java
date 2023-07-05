package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.interfaces.FacultyDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.UserState;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
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
@SessionScoped
public class NavigationBacking implements Serializable {

    @Inject
    SessionInfo sessionInfo;
    static String selectedFaculty;
    private static final Logger LOGGER = LogManager.getLogger(NavigationBacking.class);
    private UserState currentUserState;
    @Inject
    FacultyDAO facultyDAO;
    @PostConstruct
    public void  init () {
        if(!sessionInfo.isAdmin ()) {
            selectedFaculty = getDefaultUserFaculty ();
            getUserStareOfCurrentFaculty (getFacultyByName (selectedFaculty));
            sessionInfo.setCurrentFaculty (getFacultyByName (selectedFaculty));
        }
        else{
            currentUserState = UserState.ADMIN;
        }
        sessionInfo.setCurrentUserState (currentUserState);
        sessionInfo.getUser ().setCurrentUserState (currentUserState);
    }

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
            getUserStareOfCurrentFaculty(selectedFacultyObj);
        }

    }

    public void setSelectedFaculty (String selectedFaculty) {
        this.selectedFaculty = selectedFaculty;
    }

    public String getSelectedFaculty () {
        return selectedFaculty;
    }

    public void setCurrentUserState (UserState currentUserState) {
        this.currentUserState = currentUserState;
    }

    public UserState getCurrentUserState () {
        return currentUserState;
    }
    private String getDefaultUserFaculty() {
        int maxPriority = -1;
        Faculty defaultFaculty = null;

        for (Map.Entry<Faculty, UserState> entry : sessionInfo.getUser ().getUserState ().entrySet()) {
            UserState userState = entry.getValue();
            if (userState.getPriority() > maxPriority) {
                maxPriority = userState.getPriority();
                defaultFaculty = entry.getKey();
            }
        }
        sessionInfo.setCurrentFaculty (defaultFaculty);

        return defaultFaculty.getName ();
    }
    private UserState getUserStareOfCurrentFaculty(Faculty faculty){
        Map<Faculty, UserState> userStateMap = sessionInfo.getUser().getUserState();
        UserState userState = userStateMap.get(faculty);
        sessionInfo.setCurrentUserState (userState);
        currentUserState =userState;

        return userState;
    }

    private Faculty getFacultyByName(String name) {
        Faculty faculty = new Faculty ();
        faculty.setName (name);

        try (Transaction transaction = new Transaction ()) {
            faculty = facultyDAO.getFacultyByName (faculty, transaction);
        }

        return faculty;
    }

}
