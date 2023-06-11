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

    /**
     * Initialize the dto object in bean.
     */
    @PostConstruct
    public void init() {
    	circulation = new Circulation();
    	//TODO Set Faculty of circulation, or set possible faculties to choose from
    	circulation.setFacultyId(1);//TODO temporary. needs change
    	circulation.setCreatedBy(session.getUser().getId());
    }


    /**
     * Creates a new circulation
     *
     * @param circ The circulation to create.
     */
    public void create(){
    	try(Transaction transaction = new Transaction()){
    		circulationDAO.add(circulation, transaction);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataNotCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBConnectionFailedException("Transaction failed to close", e);
		}
    }

    public Circulation getCirculation() {
        return circulation;
    }
}
