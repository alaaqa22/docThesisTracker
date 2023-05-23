package dtt.dataAccess.utilities;

import java.sql.Connection;

public class Transaction implements AutoCloseable {
	private Connection connection;

	public void abort() {
	};

	public void commit() {
	};

	public void getConnection() {
	};

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

}
