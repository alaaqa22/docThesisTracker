package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.FacultyDAO;
import dtt.global.tansport.Faculty;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Backing bean for the faculty management page.
 *
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class FacultyBacking implements Serializable {
    @Inject
    private SessionInfo sessionInfo;
    private Faculty faculty;
    @Inject
    private FacultyDAO facultyDAO;

    /**
     * Initializes the dto object.
     */
    @PostConstruct
    public void init(){

    }
    /**
     * Adds a new Faculty to the system.
     *
     * @param newFaculty The FacultyDTO object containing the data for creating a new Faculty.
     */
    public void add(Faculty newFaculty) {

    }


    /**
     * Remove the faculty from the system.
     *
     * @param faculty The faculty to remove.
     */
    public void remove(Faculty faculty) {

    }


    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public Faculty getFaculty() {
        return faculty;
    }


}
