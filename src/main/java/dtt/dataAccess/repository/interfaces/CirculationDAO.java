package dtt.dataAccess.repository.interfaces;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;

/**
 * This interface describes methods to handle database access related to circulations.
 * 
 * @author Stefan Witka
 *
 */
public interface CirculationDAO {
	
	/**
	 * Add a new circulation record to the database.
	 * 
	 * <p> All properties of {@code circulation} have to be set except for {@code circulation.id} because the ID is automatically assigned. 
	 * The generated ID is set in the given {@code circulation} object. 
	 * The title must be unique (i.e., not present in the database already).
     * 
     * <p>If the user cannot be added to the database (due to invalid input or other error), an exception is thrown.
	 * 
	 * @param circulation The circulation record to be added to the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code circulation} are not set
	 * @throws InvalidInputException if input data is faulty
	 * @throws KeyExistsException if the circulation title is not unique
	 */
	public void add(Circulation circulation, Transaction transaction) throws DataNotCompleteException, InvalidInputException, KeyExistsException;
	
	/**
	 * Remove a circulation record from the database.
	 * 
	 * <p> Only the {@code circulation.id} property of {@code circulation} needs to be set, 
	 * all other values will be ignored.
	 * 
	 * <p> If the unique Identifier was not found in the Database, an exception is thrown.
	 * 
	 * @param circulation The circulation record to be removed from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 */
	public void remove(Circulation circulation, Transaction transaction) throws DataNotFoundException;
	
	/**
	 * Update a circulation record in the database.
	 * 
	 * <p>The {@code circulation.id} needs to be set correctly.
	 * All other Circulation Properties will be overwritten unless set to {@code null}.
	 * The title must be unique (i.e., not present in the database already).

	 * @param circulation The circulation record to be updated in the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws KeyExistsException if the email address of the {@code user} is already present in the Database
	 */
	public void update(Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException, KeyExistsException;
	
	/**
	 * Retrieve a circulation record from the database.
	 * 
	 * <p> Only the {@code circulation.id} property of {@code circulation} needs to be set, 
	 * all other values will be overwritten when retrieving the Circulation from the Database.
	 * If the unique Identifier was not found in the Database, an exception is thrown.
	 * 
	 * @param circulation The circulation record to be retrieved from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 */
	public void getCirculationById(Circulation circulation, Transaction transaction) throws DataNotFoundException;
	
	/**
	 * Retrieve a list of circulation records from the database with pagination support.
	 * 
	 * <p> Values set in Circulation, except for {@code circulation.id} and {@code circulation.attachmentslist} are used to filter results if set.
	 * {@code circulation.id} and {@code circulation.attachmentslist} are ignored in the filter object.
	 * 
	 * @param circulation The circulation record used as a filter for retrieving records.
	 * @param transaction The transaction associated with this operation.
	 * @param offset The starting index of the circulation records to retrieve.
	 * @param count The maximum number of circulation records to retrieve.
	 * @return A list of Users matching the filter criteria, or an empty list if no matches were found.
	 */
	public List<Circulation> getCirculations(Circulation circulation, Transaction transaction, int offset, int count);
	
	/**
	 * Get the Total Number of Circulations matching a given filter.
	 * 
	 * @param circulation The circulation record used as a filter for retrieving records.
	 * @param transaction The transaction associated with this operation.
	 * @return the total number of Users found matching the filter.
	 */
	public int getTotalCirculationNumber(Circulation circulation, Transaction transaction);
}
