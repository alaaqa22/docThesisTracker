package dtt.dataAccess.repository.interfaces;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
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
	 * <p>{@code faculty.name} has to be set, {@code user.id} doesn't because the ID is automatically assigned. 
	 * The generated ID is set in the given {@code faculty} object. 
	 * The faculty name must be unique (i.e., not present in the database already).
	 * An Error is thrown 
	 * 
	 * @param faculty The faculty object to be added to the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code faculty} are not set
	 */
	public void add(Faculty faculty, Transaction transaction) throws DataNotCompleteException;
	
	/**
	 * Remove a faculty from the database.
	 * 
	 * <p> The {@code faculty.id} needs to be set. 
	 * The corresponding faculty will be removed.
	 * 
	 * @param faculty The faculty object to be removed from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code faculty} is not found in the database
	 * @throws DataNotCompleteException if necessary values of the {@code faculty} are not set
	 */
	public void remove(Faculty faculty, Transaction transaction) throws DataNotFoundException, DataNotCompleteException;
	
	/**
	 * Update a faculty in the database.
	 * 
	 * <p> The {@code faculty.id} and {@code faculty.name} need to be set. 
	 * The faculty with the corresponding ID will be renamed to the new name given in {@code faculyt.name}.
	 * 
	 * @param faculty The faculty object to be updated in the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code faculty} is not found in the database
	 * @throws DataNotCompleteException if necessary values of the {@code faculty} are not set
	 * @throws KeyExistsException if the faculty name already exists
	 */
	public void update(Faculty faculty, Transaction transaction) throws DataNotFoundException, DataNotCompleteException, KeyExistsException;
	
	/**
	 * Retrieve a full list of faculties from the database
	 * 
	 * 
	 * @param faculty The faculty object used as a filter for retrieving faculties.
	 * @param transaction The transaction associated with this operation.
	 * @param offset The starting index of the faculty records to retrieve.
	 * @param count The maximum number of faculty records to retrieve.
	 * @throws InvalidInputException if input data is faulty
	 */
	public List<Faculty> getFaculties(Transaction transaction);
}

