package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.DataNotWrittenException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Verification;

/**
 * @author Stefan Witka
 *
 */
public interface VerificationDAO {
	/**
	 * Add a new verification record to the database.
	 * 
	 * @param verification The verification record to be added to the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code verification} are not set
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the verification record cannot be written to the database
	 */
	public void add(Verification verification, Transaction transaction) throws DataNotCompleteException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Remove a verification record from the database.
	 * 
	 * @param verification The verification record to be removed from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code verification} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the verification record cannot be removed from the database
	 */
	public void remove(Verification verification, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Update a verification record in the database.
	 * 
	 * @param verification The verification record to be updated in the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code verification} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the verification record cannot be updated in the database
	 */
	public void update(Verification verification, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Retrieve a verification record from the database.
	 * 
	 * @param verification The verification record to be retrieved from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code verification} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void getVerification(Verification verification, Transaction transaction) throws DataNotFoundException, InvalidInputException;
}
