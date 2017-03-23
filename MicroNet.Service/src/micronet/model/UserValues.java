package micronet.model;

public class UserValues {
	private int id;
	private CredentialValues credentials;

	public UserValues() {
	}

	public UserValues(int id, CredentialValues credentials) {
		this.id = id;
		this.credentials = credentials;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CredentialValues getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialValues credentials) {
		this.credentials = credentials;
	}

	public String getName() {
		return credentials.getUsername();
	}
}
