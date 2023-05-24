package dtt.dataAccess.repository.interfaces;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.DataNotWrittenException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;

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
	public void add(Circulation circulation, Transaction transaction) throws DataNotCompleteException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Remove a circulation record from the database.
	 * 
	 * @param circulation The circulation record to be removed from the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the circulation record cannot be removed from the database
	 */
	public void remove(Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
	/**
	 * Update a circulation record in the database.
	 * 
	 * @param circulation The circulation record to be updated in the database.
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code circulation} record is not found in the database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the circulation record cannot be updated in the database
	 */
	public void update(Circulation circulation, Transaction transaction) throws DataNotFoundException, InvalidInputException, DataNotWrittenException;
	
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
	 * @throws DataNotFoundException if no circulation records matching the criteria are found in the database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void getCirculations(Circulation circulation, Transaction transaction, int offset, int count) throws DataNotFoundException, InvalidInputException;
}
