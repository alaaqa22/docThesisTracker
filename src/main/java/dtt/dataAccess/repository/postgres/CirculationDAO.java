package dtt.dataAccess.repository.postgres;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dtt.dataAccess.exceptions.*;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Circulation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Postgres implementation for a class handling database access related to Circulations.
 *
 * @author Stefan Witka
 */
@Default
@Named
@ApplicationScoped
public class CirculationDAO implements dtt.dataAccess.repository.interfaces.CirculationDAO {
    private static final Logger LOGGER = LogManager.getLogger (CirculationDAO.class);
    // Table name
    private static final String TABLE = "circulation";
    // Column names
    private static final String CIRCULATION_ID = "circulation_id";
    private static final String TITLE = "title";
    private static final String DOC_CANDIDATE = "doctoral_candidate_name";
    private static final String DOC_SUPERVISOR = "doctoral_supervisor_name";
    private static final String DESCRIPTION = "description";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String IS_OBLIGATORY = "is_obligatory";
    private static final String CREATED_BY = "created_by";
    private static final String FACULTY_ID = "faculty_id";
    private static final String IS_VALID = "is_valid";

    /**
     * Constructor for CirculationDAO
     */
    public CirculationDAO () {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void add(Circulation circulation, Transaction transaction)
            throws DataNotCompleteException, InvalidInputException, KeyExistsException {
        String query = "INSERT INTO " + TABLE + " ("+ TITLE + ", "+ DOC_CANDIDATE + ", " + DOC_SUPERVISOR+", " +
                DESCRIPTION + ", "+ START_DATE +", "+ END_DATE+", " + IS_OBLIGATORY + ","+ CREATED_BY +", " + FACULTY_ID +" , " + IS_VALID + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query)) {
            setCirculationStatement(circulation, statement);

            int affectedRows = statement.executeUpdate ();

            if (affectedRows == 0) {
                throw new InvalidInputException("Creating circulation failed, no rows affected");
            }
        } catch (SQLException e) {
            LOGGER.error ("SQL exception in add(): " + e.getMessage ());
            switch (e.getSQLState ()) {
                case "23502":
                    throw new DataNotCompleteException (e.getLocalizedMessage (), e);
                case "23505":
                    throw new KeyExistsException ("unique_violation", e);
                case "23514":
                    throw new InvalidInputException("check_violation", e);
                default:
                    throw new DBConnectionFailedException(e.getMessage());
            }
        }

    }

    /**
     * Sets the parameters to the prepared statement. ID is has to be added separately as there is no ID when adding a
     * new circulation.
     */
    private void setCirculationStatement(Circulation circulation, PreparedStatement statement) throws SQLException {
        statement.setString(1, circulation.getTitle ());
        statement.setString(2, circulation.getDoctoralCandidateName ());
        statement.setString(3, circulation.getDoctoralSupervisor ());
        statement.setString(4, circulation.getDescription ());
        statement.setTimestamp(5, circulation.getStartDate ());

        statement.setTimestamp(6, circulation.getEndDate ());
        statement.setBoolean(7, circulation.isObligatory ());
        statement.setInt(8, circulation.getCreatedBy ());
        statement.setInt(9, circulation.getFacultyId ());
        statement.setBoolean(10, circulation.isValid ());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void remove(Circulation circulation, Transaction transaction) throws DataNotFoundException {
        String query = "DELETE FROM " + TABLE + " WHERE " + CIRCULATION_ID + " = ?";

        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query)) {
            statement.setInt (1, circulation.getId ());

            int affectedRows = statement.executeUpdate ();

            if (affectedRows == 0) {
                throw new DataNotFoundException ("Circulation not found in the database");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception in remove(): " + e.getMessage ());
            throw new DataNotFoundException ("Circulation not found in the database.", e);
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public void update(Circulation circulation, Transaction transaction)
            throws DataNotFoundException, InvalidInputException, KeyExistsException {
        LOGGER.debug ("update() called.");
        String query = "UPDATE "+TABLE+" SET " + TITLE + " = ?, " + DOC_CANDIDATE + " = ?," + DOC_SUPERVISOR + "= ?," +
                DESCRIPTION + " = ?, " + START_DATE + " = ?, " + END_DATE + " = ?, " + IS_OBLIGATORY + " = ?, " + CREATED_BY +
                " = ?, " + FACULTY_ID + " = ?, " + IS_VALID + " = ?" +
                "WHERE " + CIRCULATION_ID + " = ?";

        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query)) {
            setCirculationStatement (circulation, statement);
            // Finally set ID.
            statement.setInt (11, circulation.getId ());
            statement.executeUpdate ();

        } catch (SQLException e) {
            LOGGER.error ("SQL exception in update(): " + e.getMessage ());
            switch (e.getSQLState ()) {
                case "23514":
                    throw new InvalidInputException("check_violation", e);
                case "23505":
                    throw new KeyExistsException("unique_violation", e);
                default:
                    throw new DBConnectionFailedException();
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void getCirculationById(Circulation circulation, Transaction transaction) throws DataNotFoundException {
        LOGGER.debug ("getCirculationById() called.");
        String query = "SELECT * FROM " + TABLE + " WHERE " + CIRCULATION_ID + " = ?";

        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query)) {
            statement.setInt (1, circulation.getId ());

            try (ResultSet resultSet = statement.executeQuery ()) {
                if (resultSet.next ()) {
                    circulation.setTitle (resultSet.getString (TITLE));
                    circulation.setDoctoralCandidateName (resultSet.getString (DOC_CANDIDATE));
                    circulation.setDoctoralSupervisor (resultSet.getString (DOC_SUPERVISOR));
                    circulation.setDescription (resultSet.getString (DESCRIPTION));
                    circulation.setStartDate (resultSet.getTimestamp (START_DATE));
                    circulation.setEndDate (resultSet.getTimestamp (END_DATE));
                    circulation.setObligatory (resultSet.getBoolean (IS_OBLIGATORY));
                    circulation.setCreatedBy (resultSet.getInt (CREATED_BY));
                    circulation.setFacultyId (resultSet.getInt (FACULTY_ID));
                    circulation.setValid (resultSet.getBoolean (IS_VALID));
                } else {
                    throw new DataNotFoundException ("Circulation not found in the database.");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception in getCirculationById(): " + e.getMessage ());
            throw new DataNotFoundException("Failed to retrieve circulation data.");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Circulation> getCirculations(Circulation circulation, Transaction transaction,
                                              int offset, int count) {
        List<Circulation> circulations = new ArrayList<>();

        // Build the SQL query string
        StringBuilder query = new StringBuilder();
        query.append ("SELECT * FROM " + TABLE + " WHERE 1=1");

        // Add filter conditions based on the provided properties
        if (circulation.getTitle() != null) {
            query.append (" AND " + TITLE + " ILIKE ?");
        }

        if (circulation.getDescription() != null) {
            query.append (" AND " + DESCRIPTION + " ILIKE ?");
        }

        if (circulation.getDoctoralCandidateName() != null) {
            query.append (" AND " + DOC_CANDIDATE + " ILIKE ?");
        }

        if (circulation.getDoctoralSupervisor() != null) {
            query.append (" AND " + DOC_SUPERVISOR + " ILIKE ?");
        }

        if (circulation.getStartDate() != null) {
            query.append (" AND " + START_DATE + " = ?");
        }

        if (circulation.getEndDate() != null) {
            query.append (" AND " + END_DATE + " = ?");
        }

        if (circulation.getFacultyId() != 0) {
            query.append(" AND " + FACULTY_ID + " = ?");
        }

        query.append (" LIMIT ? OFFSET ?");

        try (PreparedStatement statement = transaction.getConnection().prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Set filter condition values
            if (circulation.getTitle () != null) {
                statement.setString (paramIndex++, "%" + circulation.getTitle() + "%");
            }

            if (circulation.getDescription () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDescription() + "%");
            }

            if (circulation.getDoctoralCandidateName () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralCandidateName() + "%");
            }

            if (circulation.getDoctoralSupervisor () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralSupervisor() + "%");
            }

            if (circulation.isObligatory ()) {
                statement.setBoolean (paramIndex++, circulation.isObligatory());
            }

            if (circulation.getStartDate() != null) {
                statement.setObject (paramIndex++, circulation.getStartDate());
            }

            if (circulation.getEndDate() != null) {
                statement.setObject (paramIndex++, circulation.getEndDate());
            }
            if (circulation.getFacultyId() != 0) {
                statement.setInt(paramIndex++, circulation.getFacultyId());
            }

            // Set pagination parameters
            statement.setInt (paramIndex++, count);
            statement.setInt (paramIndex, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Iterate over the result set and populate the list of circulations
                while (resultSet.next()) {
                    Circulation resultCirculation = new Circulation();
                    //Populate the circulation with data from the result set
                    resultCirculation.setId(resultSet.getInt (CIRCULATION_ID));
                    resultCirculation.setTitle(resultSet.getString (TITLE));
                    resultCirculation.setDescription(resultSet.getString (DESCRIPTION));
                    resultCirculation.setDoctoralCandidateName(resultSet.getString (DOC_CANDIDATE));
                    resultCirculation.setDoctoralSupervisor(resultSet.getString (DOC_SUPERVISOR));
                    resultCirculation.setObligatory(resultSet.getBoolean (IS_OBLIGATORY));
                    resultCirculation.setStartDate(resultSet.getTimestamp (START_DATE));
                    resultCirculation.setEndDate(resultSet.getTimestamp (END_DATE));
                    resultCirculation.setFacultyId(resultSet.getInt (FACULTY_ID));

                    circulations.add (resultCirculation);
                }
            }
        } catch (SQLException e) {
            LOGGER.error ("Failed to retrieve circulations.", e);
            // Handle any exceptions that occur during the database operation
            throw new DBConnectionFailedException("Failed to retrieve users.");
        }

        return circulations;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getTotalCirculationNumber(Circulation circulation, Transaction transaction) {
        LOGGER.debug("getTotalCirculationNumber() called.");
        String query = "SELECT COUNT(*) FROM " + TABLE + " WHERE 1=1";
        List<Object> parameters = new ArrayList<>();

        if (circulation != null) {
            if (circulation.getId () > 0) {
                query += " AND " + CIRCULATION_ID + " ILIKE ?";
                parameters.add ("%" + circulation.getId() + "%");
            }

            if (circulation.getTitle () != null) {
                query += " AND " + TITLE + " ILIKE ?";
                parameters.add ("%" + circulation.getTitle () + "%");
            }

            if (circulation.getDoctoralCandidateName () != null) {
                query += " AND " + DOC_CANDIDATE + " ILIKE ?";
                parameters.add ("%" + circulation.getDoctoralCandidateName() + "%");
            }

            if (circulation.getDoctoralSupervisor() != null) {
                query += " AND " + DOC_SUPERVISOR + " ILIKE ?";
                parameters.add ("%" + circulation.getDoctoralSupervisor() + "%");
            }

            if (circulation.getStartDate () != null) {
                query += " AND " + START_DATE + " >= ?";
                parameters.add (circulation.getStartDate());
            }

            if (circulation.getEndDate () != null) {
                query += " AND " + END_DATE + " <= ?";
                parameters.add (circulation.getEndDate());
            }
			/*
			if (circulation.isObligatory()) {
				query += "AND is_obligatory = true";
			} Don't know if this works.
			 */
            if (circulation.getFacultyId () > 0) {
                query += " AND " + FACULTY_ID + " = ?";
                parameters.add (circulation.getFacultyId());
            }
        }

        try (PreparedStatement statement = transaction.getConnection().prepareStatement (query)) {
            for (int i = 0; i < parameters.size (); i++) {
                statement.setObject (i + 1, parameters.get (i));
            }

            try (ResultSet resultSet = statement.executeQuery ()) {
                if (resultSet.next ()) {
                    return resultSet.getInt (1);
                }
            }
        } catch (SQLException e) {
            // Handle any specific exceptions or logging as needed
            LOGGER.error ("SQL exception in find getTotalCirculationNumber(): " + e.getMessage ());
            throw new DBConnectionFailedException ("Failed to retrieve circulations.", e);
        }
        return 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean findCirculationByTitle(Circulation circulation, Transaction transaction) {
        String query = "SELECT * FROM " + TABLE + " WHERE " + TITLE + " = ?";
        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query)) {
            statement.setString (1, circulation.getTitle ());
            ResultSet resultSet = statement.executeQuery ();
            if (resultSet.next ()) {
                // Set the retrieved circulation details in the provided circulation object
                circulation.setId(resultSet.getInt(CIRCULATION_ID));
                circulation.setDescription(resultSet.getString (DESCRIPTION));
                circulation.setDoctoralCandidateName(resultSet.getString (DOC_CANDIDATE));
                circulation.setDoctoralSupervisor(resultSet.getString (DOC_SUPERVISOR));
                circulation.setObligatory(resultSet.getBoolean (IS_OBLIGATORY));
                circulation.setStartDate(resultSet.getTimestamp (START_DATE));
                circulation.setEndDate(resultSet.getTimestamp (END_DATE));
                circulation.setFacultyId(resultSet.getInt (FACULTY_ID));
                return true; // Circulation with the title exists in the Database
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find circulation by title.", e);
            throw new DBConnectionFailedException("Failed to find circulation by title." , e);
        }
        return false;
    }

    @Override
    public List<Circulation> getAllCompletedCirculations(Circulation circulation, Transaction transaction, int offset, int count) {
        LOGGER.debug ("getAllCompletedCirculations() called.");
        List<Circulation> completedCirculations = new ArrayList<> ();

        // Build the SQL query string
        StringBuilder query = new StringBuilder ();
        query.append ("SELECT * FROM circulation WHERE 1=1");

        // Add filter conditions based on the provided properties
        if (circulation.getTitle () != null) {
            query.append (" AND LOWER(title) LIKE LOWER(?)");
        }

        if (circulation.getDescription () != null) {
            query.append (" AND LOWER(description) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralCandidateName () != null) {
            query.append (" AND LOWER(doctoral_candidate_name) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralSupervisor () != null) {
            query.append (" AND LOWER(doctoral_supervisor_name) LIKE LOWER(?)");
        }

        if (circulation.getStartDate () != null) {
            query.append (" AND " + START_DATE + " = ?");
        }

        if (circulation.getEndDate () != null) {
            query.append (" AND " + END_DATE + " = ?");
        }
        if(circulation.getFacultyId () !=0){
            query.append(" AND faculty_id = ?");
        }

        // Add the filter condition for completed Circulations (end_date < currentTimestamp)
        query.append (" AND end_date < ?");

        query.append (" LIMIT ? OFFSET ?");

        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query.toString ())) {
            int paramIndex = 1;

            // Set filter condition values
            if (circulation.getTitle () != null) {
                statement.setString (paramIndex++, "%" + circulation.getTitle () + "%");
            }

            if (circulation.getDescription () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDescription () + "%");
            }

            if (circulation.getDoctoralCandidateName () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralCandidateName () + "%");
            }

            if (circulation.getDoctoralSupervisor () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralSupervisor () + "%");
            }

            if (circulation.isObligatory ()) {
                statement.setBoolean (paramIndex++, circulation.isObligatory ());
            }

            if (circulation.getStartDate () != null) {
                statement.setObject (paramIndex++, circulation.getStartDate ());
            }

            if (circulation.getEndDate () != null) {
                statement.setObject (paramIndex++, circulation.getEndDate ());
            }
            if(circulation.getFacultyId ()!=0){
                statement.setInt(paramIndex++, circulation.getFacultyId ());
            }

            // Set the current timestamp for the filter condition (end_date < currentTimestamp)
            Timestamp currentTimestamp = new Timestamp (System.currentTimeMillis ());
            statement.setTimestamp (paramIndex++, currentTimestamp);

            // Set pagination parameters
            statement.setInt (paramIndex++, count);
            statement.setInt (paramIndex, offset);

            try (ResultSet resultSet = statement.executeQuery ()) {
                // Iterate over the result set and populate the list of completed circulations
                while (resultSet.next ()) {
                    Circulation resultCirculation = new Circulation ();
                    // Populate the circulation with data from the result set
                    resultCirculation.setId (resultSet.getInt (CIRCULATION_ID));
                    resultCirculation.setTitle (resultSet.getString (TITLE));
                    resultCirculation.setDescription (resultSet.getString (DESCRIPTION));
                    resultCirculation.setDoctoralCandidateName (resultSet.getString (DOC_CANDIDATE));
                    resultCirculation.setDoctoralSupervisor (resultSet.getString (DOC_SUPERVISOR));
                    resultCirculation.setObligatory (resultSet.getBoolean (IS_OBLIGATORY));
                    resultCirculation.setStartDate (resultSet.getTimestamp (START_DATE));
                    resultCirculation.setEndDate (resultSet.getTimestamp (END_DATE));
                    resultCirculation.setFacultyId (resultSet.getInt (FACULTY_ID));

                    completedCirculations.add (resultCirculation);
                }
            }
        } catch (SQLException e) {
            // Handle any exceptions that occur during the database operation
            e.printStackTrace ();
        }

        return completedCirculations;
    }

    @Override
    public List<Circulation> getAllCurrentCirculations(Circulation circulation, Transaction transaction, int offset, int count) {
        LOGGER.debug ("getAllAktulleCirculations() called.");
        List<Circulation> aktulleCirculations = new ArrayList<> ();

        // Build the SQL query string
        StringBuilder query = new StringBuilder ();
        query.append ("SELECT * FROM circulation WHERE 1=1");

        // Add filter conditions based on the provided properties
        if (circulation.getTitle () != null) {
            query.append (" AND LOWER(title) LIKE LOWER(?)");
        }

        if (circulation.getDescription () != null) {
            query.append (" AND LOWER(description) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralCandidateName () != null) {
            query.append (" AND LOWER(doctoral_candidate_name) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralSupervisor () != null) {
            query.append (" AND LOWER(doctoral_supervisor_name) LIKE LOWER(?)");
        }

        if (circulation.getStartDate () != null) {
            query.append (" AND " + START_DATE + " = ?");
        }

        if (circulation.getEndDate () != null) {
            query.append (" AND " + END_DATE + " = ?");
        }
        if(circulation.getFacultyId () != 0 ){
            query.append(" AND faculty_id = ?");
        }

        // Add the filter condition for aktulle Circulations (end_date > currentTimestamp)
        query.append (" AND end_date > ?");

        query.append (" LIMIT ? OFFSET ?");

        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query.toString ())) {
            int paramIndex = 1;

            // Set filter condition values
            if (circulation.getTitle () != null) {
                statement.setString (paramIndex++, "%" + circulation.getTitle () + "%");
            }

            if (circulation.getDescription () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDescription () + "%");
            }

            if (circulation.getDoctoralCandidateName () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralCandidateName () + "%");
            }

            if (circulation.getDoctoralSupervisor () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralSupervisor () + "%");
            }

            if (circulation.isObligatory ()) {
                statement.setBoolean (paramIndex++, circulation.isObligatory ());
            }

            if (circulation.getStartDate () != null) {
                statement.setObject (paramIndex++, circulation.getStartDate ());
            }

            if (circulation.getEndDate () != null) {
                statement.setObject (paramIndex++, circulation.getEndDate ());
            }
            if(circulation.getFacultyId () != 0){
                statement.setInt(paramIndex++, circulation.getFacultyId ());
            }

            // Set the current timestamp for the filter condition (end_date > currentTimestamp)
            Timestamp currentTimestamp = new Timestamp (System.currentTimeMillis ());
            statement.setTimestamp (paramIndex++, currentTimestamp);

            // Set pagination parameters
            statement.setInt (paramIndex++, count);
            statement.setInt (paramIndex, offset);

            try (ResultSet resultSet = statement.executeQuery ()) {
                // Iterate over the result set and populate the list of aktulle circulations
                while (resultSet.next ()) {
                    Circulation resultCirculation = new Circulation ();
                    // Populate the circulation with data from the result set
                    resultCirculation.setId (resultSet.getInt (CIRCULATION_ID));
                    resultCirculation.setTitle (resultSet.getString (TITLE));
                    resultCirculation.setDescription (resultSet.getString (DESCRIPTION));
                    resultCirculation.setDoctoralCandidateName (resultSet.getString (DOC_CANDIDATE));
                    resultCirculation.setDoctoralSupervisor (resultSet.getString (DOC_SUPERVISOR));
                    resultCirculation.setObligatory (resultSet.getBoolean (IS_OBLIGATORY));
                    resultCirculation.setStartDate (resultSet.getTimestamp (START_DATE));
                    resultCirculation.setEndDate (resultSet.getTimestamp (END_DATE));
                    resultCirculation.setFacultyId (resultSet.getInt (FACULTY_ID));

                    aktulleCirculations.add (resultCirculation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace ();
        }

        return aktulleCirculations;
    }


    @Override
    public int getTotalCurrentCirculationNumber(Circulation circulation, Transaction transaction) {
        LOGGER.debug ("getTotalCurrentCirculationNumber() called.");
        StringBuilder query = new StringBuilder ();
        query.append ("SELECT COUNT(*) FROM circulation WHERE DATE(end_date) > DATE(NOW())");

        // Add filter conditions based on the provided properties
        if (circulation.getTitle () != null) {
            query.append (" AND LOWER(title) LIKE LOWER(?)");
        }

        if (circulation.getDescription () != null) {
            query.append (" AND LOWER(description) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralCandidateName () != null) {
            query.append (" AND LOWER(doctoral_candidate_name) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralSupervisor () != null) {
            query.append (" AND LOWER(doctoral_supervisor_name) LIKE LOWER(?)");
        }

        if (circulation.getStartDate () != null) {
            query.append (" AND " + START_DATE + "= ?");
        }

        if (circulation.getEndDate () != null) {
            query.append (" AND " + END_DATE + " = ?");
        }
        if (circulation.getFacultyId() != 0) {
            query.append(" AND faculty_id = ?");
        }

        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query.toString ())) {
            int paramIndex = 1;

            // Set filter condition values
            if (circulation.getTitle () != null) {
                statement.setString (paramIndex++, "%" + circulation.getTitle () + "%");
            }

            if (circulation.getDescription () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDescription () + "%");
            }

            if (circulation.getDoctoralCandidateName () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralCandidateName () + "%");
            }

            if (circulation.getDoctoralSupervisor () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralSupervisor () + "%");
            }

            if (circulation.getStartDate () != null) {
                statement.setObject (paramIndex++, circulation.getStartDate ());
            }

            if (circulation.getEndDate () != null) {
                statement.setObject (paramIndex++, circulation.getEndDate ());
            }
            if (circulation.getFacultyId() != 0) {
                statement.setInt(paramIndex, circulation.getFacultyId());
            }

            try (ResultSet resultSet = statement.executeQuery ()) {
                if (resultSet.next ()) {
                    return resultSet.getInt (1);
                }
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException ("Failed to retrieve total number of current circulations.", e);
        }

        return -1;
    }

    public int getTotalCompletedCirculationNumber(Circulation circulation, Transaction transaction) {
        LOGGER.debug ("getTotalCompletedCirculationNumber() called.");
        StringBuilder query = new StringBuilder ();
        query.append ("SELECT COUNT(*) FROM circulation WHERE DATE(end_date) < DATE(NOW())");

        // Add filter conditions based on the provided properties
        if (circulation.getTitle () != null) {
            query.append (" AND LOWER(title) LIKE LOWER(?)");
        }

        if (circulation.getDescription () != null) {
            query.append (" AND LOWER(description) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralCandidateName () != null) {
            query.append (" AND LOWER(doctoral_candidate_name) LIKE LOWER(?)");
        }

        if (circulation.getDoctoralSupervisor () != null) {
            query.append (" AND LOWER(doctoral_supervisor_name) LIKE LOWER(?)");
        }

        if (circulation.getStartDate () != null) {
        query.append (" AND " + START_DATE + "= ?");
        }

        if (circulation.getEndDate () != null) {
            // Compare only the date part of the end_ column
            query.append (" AND DATE("+ END_DATE+") = DATE(?)");
        }
        if (circulation.getFacultyId() != 0) {
            query.append(" AND faculty_id = ?");
        }

        try (PreparedStatement statement = transaction.getConnection ().prepareStatement (query.toString ())) {
            int paramIndex = 1;

            // Set filter condition values
            if (circulation.getTitle () != null) {
                statement.setString (paramIndex++, "%" + circulation.getTitle () + "%");
            }

            if (circulation.getDescription () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDescription () + "%");
            }

            if (circulation.getDoctoralCandidateName () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralCandidateName () + "%");
            }

            if (circulation.getDoctoralSupervisor () != null) {
                statement.setString (paramIndex++, "%" + circulation.getDoctoralSupervisor () + "%");
            }

            if (circulation.getStartDate () != null) {
                statement.setObject (paramIndex++, circulation.getStartDate ());
            }

            if (circulation.getEndDate () != null) {
                // Set the date part of the end date
                statement.setObject (paramIndex++, circulation.getEndDate (), Types.DATE);
            }
            if (circulation.getFacultyId() != 0) {
                statement.setInt(paramIndex, circulation.getFacultyId());
            }

            try (ResultSet resultSet = statement.executeQuery ()) {
                if (resultSet.next ()) {
                    return resultSet.getInt (1);
                }
            }
        } catch (SQLException e) {
            throw new DBConnectionFailedException ("Failed to retrieve total number of completed circulations.", e);
        }

        return -1;
    }
}
