package dtt.business.backing;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.interfaces.FacultyDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Backing bean for the faculty management page.
 *
 * @author Alaa Qasem
 */
@RequestScoped
@Named
public class FacultyBacking implements Serializable {
    /** The DAO used to access the Faculties. */
    @Inject
    private FacultyDAO facultyDAO;
    /** The Name of the new Faculty. */
    private String newFacultyName;
    /** The List  of Faculties. */
    private List<Faculty> faculties;

    /** Initialize LOGGER. */
    private static final Logger LOGGER = LogManager
            .getLogger(FacultyBacking.class);

    /** Inject faclet context. */
    @Inject
    private FacesContext fctx;

    /**
     * Initializes the dto object.
     */
    @PostConstruct
    public void init() {
        newFacultyName = "";
        try (Transaction transaction = new Transaction()) {
            faculties = facultyDAO.getFaculties(transaction);
            transaction.commit();
        }
    }

    /**
     * Adds a new Faculty to the system.
     */
    public void add() {
        newFacultyName.trim();
        if (newFacultyName.isEmpty()) {
            final FacesMessage fmsg = new FacesMessage(
                    "Please Enter a Name");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:newFaculty", fmsg);
            return;
        }
        Faculty newFaculty = new Faculty();
        newFaculty.setName(newFacultyName);
        try (Transaction transaction = new Transaction()) {
            facultyDAO.add(newFaculty, transaction);
            faculties = facultyDAO.getFaculties(transaction);
            transaction.commit();
            newFacultyName = "";
            final FacesMessage fmsg = new FacesMessage(
                    "Faculty created successfully");
            fmsg.setSeverity(FacesMessage.SEVERITY_INFO);
            fctx.addMessage("facultyList:newFaculty", fmsg);
        } catch (InvalidInputException e) {
            LOGGER.error("Error adding Faculty: Invalid input", e);
            final FacesMessage fmsg = new FacesMessage(
                    "Error adding Faculty: Invalid input");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:newFaculty", fmsg);
        } catch (DataNotCompleteException e) {
            LOGGER.error("Error adding Faculty: Incomplete Data", e);
            final FacesMessage fmsg = new FacesMessage(
                    "Error adding Faculty: Incomplete Data");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:newFaculty", fmsg);
        } catch (KeyExistsException e) {
            LOGGER.error("Error adding Faculty: Faculty name already exists",
                    e);
            final FacesMessage fmsg = new FacesMessage(
                    "Error adding Faculty: Faculty name already exists");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:newFaculty", fmsg);
        }

    }

    /**
     * Remove the faculty from the system.
     *
     * @param faculty The faculty to remove.
     */
    public void remove(final Faculty faculty) {
        try (Transaction transaction = new Transaction()) {
            facultyDAO.remove(faculty, transaction);
            faculties = facultyDAO.getFaculties(transaction);
            transaction.commit();
            final FacesMessage fmsg = new FacesMessage(
                    "Faculty " + faculty.getName() + " removed successfully");
            fmsg.setSeverity(FacesMessage.SEVERITY_INFO);
            fctx.addMessage("facultyList:global", fmsg);
        } catch (DataNotFoundException e) {
            LOGGER.error("Error removing Faculty: Faculty doesn't exist",
                    e);
            final FacesMessage fmsg = new FacesMessage(
                    "The Faculty doesn't appear to exist in the Database");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:global", fmsg);
        } catch (DataNotCompleteException e) {
            LOGGER.error("Error removing Faculty: Faculty ID not set",
                    e);
            final FacesMessage fmsg = new FacesMessage(
                    "Something went wrong.");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:global", fmsg);
        }
    }

    /**
     * Update a Faculty in the database and Table.
     * @param faculty he faculty to update
     */
    public void update(final Faculty faculty) {
        try (Transaction transaction = new Transaction()) {
            facultyDAO.update(faculty, transaction);
            faculties = facultyDAO.getFaculties(transaction);
            transaction.commit();
            final FacesMessage fmsg = new FacesMessage(
                    "Faculty " + faculty.getName() + " renamed successfully");
            fmsg.setSeverity(FacesMessage.SEVERITY_INFO);
            fctx.addMessage("facultyList:global", fmsg);
        } catch (DataNotFoundException e) {
            LOGGER.error("Error renaming Faculty: Faculty doesn't exist",
                    e);
            final FacesMessage fmsg = new FacesMessage(
                    "The Faculty doesn't appear to exist in the Database");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:global", fmsg);
        } catch (DataNotCompleteException e) {
            LOGGER.error("Error removing Faculty: Faculty ID or Name not set",
                    e);
            final FacesMessage fmsg = new FacesMessage(
                    "Something went wrong.");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:global", fmsg);
        } catch (KeyExistsException e) {
            LOGGER.error("Error removing Faculty: Faculty Name not unique",
                    e);
            final FacesMessage fmsg = new FacesMessage(
                    "The selected Name already exists.");
            fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage("facultyList:global", fmsg);
        }
    }

    /**
     * Getter for NewFacultyName.
     * @return the value of NewFacultyName
     */
    public String getNewFacultyName() {
        return newFacultyName;
    }

    /**
     * Setter for NewFacultyName.
     * @param newNewFacultyName the new value for NewFacultyName
     */
    public void setNewFacultyName(final String newNewFacultyName) {
        this.newFacultyName = newNewFacultyName;
    }

    /**
     * Getter for Faculties.
     * @return the list of faculties
     */
    public List<Faculty> getFaculties() {
        return faculties;
    }

    /**
     * Setter for Faculties.
     * @param newFfaculties list of Faculties.
     */
    public void setFaculties(final List<Faculty> newFfaculties) {
        this.faculties = newFfaculties;
    }
}
