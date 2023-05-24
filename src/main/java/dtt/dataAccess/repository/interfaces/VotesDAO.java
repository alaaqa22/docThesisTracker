package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.DataNotWrittenException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import dtt.global.tansport.Vote;

/**
 * @author Stefan Witka
 *
 */
public interface VotesDAO {
	/**
	 * Add a new vote to the database for a specific circulation.
	 * 
	 * @param vote The vote to be added to the database.
	 * @param circulation The circulation associated with the vote.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code vote} are not set
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the vote cannot be written to the database
	 */
	public void add(Vote vote, Circulation circulation, Transaction transaction) throws DataNotCompleteException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Remove a vote from the database for a specific circulation.
	 * 
	 * @param vote The vote to be removed from the database.
	 * @param circulation The circulation associated with the vote.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code vote} is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the vote cannot be removed from the database
	 */
	public void remove(Vote vote, Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Update a vote in the database for a specific circulation.
	 * 
	 * @param vote The vote to be updated in the database.
	 * @param circulation The circulation associated with the vote.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code vote} is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the vote cannot be updated in the database
	 */
	public void update(Vote vote, Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Retrieve votes for a specific circulation from the database.
	 * 
	 * @param vote The vote object used as a filter for retrieving votes.
	 * @param circulation The circulation associated with the votes.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if no votes matching the criteria are found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void getVotes(Vote vote, Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException;
}
