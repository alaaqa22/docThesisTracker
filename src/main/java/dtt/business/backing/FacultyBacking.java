package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.Postgres.FacultyDAO;
import dtt.global.tansport.Faculty;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Backing bean for the faculty management page.
 *
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class FacultyBacking {
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
     * @param faculty The FacultyDTO object containing the data for creating a new Faculty.
     */
    public void add(Faculty faculty) {
    }

    /**
     * Remove the faculty from the system based on the provided ID.
     *
     * @param facultyId The ID of the faculty to remove.
     */
    public void remove(Integer facultyId) {

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

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public FacultyDAO getFacultyDAO() {
        return facultyDAO;
    }

    public void setFacultyDAO(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }
}
