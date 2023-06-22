package dtt.dataAccess.repository.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dtt.dataAccess.exceptions.DBConnectionFailedException;
import dtt.dataAccess.exceptions.DataNotCompleteException;
import dtt.dataAccess.exceptions.DataNotFoundException;
import dtt.dataAccess.exceptions.InvalidInputException;
import dtt.dataAccess.exceptions.KeyExistsException;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import dtt.global.tansport.User;
import dtt.global.tansport.UserState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Named;

/**
 * A Postgres implementation for a class handling database access related to
 * users and user authorization.
 *
 * @author Stefan Witka
 *
 */
@Default
@ApplicationScoped
@Named
public class UserDAO
        implements dtt.dataAccess.repository.interfaces.UserDAO {

    /** Initialize Logger. */
    private static final Logger LOGGER = LogManager
            .getLogger(CirculationDAO.class);
    // Column names
//    /** Column name of user_id of the user table. */
//    private static final String U_USER_ID = "\"user\".user_id";
//    /** Column name of email_address of the user table. */
//    private static final String U_EMAIL_ADDRESS = "\"user\".email_address";
//    /** Column name of first_name of the user table. */
//    private static final String U_FIRST_NAME = "\"user\".first_name";
//    /** Column name of last_name of the user table. */
//    private static final String U_LAST_NAME = "\"user\".last_name";
//    /** Column name of birth_date of the user table. */
//    private static final String U_BIRTH_DATE = "\"user\".birth_date";
//    /** Column name of password_hash of the user table. */
//    private static final String U_PASSWORD_HASH = "\"user\".password_hash";
//    /** Column name of password_salt of the user table. */
//    private static final String U_PASSWORD_SALT = "\"user\".password_salt";
//    /** Column name of user_id of the authentication table. */
//    private static final String A_USER_ID = "authentication.user_id";
//    /** Column name of faculty_id of the authentication table. */
//    private static final String A_FACULTY_ID = "authentication.faculty_id";
//    /** Column name of user_level of the authentication table. */
//    private static final String A_USER_LEVEL = "authentication.user_level";
//    /** Column name of faculty_id of the faculty table. */
//    private static final String F_FACULTY_ID = "faculty.faculty_id";
//    /** Column name of faculty_name of the faculty table. */
//    private static final String F_FACULTY_NAME = "faculty.faculty_name";
//    /** Column name of user_id of the admin table. */
//    private static final String ADMIN_USER_ID = "\"admin\".user_id";

    /**
     * Constructor for UserDAO.
     */
    public UserDAO() {
    }

    @Override
    public void add(final User user, final Transaction transaction)
            throws DataNotCompleteException, KeyExistsException,
            InvalidInputException {
        String query = "INSERT INTO \"user\" (email_address, first_name,"
                + " last_name, birth_date, password_hash, password_salt) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String query2 = "INSERT INTO authentication (user_id, faculty_id,"
                + " user_level) VALUES (?, ?, ?) ON CONFLICT UPDATE";

        if (user.getEmail() == null || user.getFirstName() == null
                || user.getLastName() == null
                || user.getEmail().trim().isEmpty()
                || user.getFirstName().trim().isEmpty()
                || user.getLastName().trim().isEmpty()) {
            throw new DataNotCompleteException();
        }

        if (findUserByEmail(user, transaction)) {
            throw new KeyExistsException();
        }

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            statement.setString(i++, user.getEmail());
            statement.setString(i++, user.getFirstName());
            statement.setString(i++, user.getLastName());
            statement.setDate(i++, java.sql.Date.valueOf(user.getBirthDate()));
            statement.setString(i++, user.getPasswordHashed());
            statement.setString(i++, user.getPasswordSalt());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new InvalidInputException(
                        "Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new InvalidInputException(
                            "Creating user failed, no ID obtained.");
                }
            }
            if (!user.getUserState().isEmpty()) {
                try (PreparedStatement statement2 = transaction.getConnection()
                        .prepareStatement(query2)) {
                    for (Map.Entry<Faculty, UserState> entry : user
                            .getUserState().entrySet()) {
                        int j = 1;
                        statement2.setInt(j++, user.getId());
                        statement2.setInt(j++, entry.getKey().getId());
                        statement2.setString(j++, entry.getValue().name());
                        statement2.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            switch (e.getSQLState()) {
            case "23502":
                throw new DataNotCompleteException(e.getLocalizedMessage(), e);

            case "23514":
                throw new InvalidInputException("check_violation", e);

            case "23505":
                throw new KeyExistsException("unique_violation", e);

            default:
                throw new DBConnectionFailedException();
            }
        }
    }

    @Override
    public void remove(final User user, final Transaction transaction)
            throws DataNotFoundException {
        String query = "DELETE FROM \"user\" WHERE user_id = ?";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataNotFoundException(
                        "User not found in the database.");
            }
        } catch (SQLException e) {
            throw new DataNotFoundException("User not found in the database.",
                    e);
        }

    }

    @Override
    public void update(final User user, final Transaction transaction)
            throws DataNotFoundException, InvalidInputException,
            KeyExistsException {
        StringBuilder queryBuilder = new StringBuilder("UPDATE \"user\" SET");
        List<Object> params = new ArrayList<>();

        if (user.getEmail() != null) {
            queryBuilder.append(" email_address = ?,");
            params.add(user.getEmail());
        }

        if (user.getFirstName() != null) {
            queryBuilder.append(" first_name = ?,");
            params.add(user.getFirstName());
        }

        if (user.getLastName() != null) {
            queryBuilder.append(" last_name = ?,");
            params.add(user.getLastName());
        }

        if (user.getBirthDate() != null) {
            queryBuilder.append(" birth_date = ?,");
            params.add(user.getBirthDate());
        }

        if (user.getPasswordHashed() != null) {
            queryBuilder.append(" password_hash = ?,");
            params.add(user.getPasswordHashed());
        }

        if (user.getPasswordSalt() != null) {
            queryBuilder.append(" password_salt = ?,");
            params.add(user.getPasswordSalt());
        }

        // Remove the trailing comma
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);

        queryBuilder.append(" WHERE user_id = ?");
        params.add(user.getId());

        String query = queryBuilder.toString();

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            // Set the parameter values
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataNotFoundException(
                        "User not found in the database.");
            }
            if (user.getUserState() != null || !user.getUserState().isEmpty()) {
                try {
                    updateOrAddAuth(user, transaction);
                } catch (DataNotCompleteException e) {
                    LOGGER.error(
                            "Vanished User state. This should be impossible",
                            e);
                }
            }
        } catch (SQLException e) {
            switch (e.getSQLState()) {
            case "23514":
                throw new InvalidInputException("check_violation", e);

            case "23505":
                throw new KeyExistsException("unique_violation", e);

            default:
                throw new DBConnectionFailedException();
            }
        }
    }

    @Override
    public void getUserById(final User user, final Transaction transaction)
            throws DataNotFoundException {
        String query = "SELECT email_address, first_name, last_name,"
                + " birth_date, password_hash, password_salt "
                + "FROM \"user\" WHERE user_id = ?";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, user.getId());

            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    // Retrieve the user data from the result set
                    user.setEmail(resultSet.getString("email_address"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setBirthDate(
                            resultSet.getDate("birth_date").toString());
                    user.setPasswordHashed(
                            resultSet.getString("password_hash"));
                    user.setPasswordSalt(resultSet.getString("password_salt"));
                } else {
                    throw new DataNotFoundException(
                            "User not found in the database.");
                }
                setAuthentications(user, transaction);
            }
        } catch (SQLException e) {
            throw new DataNotFoundException("Failed to retrieve user data.", e);
        }
    }

    @Override
    public boolean findUserByEmail(final User user,
            final Transaction transaction) {
        String query = "SELECT user_id, first_name, last_name, birth_date, "
                + "password_hash, password_salt FROM \"user\" "
                + "WHERE email_address = ?";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            statement.setString(1, user.getEmail());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the user data from the result set
                    user.setId(resultSet.getInt("user_id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setBirthDate(
                            resultSet.getDate("birth_date").toString());
                    user.setPasswordHashed(
                            resultSet.getString("password_hash"));
                    user.setPasswordSalt(resultSet.getString("password_salt"));

                    setAuthentications(user, transaction);

                    return true; // User with the specified email found
                }
            }
        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            throw new DBConnectionFailedException(
                    "Failed to find user by email.", e);
        }

        return false; // User with the specified email not found in the database
    }

    @Override
    public List<User> getUsers(final User user, final Faculty faculty,
            final UserState auth, final Transaction transaction,
            final int offset, final int count) {
        LOGGER.debug("getUsers() called.");
        List<User> userList = new ArrayList<>();

        // Building SQL
        StringBuilder query = new StringBuilder();
        query.append("SELECT \"user\".user_id FROM \"user\" "
                + "INNER JOIN authentication "
                + "ON \"user\".user_id=authentication.user_id "
                + "INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id WHERE 1=1");

        // Add filter conditions based on provided properties
        if (user != null && user.getEmail() != null) {
            query.append(" AND \"user\".email_address LIKE ?");
        }

        if (user != null && user.getFirstName() != null) {
            query.append(" AND \"user\".first_name LIKE ?");
        }

        if (user != null && user.getLastName() != null) {
            query.append(" AND \"user\".last_name LIKE ?");
        }

        if (user != null && user.getBirthDate() != null) {
            query.append(" AND  \"user\".birth_date LIKE ?");
        }

        if (faculty != null) {
            query.append(" AND faculty.faculty_id = ?");
        }

        if (auth != null) {
            query.append(" AND authentication.user_level LIKE ?");
        }

        query.append(" LIMIT ? OFFSET ?");

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Set filter condition values
            if (user != null && user.getEmail() != null) {
                statement.setString(paramIndex++, user.getEmail());
            }

            if (user != null && user.getFirstName() != null) {
                statement.setString(paramIndex++, user.getFirstName());
            }

            if (user != null && user.getLastName() != null) {
                statement.setString(paramIndex++, user.getLastName());
            }

            if (user != null && user.getBirthDate() != null) {
                statement.setString(paramIndex++, user.getBirthDate());
            }

            if (faculty != null) {
                statement.setLong(paramIndex++, faculty.getId());
            }

            if (auth != null) {
                statement.setString(paramIndex++, auth.name());
            }

            // Set pagination parameters
            statement.setInt(paramIndex++, count);
            statement.setInt(paramIndex, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User fetchedUser = new User();
                    fetchedUser.setId(resultSet.getInt("user_id"));
                    try {
                        getUserById(fetchedUser, transaction);
                        userList.add(fetchedUser);
                    } catch (DataNotFoundException e) {
                        LOGGER.error("Fetched User disappeared, skipping.", e);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve users.", e);
            throw new DBConnectionFailedException("Failed to retrieve users.",
                    e);
        }

        return userList;
    }

    @Override
    public List<User> getUsers(final User user, final Transaction transaction,
            final int offset, final int count) throws InvalidInputException {
        LOGGER.debug("getUsers() called.");

        // initialize objects
        Faculty faculty = null;
        UserState auth = null;
        List<User> userList = new ArrayList<>();

        // set filter Values for faculty and authentication
        if (user.getUserState() != null) {
            if (user.getUserState().entrySet().size() == 1) {
                Map.Entry<Faculty, UserState> entry = user.getUserState()
                        .entrySet().iterator().next();
                faculty = entry.getKey();
                auth = entry.getValue();
            } else {
                throw new InvalidInputException(
                        "more than one filter entry for userState"
                                + " and faculty, could not resolve");
            }
        }

        // Building SQL
        StringBuilder query = new StringBuilder();
        query.append("SELECT \"user\".user_id FROM \"user\" "
                + "INNER JOIN authentication "
                + "ON \"user\".user_id=authentication.user_id "
                + "INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id WHERE 1=1");

        // Add filter conditions based on provided properties
        if (user != null && user.getEmail() != null) {
            query.append(" AND \"user\".email_address LIKE ?");
        }

        if (user != null && user.getFirstName() != null) {
            query.append(" AND \"user\".first_name LIKE ?");
        }

        if (user != null && user.getLastName() != null) {
            query.append(" AND \"user\".last_name LIKE ?");
        }

        if (user != null && user.getBirthDate() != null) {
            query.append(" AND  \"user\".birth_date LIKE ?");
        }

        if (faculty != null) {
            query.append(" AND faculty.faculty_id = ?");
        }

        if (auth != null) {
            query.append(" AND authentication.user_level LIKE ?");
        }

        query.append(" LIMIT ? OFFSET ?");

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Set filter condition values
            if (user != null && user.getEmail() != null) {
                statement.setString(paramIndex++, user.getEmail());
            }

            if (user != null && user.getFirstName() != null) {
                statement.setString(paramIndex++, user.getFirstName());
            }

            if (user != null && user.getLastName() != null) {
                statement.setString(paramIndex++, user.getLastName());
            }

            if (user != null && user.getBirthDate() != null) {
                statement.setString(paramIndex++, user.getBirthDate());
            }

            if (faculty != null) {
                statement.setLong(paramIndex++, faculty.getId());
            }

            if (auth != null) {
                statement.setString(paramIndex++, auth.name());
            }

            // Set pagination parameters
            statement.setInt(paramIndex++, count);
            statement.setInt(paramIndex, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User fetchedUser = new User();
                    fetchedUser.setId(resultSet.getInt("user_id"));
                    try {
                        getUserById(fetchedUser, transaction);
                        userList.add(fetchedUser);
                    } catch (DataNotFoundException e) {
                        LOGGER.error("Fetched User disappeared, skipping.", e);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve users.", e);
            throw new DBConnectionFailedException("Failed to retrieve users.",
                    e);
        }

        return userList;
    }

    @Override
    public int getTotalUserNumber(final User user, final Faculty faculty,
            final UserState auth, final Transaction transaction) {
        LOGGER.debug("getTotalUserNumber() called.");

        // Building SQL
        StringBuilder query = new StringBuilder();
        query.append("SELECT Count(*) FROM \"user\" "
                + "INNER JOIN authentication "
                + "ON \"user\".user_id=authentication.user_id "
                + "INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id WHERE 1=1");

        // Add filter conditions based on provided properties
        if (user != null && user.getEmail() != null) {
            query.append(" AND \"user\".email_address LIKE ?");
        }

        if (user != null && user.getFirstName() != null) {
            query.append(" AND \"user\".first_name LIKE ?");
        }

        if (user != null && user.getLastName() != null) {
            query.append(" AND \"user\".last_name LIKE ?");
        }

        if (user != null && user.getBirthDate() != null) {
            query.append(" AND  \"user\".birth_date LIKE ?");
        }

        if (faculty != null) {
            query.append(" AND faculty.faculty_id = ?");
        }

        if (auth != null) {
            query.append(" AND authentication.user_level LIKE ?");
        }

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Set filter condition values
            if (user != null && user.getEmail() != null) {
                statement.setString(paramIndex++, user.getEmail());
            }

            if (user != null && user.getFirstName() != null) {
                statement.setString(paramIndex++, user.getFirstName());
            }

            if (user != null && user.getLastName() != null) {
                statement.setString(paramIndex++, user.getLastName());
            }

            if (user != null && user.getBirthDate() != null) {
                statement.setString(paramIndex++, user.getBirthDate());
            }

            if (faculty != null) {
                statement.setLong(paramIndex++, faculty.getId());
            }

            if (auth != null) {
                statement.setString(paramIndex++, auth.name());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            throw new DBConnectionFailedException("Failed to retrieve users.",
                    e);
        }
        return -1;
    }

    @Override
    public int getTotalUserNumber(final User user,
            final Transaction transaction) throws InvalidInputException {
        LOGGER.debug("getTotalUserNumber() called.");

        // initialize objects
        Faculty faculty = null;
        UserState auth = null;

        // set filter Values for faculty and authentication
        if (user.getUserState() != null) {
            if (user.getUserState().entrySet().size() == 1) {
                Map.Entry<Faculty, UserState> entry = user.getUserState()
                        .entrySet().iterator().next();
                faculty = entry.getKey();
                auth = entry.getValue();
            } else {
                throw new InvalidInputException(
                        "more than one filter entry for userState"
                                + " and faculty, could not resolve");
            }
        }

        // Building SQL
        StringBuilder query = new StringBuilder();
        query.append("SELECT Count(*) FROM \"user\" "
                + "INNER JOIN authentication "
                + "ON \"user\".user_id=authentication.user_id "
                + "INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id WHERE 1=1");

        // Add filter conditions based on provided properties
        if (user != null && user.getEmail() != null) {
            query.append(" AND \"user\".email_address LIKE ?");
        }

        if (user != null && user.getFirstName() != null) {
            query.append(" AND \"user\".first_name LIKE ?");
        }

        if (user != null && user.getLastName() != null) {
            query.append(" AND \"user\".last_name LIKE ?");
        }

        if (user != null && user.getBirthDate() != null) {
            query.append(" AND  \"user\".birth_date LIKE ?");
        }

        if (faculty != null) {
            query.append(" AND faculty.faculty_id = ?");
        }

        if (auth != null) {
            query.append(" AND authentication.user_level LIKE ?");
        }

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Set filter condition values
            if (user != null && user.getEmail() != null) {
                statement.setString(paramIndex++, user.getEmail());
            }

            if (user != null && user.getFirstName() != null) {
                statement.setString(paramIndex++, user.getFirstName());
            }

            if (user != null && user.getLastName() != null) {
                statement.setString(paramIndex++, user.getLastName());
            }

            if (user != null && user.getBirthDate() != null) {
                statement.setString(paramIndex++, user.getBirthDate());
            }

            if (faculty != null) {
                statement.setLong(paramIndex++, faculty.getId());
            }

            if (auth != null) {
                statement.setString(paramIndex++, auth.name());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            throw new DBConnectionFailedException("Failed to retrieve users.",
                    e);
        }
        return -1;
    }

    @Override
    public void updateOrAddAuth(final User user, final Transaction transaction)
            throws DataNotCompleteException, InvalidInputException {
        if (user.getUserState() != null || !user.getUserState().isEmpty()) {
            String query = "INSERT INTO authentication (user_id, faculty_id, "
                    + "user_level) VALUES (?, ?, ?) ON CONFLICT UPDATE";
            try (PreparedStatement statement = transaction.getConnection()
                    .prepareStatement(query)) {
                for (Map.Entry<Faculty, UserState> entry : user.getUserState()
                        .entrySet()) {
                    int i = 1;
                    statement.setInt(i++, user.getId());
                    statement.setInt(i++, entry.getKey().getId());
                    statement.setString(i++, entry.getValue().name());
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                switch (e.getSQLState()) {
                case "23503":
                    throw new InvalidInputException("foreign_key_violation", e);

                default:
                    throw new DBConnectionFailedException();
                }
            }
        } else {
            throw new DataNotCompleteException();
        }

    }

    @Override
    public void removeAuth(final User user, final Transaction transaction)
            throws DataNotFoundException, DataNotCompleteException {
        if (user.getUserState() != null || !user.getUserState().isEmpty()) {
            String query = "DELETE FROM authentication "
                    + "WHERE user_id = ? AND faculty_id = ?";
            try (PreparedStatement statement = transaction.getConnection()
                    .prepareStatement(query)) {
                for (Map.Entry<Faculty, UserState> entry : user.getUserState()
                        .entrySet()) {
                    statement.setInt(1, user.getId());
                    statement.setInt(2, entry.getKey().getId());
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new DataNotFoundException("Admin with user ID "
                                + user.getId() + " not found.");
                    }
                }
            } catch (SQLException e) {
                throw new DBConnectionFailedException(
                        "Failed to remove authentications", e);
            }
        } else {
            throw new DataNotCompleteException();
        }
    }

    @Override
    public void addAdmin(final User user, final Transaction transaction)
            throws KeyExistsException, InvalidInputException {
        String query = "INSERT INTO \"admin\" (user_id) VALUES (?)";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            switch (e.getSQLState()) {
            case "23503":
                throw new InvalidInputException(
                        "User with user ID " + user.getId() + " doesn't exist.",
                        e);

            case "23505":
                throw new KeyExistsException("Admin with user ID "
                        + user.getId() + " already exists.", e);

            default:
                throw new DBConnectionFailedException();
            }
        }
    }

    @Override
    public void removeAdmin(final User user, final Transaction transaction)
            throws DataNotFoundException {
        String query = "DELETE FROM \"admin\" WHERE user_id = ?";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, user.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DataNotFoundException(
                        "Admin with user ID " + user.getId() + " not found.");
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException("Failed to remove admin.", e);
        }
    }

    @Override
    public List<User> getAdmins(final Transaction transaction) {
        List<User> adminList = new ArrayList<>();
        String query = "SELECT u.user_id, u.email_address, "
                + "u.firstname, u.lastname, u.birthdate "
                + "FROM \"admin\" AS a "
                + "INNER JOIN \"user\" AS u ON a.user_id = u.user_id";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User admin = new User();
                admin.setId(resultSet.getInt("user_id"));
                admin.setEmail(resultSet.getString("email_address"));
                admin.setFirstName(resultSet.getString("firstname"));
                admin.setLastName(resultSet.getString("lastname"));
                admin.setBirthDate(resultSet.getDate("birthdate").toString());

                adminList.add(admin);
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException("Failed to retrieve admins.",
                    e);
        }
        return adminList;
    }

    private void setAuthentications(final User user,
            final Transaction transaction) {
        LOGGER.debug("Loading authentications.");
        String query = "SELECT authentication.faculty_id, "
                + "authentication.user_level, faculty.faculty_name "
                + "FROM authentication INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id "
                + "WHERE user_id = ?";
        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, user.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                Map<Faculty, UserState> stateMap =
                        new HashMap<Faculty, UserState>();
                while (resultSet.next()) {
                    Faculty f = new Faculty();
                    f.setId(resultSet.getInt("faculty_id"));
                    f.setName(resultSet.getString("faculty_name"));
                    stateMap.put(f, UserState
                            .valueOf(resultSet.getString("user_level")));
                }
                user.setUserState(stateMap);
            }

        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            throw new DBConnectionFailedException(
                    "Failed to load authentications.", e);
        }

    }
}
