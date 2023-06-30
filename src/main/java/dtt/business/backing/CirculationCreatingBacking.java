package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.interfaces.CirculationDAO;
import dtt.dataAccess.repository.interfaces.FacultyDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.UserState;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Backing bean for new circulation page.
 *
 * @author Alaa Qasem
 */

@RequestScoped
@Named
public class CirculationCreatingBacking implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The DAO used to access the Database. */
    @Inject
    private CirculationDAO circulationDAO;
    /** The DAO used to access the Faculties. */
    @Inject
    private FacultyDAO facultyDAO;

    /** Information about current session. */
    @Inject
    private SessionInfo session;

    /** Inject faclet context. */
    @Inject
    private FacesContext fctx;

    /** Corculation to fill for the facelet. */
    private Circulation circulation;
    /** Item to store start date as LocalDate format. */
    private LocalDate startDate;
    /** Item to store end date as LocalDate format. */
    private LocalDate endDate;
    /** List of Faculties available to User. */
    private List<Faculty> facultyList;
    /** Selected Faculty. */
    private Faculty faculty;

    /** Initialize LOGGER. */
    private static final Logger LOGGER = LogManager
            .getLogger(CirculationCreatingBacking.class);

    /**
     * Initialize the dto object in bean.
     */
    @PostConstruct
    public void init() {
        circulation = new Circulation();
        if (session.isAdmin()) {
            facultyList = getListOfFacultiesFromDB();
        } else {
            facultyList = session.getUser().getUserState().entrySet().stream()
                    .filter(entry -> UserState.EXAMINCOMMITTEEMEMBERS
                            .equals(entry.getValue())
                            || UserState.DEANERY.equals(entry.getValue()))
                    .map(Map.Entry::getKey).collect(Collectors.toList());
        }
        setFaculty(facultyList.get(0));
        circulation.setCreatedBy(session.getUser().getId());
    }

    /**
     * Creates a new circulation.
     *
     * @return String containing the facelet name to direct to
     */
    public String create() {
        circulation.setStartDate(
                java.sql.Timestamp.valueOf(startDate.atStartOfDay()));
        circulation
                .setEndDate(java.sql.Timestamp.valueOf(endDate.atStartOfDay()));
        circulation.setFacultyId(faculty.getId());
        try (Transaction transaction = new Transaction()) {
            circulationDAO.add(circulation, transaction);
            transaction.commit();
            final FacesMessage fmsg = new FacesMessage(
                    "Circulation created successfully");
            fmsg.setSeverity(FacesMessage.SEVERITY_INFO);
            fctx.addMessage("createCirculation:create", fmsg);
            return null;
        } catch (InvalidInputException | DataNotCompleteException
                | KeyExistsException e) {
            LOGGER.error("Error adding the Circulation.", e);
            final FacesMessage fmsg = new FacesMessage(
                    "There was an Error creating the Circulation.");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("createCirculation:create", fmsg);
        }
        return null;
    }

    /**
     * Getter for circulation.
     *
     * @return the circulation
     */
    public Circulation getCirculation() {
        return circulation;
    }

    /**
     * Getter for start date. .
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Setter for start date.
     *
     * @param sDate the start date
     */
    public void setStartDate(final LocalDate sDate) {
        this.startDate = sDate;
    }

    /**
     * Getter for end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Setter for end date.
     *
     * @param eDate the end date
     */
    public void setEndDate(final LocalDate eDate) {
        this.endDate = eDate;
    }

    /**
     * Getter for faculty.
     *
     * @return the selected faculty
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * Setter for faculty.
     *
     * @param efaculty the selected faculty
     */
    public void setFaculty(final Faculty efaculty) {
        this.faculty = efaculty;
    }

    /**
     * Getter for facultyList.
     * @return the facultyList
     */
    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    private List<Faculty> getListOfFacultiesFromDB() {
        LOGGER.debug("getListOfFaculties() called.");
        try (Transaction transaction = new Transaction()) {
            return facultyDAO.getFaculties(transaction);
        }
    }
}
