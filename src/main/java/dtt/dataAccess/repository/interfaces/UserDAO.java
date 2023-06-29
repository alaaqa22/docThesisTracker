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
 * This interface describes methods to handle database access related to users
 * and user authorization.
 *
 * @author Stefan Witka
 *
 */
public interface UserDAO {
    /**
     * Insert a new user into the database.
     *
     * <p>
     * All properties of {@code user} have to be set except for {@code user.id}
     * because the ID is automatically assigned. The generated ID is set in the
     * given {@code user} object. The email address must be unique (i.e., not
     * present in the database already). All entries in {@code user.userState}
     * will be written into the Database.
     *
     * If the user cannot be added to the database (due to invalid input or
     * other error), an exception is thrown.
     *
     * @param user        The user DTO to be added to the Database
     * @param transaction The transaction associated with this operation.
     * @throws DataNotCompleteException if necessary values of the {@code user}
     *                                  are not set
     * @throws KeyExistsException       if the email address of the {@code user}
     *                                  is already present in the Database
     * @throws InvalidInputException    if input data is faulty
     */
    void add(User user, Transaction transaction)
            throws DataNotCompleteException, KeyExistsException,
            InvalidInputException;

    /**
     * Remove a user from the database.
     *
     * <p>
     * Only the {@code user.id} property of {@code user} needs to be set, all
     * other values will be ignored. If the unique Identifier was not found in
     * the Database, an exception is thrown.
     *
     * @param user        The user DTO to be removed from the Database
     * @param transaction The transaction associated with this operation.
     * @throws DataNotFoundException if the {@code user} is not found in the
     *                               Database
     */
    void remove(User user, Transaction transaction)
            throws DataNotFoundException;

    /**
     * Update a user in the database.
     *
     * <p>
     * The {@code user.id} needs to be set correctly. All other User Properties
     * will be overwritten unless set to {@code null}. The email address must be
     * unique (i.e., not present in the database already). All entries in
     * {@code user.userState} will update or create the table entries with the
     * key specified by the faculty field of the userState map and the ID of the
     * user.
     *
     * @param user        The user DTO to be updated in the Database
     * @param transaction The transaction associated with this operation.
     * @throws DataNotFoundException if the {@code user} is not found in the
     *                               Database
     * @throws InvalidInputException if input data is faulty
     * @throws KeyExistsException    if the email address of the {@code user} is
     *                               already present in the Database
     */
    void update(User user, Transaction transaction)
            throws DataNotFoundException, InvalidInputException,
            KeyExistsException;

    /**
     * Retrieve a user from the database.
     *
     * <p>
     * Only the {@code user.id} property of {@code user} needs to be set, all
     * other values will be overwritten when retrieving the User from the
     * Database. If the unique Identifier was not found in the Database, an
     * exception is thrown.
     *
     * @param user        The user DTO to be retrieved from the Database
     * @param transaction The transaction associated with this operation.
     * @throws DataNotFoundException if the {@code user} is not found in the
     *                               Database
     */
    void getUserById(User user, Transaction transaction)
            throws DataNotFoundException;

    /**
     * Check if a User with a given Email exists and retrieve it from the
     * Database.
     *
     * <p>
     * Only the {@code user.email} property of {@code user} needs to be set, all
     * other values will be overwritten when retrieving the User from the
     * Database. If the unique Email was not found in the Database,
     * {@code false} is returned.
     *
     * @param user        The user DTO with set email value to be retrieved from
     *                    the Database
     * @param transaction The transaction associated with this operation.
     * @return {@code true} if user with that same email was found in the
     *         Database; {@code false} if no user with that email was found
     */
    boolean findUserByEmail(User user, Transaction transaction);

    /**
     * Retrieve a list of users from the database with pagination support.
     *
     * <p>
     * Values set in User, except for {@code user.id} and {@code user.userState}
     * are used to filter results if set. {@code user.id} and
     * {@code user.userState} are ignored in the filter object. To filter for a
     * specified faculty, {@code faculty.id} needs to be set in the faculty DTO.
     *
     * @param user        The user DTO used as a filter for retrieving users.
     * @param faculty     can be {@code null}. Filter used for the Faculty. If
     *                    {@code null}, no filter.
     * @param auth        can be {@code null}. Filter used for authorization
     *                    level. If {@code null}, not filtered.
     * @param transaction The transaction associated with this operation.
     * @param offset      The starting index of the user records to retrieve.
     * @param count       The maximum number of user records to retrieve.
     * @return A list of Users matching the filter criteria, or an empty list if
     *         no matches were found.
     */
    List<User> getUsers(User user, Faculty faculty, UserState auth,
            Transaction transaction, int offset, int count);

    /**
     * Retrieve a list of users from the database with pagination support.
     *
     * <p>
     * Values set in User, except for {@code user.id} and {@code user.userState}
     * are used to filter results if set. {@code user.userState} needs to
     * contain exactly one element to filter for faculty and userState, with
     * null values if one of them is not filtered for. {@code user.id} is
     * ignored in the filter object.
     *
     * @param user        The user DTO used as a filter for retrieving users.
     * @param transaction The transaction associated with this operation.
     * @param offset      The starting index of the user records to retrieve.
     * @param count       The maximum number of user records to retrieve.
     * @return A list of Users matching the filter criteria, or an empty list if
     *         no matches were found.
     * @throws InvalidInputException if {@code user.userState} contains more
     *                               than one element
     */
    List<User> getUsers(User user, Transaction transaction, int offset,
            int count) throws InvalidInputException;

    /**
     * Get the Total Number of Users matching a given filter.
     *
     * @param user        The user DTO used as a filter for retrieving users.
     * @param faculty     can be {@code null}. Filter used for the Faculty. If
     *                    {@code null}, no filter.
     * @param auth        can be {@code null}. Filter used for authorization
     *                    level. If {@code null}, not filtered.
     * @param transaction The transaction associated with this operation.
     * @return the total number of Users found matching the filter.
     */
    int getTotalUserNumber(User user, Faculty faculty, UserState auth,
            Transaction transaction);

    /**
     * Get the Total Number of Users matching a given filter.
     *
     * @param user        The user DTO used as a filter for retrieving users.
     * @param transaction The transaction associated with this operation.
     * @return the total number of Users found matching the filter.
     * @throws InvalidInputException if {@code user.userState} contains more
     *                               than one element
     */
    int getTotalUserNumber(User user, Transaction transaction)
            throws InvalidInputException;

    /**
     * Adds entries in the authorization table or updates entries if already
     * there.
     *
     * <p>
     * The {@code user.id} needs to exist and be set correctly, as well as the
     * faculty ID in {@code user.userState}. All entries from the
     * {@code user.userState} along with the {@code user.id} will be added to
     * the database, or updated if existing.
     *
     * @param user        The user DTO containing the user ID and map of
     *                    faculties with authorization levels.
     * @param transaction The transaction associated with this operation.
     * @throws DataNotCompleteException if necessary values of the {@code user}
     *                                  are not set
     * @throws InvalidInputException    if input data is faulty
     */
    void updateOrAddAuth(User user, Transaction transaction)
            throws DataNotCompleteException, InvalidInputException;

    /**
     * Removes entries in the authorization table.
     *
     * <p>
     * The {@code user.id} needs to exist and be set correctly, as well as the
     * faculty IDs in {@code user.userState}. All user ID and faculty ID pairs
     * need to exist in the authorization database. All entries from the
     * {@code user.userState} along with the {@code user.id} will be removed
     * from the database. TODO rmAuth consider changing input parameters to more
     * usable ones.
     *
     * @param user        The user DTO containing the user ID and map of
     *                    faculties with authorization levels.
     * @param transaction The transaction associated with this operation.
     * @throws DataNotFoundException    if not all given user ID and faculty ID
     *                                  pairs exist in the database
     * @throws InvalidInputException    if input data is faulty
     * @throws DataNotCompleteException
     */
    void removeAuth(User user, Transaction transaction)
            throws DataNotFoundException, InvalidInputException,
            DataNotCompleteException;

//    /**
//     * Add user to admin table.
//     *
//     * <p>
//   * The {@code user.id} needs to exist in the user table and be set correctly
//     * and not yet in the admin database, all other fields will be ignored.
//     *
//     * @param user        The user DTO containing the ID to be added to the
//     *                    admin table
//     * @param transaction The transaction associated with this operation.
//     * @throws KeyExistsException    if user is already in the table
//     * @throws InvalidInputException
//     */
//    void addAdmin(User user, Transaction transaction)
//            throws KeyExistsException, InvalidInputException;
//
//    /**
//     * Remove a User from the admin Table.
//     *
//     * <p>
//     * The {@code user.id} needs to exist in the user table and in the admin
//     * database.
//     *
//   * @param user        The user DTO containing the ID to be removed from the
//     *                    admin table
//     * @param transaction The transaction associated with this operation.
//     * @throws DataNotFoundException if the user doesn't exist in the database
//     */
//    void removeAdmin(User user, Transaction transaction)
//            throws DataNotFoundException;
//
//    /**
//     * Retrieves a list of administrators.
//     *
//     * @param transaction The transaction associated with this operation.
//     * @return A list of administrators.
//     */
//    List<User> getAdmins(Transaction transaction);

}
