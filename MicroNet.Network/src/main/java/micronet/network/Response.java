package micronet.network;

public class Response {
	private StatusCode status;
	private String data;

	private Parameters parameters = new Parameters();

	public Response(StatusCode status) {
		this(status, "");
	}

	public Response(StatusCode status, String data) {
		this.status = status;
		this.data = data;
	}

	public StatusCode getStatus() {
		return status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Parameters getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return status + ":" + data;
	}
}