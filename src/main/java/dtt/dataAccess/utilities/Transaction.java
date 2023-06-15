package dtt.dataAccess.utilities;

import java.sql.Connection;
import java.sql.SQLException;

import dtt.dataAccess.exceptions.DBConnectionFailedException;

/**
 * Class handling transactions on a Postgres Database.
 * 
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
		connection = ConnectionPool.getInstance().getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DBConnectionFailedException("Error disabling AutoCommit", e);
		}
	}

	/**
	 * Aborts the transaction and rolls back any changes made within the
	 * transaction.
	 * 
	 * @throws SQLException if there is an error aborting the transaction
	 */
	public void abort() {
		if (!didCommit) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new DBConnectionFailedException("Error aborting the Transaction", e);
			}
		}
	};

	/**
	 * Commits the transaction, persisting any changes made within the transaction.
	 * 
	 * @throws SQLException if there is an error committing the transaction
	 */
	public void commit() {
		if (!didCommit) {
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new DBConnectionFailedException("Error committing transaction", e);
			}
			didCommit = true;
		}
	};

	/**
	 * Retrieves the underlying connection associated with the transaction.
	 * 
	 * @return The connection associated with the transaction.
	 */
	public Connection getConnection() {
		return connection;
	};

	/**
	 * {@inheritDoc}
	 * 
	 * @throws SQLException if there is an error closing the transaction
	 */
	@Override
	public void close() {
		if (!didCommit) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new DBConnectionFailedException("Rollback failed", e);
			}
			didCommit = true;
		}
		ConnectionPool.getInstance().releaseConnection(connection);
	}
}
