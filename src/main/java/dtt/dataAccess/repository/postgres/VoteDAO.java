package dtt.dataAccess.repository.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dtt.dataAccess.exceptions.*;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Options;
import dtt.global.tansport.Vote;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Postgres implementation for a class handling database access related to votes.
 * 
 * @author Stefan Witka
 *
 */
@Named
@ApplicationScoped
@Default
public class VoteDAO implements dtt.dataAccess.repository.interfaces.VoteDAO {

	private static final String VOTE_ID = "vote_id";
	private static final String USER_ID = "user_id";
	private static final String CIRCULATION_ID = "circulation_id";
	private static final String CHOICE = "choice";
	private static final String REASON = "reason";
	private static final Logger LOGGER = LogManager.getLogger(VoteDAO.class);

	/**
	 * Constructor for VotesDAO
	 */
	public VoteDAO() {

	}

	private void setVoteStatement(Vote vote, PreparedStatement statement) throws SQLException {
		statement.setInt(1, vote.getUserId());
		statement.setInt(2, vote.getCirculationId());
		statement.setInt(3, vote.getSelection ().getValue());
		statement.setString(4, vote.getDescription());
	}

	@Override
	public void add(Vote vote, Transaction transaction) throws DataNotCompleteException, InvalidInputException,DBConnectionFailedException {
		LOGGER.debug("add() called.");
		String query = "INSERT INTO vote (user_id, circulation_id, choice, reason) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
			setVoteStatement(vote, statement);

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new InvalidInputException("Creating vote failed, no rows affected");
			}
		} catch (SQLException e) {
			switch (e.getSQLState()) {
				case "23502": // The specific SQL error code for a not-null constraint violation
					throw new DataNotCompleteException(e.getLocalizedMessage(), e);
				default:
					DBConnectionFailedException ex = new DBConnectionFailedException();
					ex.initCause(e); // Preserve the original SQLException as the cause
					throw ex;
			}
		}

	}

	@Override
	public void remove(Vote vote, Transaction transaction) throws DataNotFoundException, InvalidInputException {
		LOGGER.debug("remove() called.");
		String query = "DELETE FROM vote WHERE vote_id = ?";
		try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
			statement.setInt(1, vote.getVoteId ());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new DataNotFoundException("Vote not found in the database");
			}
		} catch (SQLException e) {
			throw new DataNotFoundException("Vote not found in the database.", e);
		}

	}

	@Override
	public void update(Vote vote, Transaction transaction) throws DataNotFoundException, InvalidInputException {
		LOGGER.debug("update() called.");
		String query = "UPDATE vote SET user_id = ?, circulation_id = ?, choice = ?, reason = ? WHERE vote_id = ?";

		try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
			setVoteStatement(vote, statement);
			statement.setInt(5, vote.getVoteId ());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new DataNotFoundException("Update failed, no vote with provided ID found");
			}
		} catch (SQLException e) {
			throw new DBConnectionFailedException("Failed to update vote.", e);
		}

	}

	@Override
	public List<Vote> getVotes(Vote vote, Transaction transaction) {
		LOGGER.debug("getVotes() called.");
		List<Vote> votes = new ArrayList<>();

		String query = "SELECT * FROM vote WHERE circulation_id = ?";
		try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
			statement.setInt(1, vote.getCirculationId());

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Vote dbVote = new Vote();
					dbVote.setVoteId(rs.getInt(VOTE_ID));
					dbVote.setUser(rs.getInt(USER_ID));
					dbVote.setCirculation(rs.getInt(CIRCULATION_ID));
					dbVote.setSelection(Options.fromValue(rs.getInt(CHOICE)));
					dbVote.setDescription(rs.getString(REASON));
					votes.add(dbVote);
				}
			}
		} catch (SQLException e) {
			throw new DBConnectionFailedException("Failed to get votes.", e);
		}

		return votes;
	}


	@Override
	public boolean findVote(Vote vote, Transaction transaction) {
		LOGGER.debug("findVote() called.");
		String query = "SELECT * FROM vote WHERE circulation_id = ? and user_id = ?";
		try (PreparedStatement statement = transaction.getConnection().prepareStatement(query)) {
			statement.setInt(1, vote.getCirculationId());
			statement.setInt(2, vote.getUserId ());

			try (ResultSet rs = statement.executeQuery()) {
				if(rs.next()){
					vote.setVoteId(rs.getInt(VOTE_ID));
					vote.setUser(rs.getInt(USER_ID));
					vote.setCirculation(rs.getInt(CIRCULATION_ID));
					vote.setSelection(Options.fromValue(rs.getInt(CHOICE)));
					vote.setDescription(rs.getString(REASON));
					return true;
				}

			}
		} catch (SQLException e) {
			throw new DBConnectionFailedException("Failed to find vote.", e);
		}
		return false;
	}
	}

