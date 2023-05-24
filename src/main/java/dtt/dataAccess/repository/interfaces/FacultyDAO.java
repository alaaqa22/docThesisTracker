package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.DataNotWrittenException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;

/**
 * @author Stefan Witka
 *
 */
public interface FacultyDAO {
	/**
	 * Add a new faculty to the database.
	 * 
	 * @param faculty The faculty object to be added to the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code faculty} are not set
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the faculty object cannot be written to the database
	 */
	public void add(Faculty faculty, Transaction transaction) throws DataNotCompleteException, InvalidInputException , DataNotWrittenException;
	
	/**
	 * Remove a faculty from the database.
	 * 
	 * @param faculty The faculty object to be removed from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code faculty} is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the faculty object cannot be removed from the database
	 */
	public void remove(Faculty faculty, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Update a faculty in the database.
	 * 
	 * @param faculty The faculty object to be updated in the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code faculty} is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the faculty object cannot be updated in the database
	 */
	public void update(Faculty faculty, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Retrieve a list of faculties from the database with pagination support.
	 * 
	 * @param faculty The faculty object used as a filter for retrieving faculties.
	 * @param transaction The transaction associated with this operation.
	 * @param offset The starting index of the faculty records to retrieve.
	 * @param count The maximum number of faculty records to retrieve.
	 * @throws DataNotFoundException if no faculties matching the criteria are found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void getFaculties(Faculty faculty, Transaction transaction, int offset, int count) throws DataNotFoundException, InvalidInputException;
}

