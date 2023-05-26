package dtt.dataAccess.repository.interfaces;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;

/**
 * @author Stefan Witka
 *
 */
public interface UserDAO {
	/**
	 * Insert a new user into the database.
	 * 
	 * <p>All properties of {@code user} have to be set except for {@code user.id}
     * because the ID is automatically assigned. The generated ID
     * is set in the given {@code user} object. The email address must be
     * unique (i.e., not present in the database already). If the user
     * cannot be added to the database (due to invalid input or other error),
     * an exception is thrown.
	 * 
	 * @param user The user DTO to be added to the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code user} are not set
	 * @throws EmailAddressExistsException if the email address of the {@code user} is already present in the Database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the user record cannot be written to the database
	 */
	public void add(User user, Transaction transaction) throws DataNotCompleteException, InvalidInputException;
	
	/**
	 * Remove a user from the database.
	 * 
	 * @param user The user DTO to be removed from the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code user} is not found in the Database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the user record cannot be removed from the database
	 */
	public void remove(User user, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Update a user in the database.
	 * 
	 * @param user The user DTO to be updated in the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code user} is not found in the Database
	 * @throws InvalidInputException if input data is faulty
	 * @throws DataNotWrittenException if the user record cannot be updated in the database
	 */
	public void update(User user, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Retrieve a user from the database.
	 * 
	 * @param user The user DTO to be retrieved from the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code user} is not found in the Database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void getUserById(User user, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Retrieve a user from the database.
	 * 
	 * @param user The user DTO to be retrieved from the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code user} is not found in the Database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void getUserByEmail(User user, Transaction transaction) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Retrieve a list of users from the database with pagination support.
	 * 
	 * @param user The user DTO used as a filter for retrieving users
	 * @param transaction The transaction associated with this operation.
	 * @param offset The starting index of the user records to retrieve
	 * @param count The maximum number of user records to retrieve
	 * @throws DataNotFoundException if no users matching the criteria are found in the Database
	 * @throws InvalidInputException if input data is faulty
	 */
	public List<User> getUsers(User user, Transaction transaction, int offset, int count) throws DataNotFoundException, InvalidInputException;
	
	/**
	 * Get the Total Number of Users matching a given filter.
	 * 
	 * @param circulation
	 * @param transaction
	 * @return
	 * @throws InvalidInputException
	 */
	public int getTotalUserNumber(User user, Transaction transaction) throws InvalidInputException;
}
