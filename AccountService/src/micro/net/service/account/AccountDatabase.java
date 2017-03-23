package micro.net.service.account;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PGobject;

import micronet.core.database.Database;
import micronet.core.serialization.Serialization;
import micronet.service.model.CredentialValues;
import micronet.service.model.UserValues;

public class AccountDatabase extends Database {

	public AccountDatabase() {
		super("account_db", "account_service", "account1234");
	}

	public UserValues getUser(int id) {
		try {
			String queryString = "SELECT credentials FROM users WHERE id = ?;";
			PreparedStatement stmt = getConnection().prepareStatement(queryString);
			stmt.setInt(1, id);

			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				String data = result.getString(1);
				CredentialValues credentials = Serialization.deserialize(data, CredentialValues.class);
				return new UserValues(id, credentials);
			}
			stmt.close();
		} catch (SQLException sqle) {
			System.err.println("Error running the select: " + sqle.getMessage());
		}
		return null;
	}

	public UserValues getUser(String name) {
		try {
			String queryString = "SELECT * FROM users WHERE credentials->>'username' = ?;";
			PreparedStatement stmt = getConnection().prepareStatement(queryString);
			stmt.setString(1, name);

			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				String data = result.getString(2);
				int id = result.getInt(1);
				CredentialValues credentials = Serialization.deserialize(data, CredentialValues.class);
				return new UserValues(id, credentials);
			}
			stmt.close();
		} catch (SQLException sqle) {
			System.err.println("Error running the select: " + sqle.getMessage());
		}
		return null;
	}

	public boolean addUser(CredentialValues credentials) {
		try {
			if (credentials == null)
				return false;

			String data = Serialization.serialize(credentials);

			PGobject dataObject = new PGobject();
			dataObject.setType("json");
			dataObject.setValue(data);

			PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO users (credentials) VALUES (?)");
			stmt.setObject(1, dataObject);

			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException sqle) {
			System.err.println("Something exploded running the insert: " + sqle.getMessage());
		}
		return false;
	}
}
