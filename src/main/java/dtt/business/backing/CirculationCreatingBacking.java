package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.exceptions.DBConnectionFailedException;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.repository.Postgres.CirculationDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Faculty;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Backing bean for new circulation page.
 * @author Alaa Qasem
 */

@RequestScoped
@Named
public class CirculationCreatingBacking implements Serializable {
    private static final long serialVersionUID = 1L;

	@Inject
    private CirculationDAO circulationDAO;
	@Inject
	private SessionInfo session;

    private Circulation circulation;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Initialize the dto object in bean.
     */
    @PostConstruct
    public void init() {
    	circulation = new Circulation();
    	//TODO Set Faculty of circulation, or set possible faculties to choose from
    	circulation.setFacultyId(1);//TODO temporary. needs change circulation.setFacultyId(session.getFacultyId());
    	circulation.setCreatedBy(session.getUser().getId());
//    	startDate = LocalDate.now();
//    	endDate = LocalDate.now();
    }


    /**
     * Creates a new circulation
     *
     * @param circ The circulation to create.
     */
    public String create(){
    	circulation.setStartDate(java.sql.Timestamp.valueOf(startDate.atStartOfDay()));
    	circulation.setEndDate(java.sql.Timestamp.valueOf(endDate.atStartOfDay()));
    	try(Transaction transaction = new Transaction()){
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

    public Circulation getCirculation() {
        return circulation;
    }


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
