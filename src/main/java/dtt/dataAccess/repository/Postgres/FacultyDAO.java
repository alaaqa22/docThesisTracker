package dtt.dataAccess.repository.Postgres;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;

/**
 * @author Stefan Witka
 *
 */
public class FacultyDAO implements dtt.dataAccess.repository.interfaces.FacultyDAO {

	/**
	 * Constructor for FacultyDAO
	 */
	public FacultyDAO() {
		
	}

	@Override
	public void add(Faculty faculty, Transaction transaction) throws DataNotCompleteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Faculty faculty, Transaction transaction)
			throws DataNotFoundException, DataNotCompleteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Faculty faculty, Transaction transaction)
			throws DataNotFoundException, DataNotCompleteException, KeyExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Faculty> getFaculties(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}


}
