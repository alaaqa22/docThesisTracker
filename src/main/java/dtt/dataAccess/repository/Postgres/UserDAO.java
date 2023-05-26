package dtt.dataAccess.repository.Postgres;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;

/**
 * 
 * @author Stefan Witka
 *
 */
public class UserDAO implements dtt.dataAccess.repository.interfaces.UserDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(User user, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(User user, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(User user, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getUserById(User user, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsers(User user, Transaction transaction, int offset, int count) {
		return null;
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getUserByEmail(User user, Transaction transaction) throws DataNotFoundException, InvalidInputException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTotalUserNumber(User user, Transaction transaction) throws InvalidInputException {
		// TODO Auto-generated method stub
		return 0;
	}

}
