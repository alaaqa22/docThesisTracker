package dtt.dataAccess.repository.Postgres;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;

/**
 * @author Stefan Witka
 *
 */
public class CirculationDAO implements dtt.dataAccess.repository.interfaces.CirculationDAO {

	/**
	 * COnstructor for CirculationDAO
	 */
	public CirculationDAO() {
		
	}

	@Override
	public void add(Circulation circulation, Transaction transaction)
			throws DataNotCompleteException, InvalidInputException, KeyExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Circulation circulation, Transaction transaction) throws DataNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Circulation circulation, Transaction transaction)
			throws DataNotFoundException, InvalidInputException, KeyExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCirculationById(Circulation circulation, Transaction transaction) throws DataNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Circulation> getCirculations(Circulation circulation, Transaction transaction, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCirculationNumber(Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		return 0;
	}
}
