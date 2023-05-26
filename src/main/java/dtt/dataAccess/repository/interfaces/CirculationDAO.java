package dtt.dataAccess.repository.interfaces;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;

/**
 * @author Stefan Witka
 *
 */
public interface CirculationDAO {
	
	/**
	 * Add a new circulation record to the database.
	 * 
	 * @param circulation The circulation record to be added to the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code circulation} are not set
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the circulation record cannot be written to the database
	 */
	public void add(Circulation circulation, Transaction transaction) throws DataNotCompleteException, InvalidInputException;
	
	/**
	 * Remove a circulation record from the database.
	 * 
	 * @param circulation The circulation record to be removed from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the circulation record cannot be removed from the database
	 */
	public void remove(Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Update a circulation record in the database.
	 * 
	 * @param circulation The circulation record to be updated in the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the circulation record cannot be updated in the database
	 */
	public void update(Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Retrieve a circulation record from the database.
	 * 
	 * @param circulation The circulation record to be retrieved from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void getCirculation(Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Retrieve a list of circulation records from the database with pagination support.
	 * 
	 * @param circulation The circulation record used as a filter for retrieving records.
	 * @param transaction The transaction associated with this operation.
	 * @param offset The starting index of the circulation records to retrieve.
	 * @param count The maximum number of circulation records to retrieve.
	 * @return 
	 * @throws DataNotFoundException if no circulation records matching the criteria are found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public List<Circulation> getCirculations(Circulation circulation, Transaction transaction, int offset, int count) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Get the Total Number of Circulations matching a given filter.
	 * 
	 * @param circulation
	 * @param transaction
	 * @return
	 * @throws InvalidInputException
	 */
	public int getTotalCirculationNumber(Circulation circulation, Transaction transaction) throws InvalidInputException;
}
