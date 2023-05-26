package dtt.dataAccess.repository.Postgres;

import java.util.List;

import dtt.dataAccess.exceptions.InvalidInputException;
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getCirculation(Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Circulation> getCirculations(Circulation circulation, Transaction transaction, int offset, int count) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalCirculationNumber(Circulation circulation, Transaction transaction)
			throws InvalidInputException {
		// TODO Auto-generated method stub
		return 0;
	}

}
