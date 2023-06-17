package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.postgres.CirculationDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Backing bean for new circulation page.
 *
 * @author Alaa Qasem
 */

@RequestScoped
@Named
public class CirculationCreatingBacking implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The DAO used to access the Database.*/
    @Inject
    private CirculationDAO circulationDAO;
    /**Infirmation about current session.*/
    @Inject
    private SessionInfo session;

    /**Corculation to fill for the facelet.*/
    private Circulation circulation;
    /**Item to store start date as LocalDate format.*/
    private LocalDate startDate;
    /**Item to store end date as LocalDate format.*/
    private LocalDate endDate;

    /**
     * Initialize the dto object in bean.
     */
    @PostConstruct
    public void init() {
        circulation = new Circulation();
        circulation.setFacultyId(1);
        // circulation.setFacultyId(session.getFacultyId());
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
        try (Transaction transaction = new Transaction()) {
            circulationDAO.add(circulation, transaction);
            transaction.commit();
            return "/views/examineCommittee/createCirculation.xhtml";
        } catch (InvalidInputException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DataNotCompleteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
     * Getter for start date.
     *.
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
     * Getter for end date..
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
}
