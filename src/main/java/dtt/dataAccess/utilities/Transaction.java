package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Stefan Witka
 *
 */
public class Transaction implements AutoCloseable {
	private Connection connection; // connection on which the Transaction is run
	private boolean didCommit; // boolean to check if a successful commit took place
	
	/**
	 * Constructor for the transaction
	 */
	public Transaction() {
		didCommit = false;
		connection = getConnection();
	}
	
	/**
	 * Aborts the transaction and rolls back any changes made within the transaction.
	 * 
	 * @throws SQLException if there is an error aborting the transaction
	 */
	public void abort() throws SQLException {
		// TODO: Implement abort logic
	};
	
	/**
	 * Commits the transaction, persisting any changes made within the transaction.
	 * 
	 * @throws SQLException if there is an error committing the transaction
	 */
	public void commit() throws SQLException {
		// TODO: Implement commit logic
	};
	
	/**
	 * Retrieves the underlying connection associated with the transaction.
	 * 
	 * @return The connection associated with the transaction.
	 */
	public Connection getConnection() {
		// TODO: Implement getConnection logic
		return null;
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException if there is an error closing the transaction
	 */
	@Override
	public void close() throws SQLException {
		// TODO: Implement close logic
	}
}
