package dtt.dataAccess.repository.interfaces;

import java.util.List;

import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;

/**
 * This interface describes methods to handle database access related to users and user authorization.
 * 
 * @author Stefan Witka
 *
 */
public interface UserDAO {
	/**
	 * Insert a new user into the database.
	 * 
	 * <p>All properties of {@code user} have to be set except for {@code user.id} because the ID is automatically assigned. 
	 * The generated ID is set in the given {@code user} object. 
	 * The email address must be unique (i.e., not present in the database already).
	 * All entries in {@code user.userState} will be written into the Database.
     * 
     * If the user cannot be added to the database (due to invalid input or other error), an exception is thrown.
	 * 
	 * @param user The user DTO to be added to the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotCompleteException if necessary values of the {@code user} are not set
	 * @throws KeyExistsException if the email address of the {@code user} is already present in the Database
	 * @throws InvalidInputException if input data is faulty
	 */
	public void add(User user, Transaction transaction) throws DataNotCompleteException, KeyExistsException, InvalidInputException;
	
	/**
	 * Remove a user from the database.
	 * 
	 * <p> Only the {@code user.id} property of {@code user} needs to be set, 
	 * all other values will be ignored.
	 * If the unique Identifier was not found in the Database, an exception is thrown.
	 * 
	 * @param user The user DTO to be removed from the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code user} is not found in the Database
	 */
	public void remove(User user, Transaction transaction) throws DataNotFoundException;
	
	/**
	 * Update a user in the database.
	 * 
	 * <p>The {@code user.id} needs to be set correctly.
	 * All other User Properties will be overwritten unless set to {@code null}.
	 * The email address must be unique (i.e., not present in the database already).
	 * TODO All entries in {@code user.userState} will update or create the table entries with the key specified by the faculty field of the userState map and the ID of the user.
	 * TODO how to Delete userState entry
	 * 
	 * 
	 * @param user The user DTO to be updated in the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code user} is not found in the Database
	 * @throws InvalidInputException if input data is faulty
	 * @throws KeyExistsException if the email address of the {@code user} is already present in the Database
	 */
	public void update(User user, Transaction transaction) throws DataNotFoundException, InvalidInputException, KeyExistsException;
	
	/**
	 * Retrieve a user from the database.
	 * 
	 * <p> Only the {@code user.id} property of {@code user} needs to be set, 
	 * all other values will be overwritten when retrieving the User from the Database.
	 * If the unique Identifier was not found in the Database, an exception is thrown.
	 * 
	 * @param user The user DTO to be retrieved from the Database
	 * @param transaction The transaction associated with this operation.
	 * @throws DataNotFoundException if the {@code user} is not found in the Database
	 */
	public void getUserById(User user, Transaction transaction) throws DataNotFoundException;
	
	/**
	 * Check if a User with a given Email exists and retrieve it from the Database.
	 * 
	 * <p> Only the {@code user.email} property of {@code user} needs to be set, 
	 * all other values will be overwritten when retrieving the User from the Database.
	 * If the unique Email was not found in the Database, {@code false} is returned. 
	 * 
	 * @param user The user DTO with set email value to be retrieved from the Database
	 * @param transaction The transaction associated with this operation.
	 * @return {@code true} if user with that same email was found in the Database; {@code false} if no user with that email was found 
	 */
	public boolean findUserByEmail(User user, Transaction transaction);
	
	/**
	 * Retrieve a list of users from the database with pagination support.
	 * 	 
	 * <p> Values set in User, except for {@code user.id} and {@code user.userState} are used to filter results if set.
	 * {@code user.id} and {@code user.userState} are ignored in the filter object.
	 * To filter for a specified faculty, {@code faculty.id} needs to be set in the faculty DTO.
	 * 
     * 
	 * @param user The user DTO used as a filter for retrieving users.
	 * @param faculty can be {@code null}. Filter used for the Faculty. If {@code null}, no filter.
	 * @param auth can be {@code null}. Filter used for authorization level. If {@code null}, not filtered.
	 * @param transaction The transaction associated with this operation.
	 * @param offset The starting index of the user records to retrieve.
	 * @param count The maximum number of user records to retrieve.
	 * @return A list of Users matching the filter criteria, or an empty list if no matches were found.
	 */
	public List<User> getUsers(User user, Faculty faculty, UserState auth, Transaction transaction, int offset, int count);
	
	/**
	 * Retrieves a list of administrators
	 * 
	 * @param transaction The transaction associated with this operation.
	 * @return A list of administrators.
	 */
	public List<User> getAdmins(Transaction transaction);
	
	/**
	 * Get the Total Number of Users matching a given filter.
	 * 
	 * @param user The user DTO used as a filter.
	 * @param transaction The transaction associated with this operation.
	 * @return the total number of Users found matching the filter.
	 */
	public int getTotalUserNumber(User user, Transaction transaction);
}
