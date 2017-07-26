package micronet.network;

/**
 * MicroNet response which encapsulates a payload and parameters
 * 
 * @author Jonas Biedermann
 *
 */
public class Response {
	private StatusCode status;
	private String data;

	private Parameters parameters = new Parameters();

	/**
	 * Creates a Response.
	 * @param status The {@link StatusCode} of the Response 
	 */
	public Response(StatusCode status) {
		this(status, "");
	}

	/**
	 * Creates a response and initalizes the payload with the data argument
	 * @param status The {@link StatusCode} of the Response 
	 * @param data The payload of the response in String representation
	 */
	public Response(StatusCode status, String data) {
		this.status = status;
		this.data = data;
	}

	/**
	 * Returns the {@link StatusCode} of the Response 
	 * @return The {@link StatusCode} of the response
	 */
	public StatusCode getStatus() {
		return status;
	}

	/**
	 * Returns the payload of the Response
	 * @return The payload of the Response in String representation
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the payload of the Request
	 * @param data The payload of the Response in String representation
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Returns the parameter map of the Response
	 * @return The parameter map of the Response
	 */
	public Parameters getParameters() {
		return parameters;
	}

	
	@Override
	public String toString() {
		return status + ":" + data;
	}
}