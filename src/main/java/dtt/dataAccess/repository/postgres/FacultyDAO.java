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
import jakarta.inject.Named;

/**
 * A Postgres implementation for a class handling database access related to faculties.
 *
 * @author Stefan Witka
 */
@Named
@ApplicationScoped
public class FacultyDAO implements dtt.dataAccess.repository.interfaces.FacultyDAO {

    /**
     * Constructor for FacultyDAO
     */
    public FacultyDAO() {

    }

    @Override
    public void add(Faculty faculty, Transaction transaction) throws DataNotCompleteException, InvalidInputException, KeyExistsException {
        String query = "INSERT INTO faculty (faculty_id, faculty_name) VALUES (? , ?)";

        try (PreparedStatement preparedStatement = transaction.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, faculty.getId());
            preparedStatement.setString(2, faculty.getName());
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
        String query = "UPDATE faculty SET faculty_name = ? WHERE faculty_id = ?";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            statement.setInt(1, faculty.getId());
            statement.setString(2, faculty.getName());

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
        String query = "SELECT * FROM faculty WHERE faculty_name = ?";
        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
            statement.executeQuery();
            statement.setString(1, faculty.getName());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    faculty.setId(rs.getInt("faculty_id"));
                    faculty.setId(rs.getInt("faculty_name"));

                    return true;
                }
            }

        } catch (SQLException e) {
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


}
