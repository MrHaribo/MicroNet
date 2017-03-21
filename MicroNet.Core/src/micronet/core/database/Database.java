package micronet.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import micronet.core.config.Config;

public class Database {

	private final String databaseHost = Config.getString("database_host");
	private final int databasePort = Config.getInt("database_port");
	private final String HOST_ADDRESS = "jdbc:postgresql://" + databaseHost + ":" + databasePort + "/";

	private Connection connection = null;

	public Database(String databaseName, String username, String password) {
		System.out.println("-------- PostgreSQL " + "JDBC Connection Opening ------------");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");

		try {
			connection = DriverManager.getConnection(HOST_ADDRESS + databaseName, username, password);
		} catch (SQLException e) {
			System.err.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}
		System.out.println("PostgreSQL JDBC Started!");
	}

	public void shutdown() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("PostgreSQL JDBC Shutdown Failed!");
			e.printStackTrace();
		}
	}

	protected Connection getConnection() {
		return connection;
	}

	public void removeArrayElement(Object[] array, int index) {
		System.arraycopy(array, index + 1, array, index, array.length - 1 - index);
	}
}
