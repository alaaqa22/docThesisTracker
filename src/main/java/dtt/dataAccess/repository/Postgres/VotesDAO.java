package dtt.dataAccess.repository.Postgres;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Vote;

/**
 * @author Stefan Witka
 *
 */
public class VotesDAO implements dtt.dataAccess.repository.interfaces.VotesDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Vote vote, Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Vote vote, Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Vote vote, Circulation circulation, Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @return 
	 */
	@Override
	public List<Vote> getVotes(Vote vote, Circulation circulation, Transaction transaction) {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean findVote(Vote vote, Circulation circulation, Transaction transaction)
			throws DataNotFoundException, InvalidInputException {
		// TODO Auto-generated method stub
		return false;
	}

}
