package dtt.dataAccess.repository.Postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import jakarta.inject.Named;

/**
 * A Postgres implementation for a class handling database access related to
 * users and user authorization.
 *
 * @author Stefan Witka
 *
 */

@ApplicationScoped
@Named
public class UserDAO
        implements dtt.dataAccess.repository.interfaces.UserDAO {

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
            statement.setDate(i++,
                    java.sql.Date.valueOf(user.getBirthDate()));
            statement.setString(i++, user.getPasswordHashed());
            statement.setString(i++, user.getPasswordSalt());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new InvalidInputException(
                        "Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement
                    .getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new InvalidInputException(
                            "Creating user failed, no ID obtained.");
                }
            }
            if (!user.getUserState().isEmpty()) {
                try (PreparedStatement statement2 = transaction
                        .getConnection().prepareStatement(query2)) {
                    for (Map.Entry<Faculty, UserState> entry : user
                            .getUserState().entrySet()) {
                        int j = 1;
                        statement2.setInt(j++, user.getId());
                        statement2.setInt(j++,
                                entry.getKey().getId());
                        statement2.setString(j++,
                                entry.getValue().name());
                        statement2.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            switch (e.getSQLState()) {
            case "23502":
                throw new DataNotCompleteException(
                        e.getLocalizedMessage(), e);

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
            throw new DataNotFoundException(
                    "User not found in the database.", e);
        }

    }

    @Override
    public void update(final User user, final Transaction transaction)
            throws DataNotFoundException, InvalidInputException,
            KeyExistsException {
        StringBuilder queryBuilder = new StringBuilder(
                "UPDATE \"user\" SET");
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
            if (user.getUserState() != null
                    || !user.getUserState().isEmpty()) {
                String query2 = "INSERT INTO authentication "
                        + "(user_id, faculty_id, user_level) "
                        + "VALUES (?, ?, ?) ON CONFLICT UPDATE";
                try (PreparedStatement statement2 = transaction
                        .getConnection().prepareStatement(query2)) {
                    for (Map.Entry<Faculty, UserState> entry : user
                            .getUserState().entrySet()) {
                        int j = 1;
                        statement2.setInt(j++, user.getId());
                        statement2.setInt(j++, entry.getKey().getId());
                        statement2.setString(j++,
                                entry.getValue().name());
                        statement2.executeUpdate();
                    }
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
    public void getUserById(final User user,
            final Transaction transaction)
            throws DataNotFoundException {
        String query = "SELECT email_address, first_name, last_name,"
                + " birth_date, password_hash, password_salt "
                + "FROM \"user\" WHERE user_id = ?";
        String query2 = "SELECT authentication.faculty_id, "
                + "authentication.user_level, faculty.faculty_name "
                + "FROM authentication INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id "
                + "WHERE user_id = ?";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query);
                PreparedStatement statement2 = transaction
                        .getConnection().prepareStatement(query2)) {
            statement.setInt(1, user.getId());
            statement2.setInt(1, user.getId());

            try (ResultSet resultSet = statement.executeQuery();
                    ResultSet resultSet2 = statement2
                            .executeQuery();) {
                if (resultSet.next()) {
                    // Retrieve the user data from the result set
                    user.setEmail(
                            resultSet.getString("email_address"));
                    user.setFirstName(
                            resultSet.getString("first_name"));
                    user.setLastName(
                            resultSet.getString("last_name"));
                    user.setBirthDate(resultSet.getDate("birth_date")
                            .toString());
                    user.setPasswordHashed(
                            resultSet.getString("password_hash"));
                    user.setPasswordSalt(
                            resultSet.getString("password_salt"));
                } else {
                    throw new DataNotFoundException(
                            "User not found in the database.");
                }
                Map<Faculty, UserState> stateMap =
                        new HashMap<Faculty, UserState>();
                while (resultSet2.next()) {
                    Faculty f = new Faculty();
                    f.setId(resultSet2.getInt("faculty_id"));
                    f.setName(resultSet2.getString("faculty_name"));
                    stateMap.put(f, UserState.valueOf(
                            resultSet2.getString("user_level")));
                }
                user.setUserState(stateMap);
            }
        } catch (SQLException e) {
            throw new DataNotFoundException(
                    "Failed to retrieve user data.", e);
        }
    }

    @Override
    public boolean findUserByEmail(final User user,
            final Transaction transaction) {
        String query = "SELECT user_id, first_name, last_name, birth_date, "
                + "password_hash, password_salt FROM \"user\" "
                + "WHERE email_address = ?";
        String query2 = "SELECT authentication.faculty_id, "
                + "authentication.user_level, faculty.faculty_name "
                + "FROM authentication INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id "
                + "WHERE user_id = ?";

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query);
                PreparedStatement statement2 = transaction
                        .getConnection().prepareStatement(query2)) {
            statement.setString(1, user.getEmail());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the user data from the result set
                    user.setId(resultSet.getInt("user_id"));
                    user.setFirstName(
                            resultSet.getString("first_name"));
                    user.setLastName(
                            resultSet.getString("last_name"));
                    user.setBirthDate(resultSet.getDate("birth_date")
                            .toString());
                    user.setPasswordHashed(
                            resultSet.getString("password_hash"));
                    user.setPasswordSalt(
                            resultSet.getString("password_salt"));

                    statement2.setInt(1, user.getId());
                    try (ResultSet resultSet2 = statement2
                            .executeQuery()) {
                        Map<Faculty, UserState> stateMap =
                                new HashMap<Faculty, UserState>();
                        while (resultSet2.next()) {
                            Faculty f = new Faculty();
                            f.setId(resultSet2.getInt("faculty_id"));
                            f.setName(resultSet2
                                    .getString("faculty_name"));
                            stateMap.put(f,
                                    UserState.valueOf(
                                            resultSet2.getString(
                                                    "user_level")));
                        }
                        user.setUserState(stateMap);
                    }

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
            List<User> userList = new ArrayList<>();
            String query = "SELECT \"user\".user_id, \"user\".email_address, "
                    + "\"user\".first_name, \"user\".last_name,"
                    + " \"user\".birth_date, faculty.faculty_name, "
                    + "authentication.user_Level FROM \"user\" "
                    + "INNER JOIN authentication "
                + "ON \"user\".user_id=authentication.user_id "
                + "INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id WHERE ";
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (user != null && user.getEmail() != null) {
            conditions.add("\"user\".email_address = ?");
            parameters.add(user.getEmail());
        }

        if (user != null && user.getFirstName() != null) {
            conditions.add("\"user\".first_name = ?");
            parameters.add(user.getFirstName());
        }

        if (user != null && user.getEmail() != null) {
            conditions.add("\"user\".email_address = ?");
            parameters.add(user.getEmail());
        }

        if (user != null && user.getBirthDate() != null) {
            conditions.add("\"user\".birth_date = ?");
            parameters.add(user.getBirthDate());
        }

        if (faculty != null) {
            conditions.add("faculty.faculty_id = ?");
            parameters.add(faculty.getId());
        }

        if (auth != null) {
            conditions.add("authentication.user_state = ?");
            parameters.add(auth.name());
        }

        if (!conditions.isEmpty()) {
            query += String.join(" AND ", conditions);
        } else {
            query += "TRUE"; // No conditions, include all users
        }

        query += " LIMIT ? OFFSET ?";

        parameters.add(count);
        parameters.add(offset);

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User fetchedUser = new User();
                    fetchedUser.setId(resultSet.getInt("user_id"));
                    fetchedUser.setEmail(
                            resultSet.getString("email_address"));
                    fetchedUser.setFirstName(
                            resultSet.getString("first_name"));
                    fetchedUser.setLastName(
                            resultSet.getString("last_name"));
                    fetchedUser.setBirthDate(resultSet
                            .getDate("birth_date").toString());

                    userList.add(fetchedUser);
                }
            }
        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            throw new DBConnectionFailedException(
                    "Failed to retrieve users.", e);
        }

        return userList;
    }

    @Override
    public List<User> getUsers(final User user,
            final Transaction transaction, final int offset,
            final int count) throws InvalidInputException {
        Faculty faculty = null;
        UserState auth = null;
        List<User> userList = new ArrayList<>();
        String query = "SELECT \"user\".user_id, \"user\".email_address, "
                + "\"user\".first_name, \"user\".last_name, "
                + "\"user\".birth_date, faculty.faculty_name, "
                + "authentication.user_Level FROM \"user\" "
                + "INNER JOIN authentication "
                + "ON \"user\".user_id=authentication.user_id "
                + "INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id WHERE ";
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (user.getUserState() != null) {
            if (user.getUserState().entrySet().size() == 1) {
                Map.Entry<Faculty, UserState> entry = user
                        .getUserState().entrySet().iterator().next();
                faculty = entry.getKey();
                auth = entry.getValue();
            } else {
                throw new InvalidInputException(
                        "more than one filter entry for userState"
                                + " and faculty, could not resolve");
            }
        }

        if (user != null && user.getEmail() != null) {
            conditions.add("\"user\".email_address = ?");
            parameters.add(user.getEmail());
        }

        if (user != null && user.getFirstName() != null) {
            conditions.add("\"user\".first_name = ?");
            parameters.add(user.getFirstName());
        }

        if (user != null && user.getEmail() != null) {
            conditions.add("\"user\".email_address = ?");
            parameters.add(user.getEmail());
        }

        if (user != null && user.getBirthDate() != null) {
            conditions.add("\"user\".birth_date = ?");
            parameters.add(user.getBirthDate());
        }

        if (faculty != null) {
            conditions.add("faculty.faculty_id = ?");
            parameters.add(faculty.getId());
        }

        if (auth != null) {
            conditions.add("authentication.user_state = ?");
            parameters.add(auth.name());
        }

        if (!conditions.isEmpty()) {
            query += String.join(" AND ", conditions);
        } else {
            query += "TRUE"; // No conditions, include all users
        }

        query += " LIMIT ? OFFSET ?";

        parameters.add(count);
        parameters.add(offset);

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User fetchedUser = new User();
                    fetchedUser.setId(resultSet.getInt("user_id"));
                    fetchedUser.setEmail(
                            resultSet.getString("email_address"));
                    fetchedUser.setFirstName(
                            resultSet.getString("first_name"));
                    fetchedUser.setLastName(
                            resultSet.getString("last_name"));
                    fetchedUser.setBirthDate(resultSet
                            .getDate("birth_date").toString());

                    userList.add(fetchedUser);
                }
            }
        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            throw new DBConnectionFailedException(
                    "Failed to retrieve users.", e);
        }

        return userList;
    }

    @Override
    public int getTotalUserNumber(final User user,
            final Faculty faculty, final UserState auth,
            final Transaction transaction) {
        String query = "SELECT COUNT(*) FROM \"user\" "
                + "INNER JOIN authentication "
                + "ON \"user\".user_id=authentication.user_id "
                + "INNER JOIN faculty "
                + "ON authentication.faculty_id=faculty.faculty_id "
                + "WHERE ";
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (user != null && user.getEmail() != null) {
            conditions.add("\"user\".email_address = ?");
            parameters.add(user.getEmail());
        }

        if (user != null && user.getFirstName() != null) {
            conditions.add("\"user\".first_name = ?");
            parameters.add(user.getFirstName());
        }

        if (user != null && user.getEmail() != null) {
            conditions.add("\"user\".email_address = ?");
            parameters.add(user.getEmail());
        }

        if (user != null && user.getBirthDate() != null) {
            conditions.add("\"user\".birth_date = ?");
            parameters.add(user.getBirthDate());
        }

        if (faculty != null) {
            conditions.add("faculty.faculty_id = ?");
            parameters.add(faculty.getId());
        }

        if (auth != null) {
            conditions.add("authentication.user_state = ?");
            parameters.add(auth.name());
        }

        if (!conditions.isEmpty()) {
            query += String.join(" AND ", conditions);
        } else {
            query += "TRUE"; // No conditions, include all users
        }

        try (PreparedStatement statement = transaction.getConnection()
                .prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            throw new DBConnectionFailedException(
                    "Failed to retrieve users.", e);
        }
        return -1;
    }

    @Override
    public void UpdateOrAddAuth(final User user, final Transaction transaction)
            throws DataNotCompleteException, InvalidInputException {
        if (user.getUserState() != null
                || !user.getUserState().isEmpty()) {
            String query = "INSERT INTO authentication (user_id, faculty_id, "
                    + "user_level) VALUES (?, ?, ?) ON CONFLICT UPDATE";
            try (PreparedStatement statement = transaction
                    .getConnection().prepareStatement(query)) {
                for (Map.Entry<Faculty, UserState> entry : user
                        .getUserState().entrySet()) {
                    int i = 1;
                    statement.setInt(i++, user.getId());
                    statement.setInt(i++, entry.getKey().getId());
                    statement.setString(i++, entry.getValue().name());
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                switch (e.getSQLState()) {
                case "23503":
                    throw new InvalidInputException(
                            "foreign_key_violation", e);

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
        if (user.getUserState() != null
                || !user.getUserState().isEmpty()) {
            String query = "DELETE FROM authentication "
                    + "WHERE user_id = ? AND faculty_id = ?";
            try (PreparedStatement statement = transaction
                    .getConnection().prepareStatement(query)) {
                for (Map.Entry<Faculty, UserState> entry : user
                        .getUserState().entrySet()) {
                    statement.setInt(1, user.getId());
                    statement.setInt(2, entry.getKey().getId());
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new DataNotFoundException(
                                "Admin with user ID " + user.getId()
                                        + " not found.");
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
                throw new InvalidInputException("User with user ID "
                        + user.getId() + " doesn't exist.", e);

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
                throw new DataNotFoundException("Admin with user ID "
                        + user.getId() + " not found.");
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException(
                    "Failed to remove admin.", e);
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
                admin.setBirthDate(
                        resultSet.getDate("birthdate").toString());

                adminList.add(admin);
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException(
                    "Failed to retrieve admins.", e);
        }

        return adminList;
    }

}
