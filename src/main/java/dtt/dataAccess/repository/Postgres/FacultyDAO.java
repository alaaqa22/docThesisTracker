package dtt.dataAccess.repository.Postgres;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Faculty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * A Postgres implementation for a class handling database access related to faculties.
 * 
 * @author Stefan Witka
 *
 */
@Named
@ApplicationScoped
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

	@Override
	public boolean findFacultyByName(Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}


}
