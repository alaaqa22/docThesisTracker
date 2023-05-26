package dtt.dataAccess.repository.interfaces;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Vote;

/**
 * This interface describes methods to handle database access related to votes.
 * 
 * @author Stefan Witka
 *
 */
public interface VoteDAO {
	/**
	 * Add a new vote to the database for a specific circulation.
	 * 
	 * <p> {@code vote.user.id} and {@code vote.circulation.id} need to be set, as well as the {@code vote.selection} and optionally the TODO reason.
	 * 
	 * @param vote The vote to be added to the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code vote} are not set
	 * @throws InvalidInputException if input data is faulty
	 */
	public void add(Vote vote, Transaction transaction) throws DataNotCompleteException, InvalidInputException;
	
	/**
	 * Remove a vote from the database for a specific circulation.
	 * 
	 * <p> {@code vote.user.id} and {@code vote.circulation.id} need to be set to identify the vote.
	 * 
	 * @param vote The vote to be removed from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code vote} is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void remove(Vote vote, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Update a vote in the database for a specific circulation.
	 * 
	 * <p> {@code vote.user.id} and {@code vote.circulation.id} need to be set and existing in the database.
	 * The {@code vote.selection} and the TODO reason need to be set. If the reason is set to null, the value for the reason will be set empty.
	 * 
	 * @param vote The vote to be updated in the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code vote} is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void update(Vote vote, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Retrieve votes for a specific circulation from the database.
	 * 
	 * <p> {@code vote.circulation.id} needs to be set to specify the circulation.
	 * 
	 * 
	 * @param vote The vote object used to specify the circulation.
	 * @param transaction The transaction associated with this operation.
	 * @return List of Votes belonging to the specified circulation. List can be empty if circulation doesn't exist or has no votes yet.
	 */
	public List<Vote> getVotes(Vote vote, Transaction transaction);
	
	/**
	 * Retrieve a specific Vote for a specific user-circulation pair.
	 * 
	 * <p> {@code vote.user.id} and {@code vote.circulation.id} need to be set.
	 * If the user-circulation pair doesn't exist, {@code false} is returned.
	 * Else the parameters of {@code vote} will be filled and {@code true} returned
	 * 
	 * @param vote The vote to be found in the database.
	 * @param transaction The transaction associated with this operation.
	 * @return {@code true} if the data was found; {@code false} if no entry was found in the database.
	 */
	public boolean findVote(Vote vote, Transaction transaction);
}
