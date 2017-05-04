package micronet.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static final String databaseAddress = System.getenv("database_address") != null ? 
			"jdbc:postgresql://" + System.getenv("database_address") + "/" : "jdbc:postgresql://localhost:5432/";

	private Connection connection = null;
	
	String databaseName;
	String username;
	String password;

	public Database(String databaseName, String username, String password) {
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		
		System.out.println("-------- PostgreSQL " + "JDBC Connection Opening ------------");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		
		refreshConnection();
		
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
	
	private void refreshConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				return;
			}
			connection = DriverManager.getConnection(databaseAddress + databaseName, username, password);
			System.out.println("PostgreSQL JDBC Connection Refreshed!");
		} catch (SQLException e) {
			System.err.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	protected Connection getConnection() {
		refreshConnection();
		return connection;
	}

	public void removeArrayElement(Object[] array, int index) {
		System.arraycopy(array, index + 1, array, index, array.length - 1 - index);
	}
}
