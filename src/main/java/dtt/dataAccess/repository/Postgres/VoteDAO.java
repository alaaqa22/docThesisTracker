package dtt.dataAccess.repository.Postgres;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Vote;

/**
 * @author Stefan Witka
 *
 */
public class VoteDAO implements dtt.dataAccess.repository.interfaces.VoteDAO {

	/**
	 * Constructor for VotesDAO
	 */
	public VoteDAO() {
		
	}

	@Override
	public void add(Vote vote, Transaction transaction) throws DataNotCompleteException, InvalidInputException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Vote vote, Transaction transaction) throws DataNotFoundException, InvalidInputException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Vote vote, Transaction transaction) throws DataNotFoundException, InvalidInputException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vote> getVotes(Vote vote, Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findVote(Vote vote, Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}

}
