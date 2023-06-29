package dtt.dataAccess.repository.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtt.dataAccess.exceptions.*;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Faculty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Postgres implementation for a class handling database access related to faculties.
 *
 * @author Stefan Witka
 */
@Default
@Named
@ApplicationScoped
public class FacultyDAO implements dtt.dataAccess.repository.interfaces.FacultyDAO {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor for FacultyDAO
     */
    public FacultyDAO() {

    }


    @Override
    public void add(Faculty faculty, Transaction transaction) throws DataNotCompleteException, InvalidInputException, KeyExistsException {
        LOGGER.debug("add() called: " + faculty.getName());
        String query = "INSERT INTO faculty (faculty_name) VALUES (?)";

        try (PreparedStatement preparedStatement = transaction.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, faculty.getName());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new InvalidInputException("Creating faculty failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    faculty.setId(generatedKeys.getInt(1));
                } else {
                    throw new InvalidInputException("Creating faculty failed, no ID obtained.");
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
    public void remove(Faculty faculty, Transaction transaction)
            throws DataNotFoundException, DataNotCompleteException {
        LOGGER.debug("remove() called: " + faculty.getName());
        String query = "DELETE FROM faculty WHERE faculty_id = ?";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            statement.setInt(1, faculty.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataNotFoundException("Faculty not found in the database.");
            }
        } catch (SQLException e) {
            throw new DataNotFoundException("Faculty not found in the database.", e);
        }
    }

    @Override
    public void update(Faculty faculty, Transaction transaction)
            throws DataNotFoundException, DataNotCompleteException, KeyExistsException {
        LOGGER.debug("update called: " + faculty.getName());
        String query = "UPDATE faculty SET faculty_name = ? WHERE faculty_id = ?";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            statement.setString(1, faculty.getName());
            statement.setInt(2, faculty.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataNotFoundException("Faculty not found in the database.");
            }
        } catch (SQLException e) {
            throw new DataNotFoundException("Faculty not found in the database.", e);
        }
    }

    @Override
    public List<Faculty> getFaculties(Transaction transaction) {
        LOGGER.debug("getFaculties called().");
        List<Faculty> facultyList = new ArrayList<>();
        String query = "SELECT * FROM faculty";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Faculty faculty = new Faculty();
                    faculty.setId(rs.getInt("faculty_id"));
                    faculty.setName(rs.getString("faculty_name"));
                    facultyList.add(faculty);
                }
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException("Failed to retrieve faculties.", e);
        }
        return facultyList;
    }

    @Override
    public boolean findFacultyByName(Faculty faculty, Transaction transaction) {

        LOGGER.debug("findFacultyByName called: " + faculty.getName());
        String query = "SELECT * FROM faculty WHERE faculty_name = ?";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            statement.setString(1, faculty.getName());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    faculty.setId(rs.getInt("faculty_id"));
                    faculty.setName(rs.getString("faculty_name"));

                    return true;
                }
            }

        } catch (SQLException e) {
            LOGGER.debug("An SQLException was thrown: " + e.getMessage());
            throw new DBConnectionFailedException("Failed to find faculty by name.", e);

        }

        return false;
    }

    public Faculty getFacultyById(int id, Transaction transaction) {
        Faculty faculty = null;
        String query = "SELECT * FROM faculty WHERE faculty_id = ?";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    faculty = new Faculty();
                    faculty.setId(rs.getInt("faculty_id"));
                    faculty.setName(rs.getString("faculty_name"));
                }
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException("Failed to retrieve faculty.", e);
        }
        return faculty;
    }
    @Override
    public Faculty getFacultyByName(Faculty faculty, Transaction transaction) {

        LOGGER.debug("findFacultyByName called: " + faculty.getName());
        String query = "SELECT * FROM faculty WHERE faculty_name = ?";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            statement.setString(1, faculty.getName());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    faculty.setId(rs.getInt("faculty_id"));
                    faculty.setName(rs.getString("faculty_name"));

                    return faculty;
                }
            }

        } catch (SQLException e) {
            LOGGER.debug("An SQLException was thrown: " + e.getMessage());
            throw new DBConnectionFailedException("Failed to find faculty by name.", e);

        }

        return null;
    }


}
