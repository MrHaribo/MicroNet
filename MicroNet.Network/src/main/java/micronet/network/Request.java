package micronet.network;

/**
 * MicroNet request which encapsulates a payload and parameters
 * 
 * @author Jonas Biedermann
 *
 */
public class Request {
	private String data;
	private Parameters parameters = new Parameters();

	/**
	 * Creates an empty Request
	 */
	public Request() {
	}

	/**
	 * Creates a Request and initialized the payload with the data parameter
	 * @param data Initial payload of the Request
	 */
	public Request(String data) {
		this.data = data;
	}

	/**
	 * Returns the payload of the Request
	 * @return The payload in String representation
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the payload of the Request
	 * @param data The payload in String representation
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Returns the parameter map of the Request
	 * @return The parameter map of the Request
	 */
	public Parameters getParameters() {
		return parameters;
	}
	
	@Override
	public String toString() {
		return data;
	}
}
