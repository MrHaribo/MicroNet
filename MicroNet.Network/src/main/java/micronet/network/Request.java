package micronet.network;

public class Request {
	private String data;
	private Parameters parameters = new Parameters();

	public Request() {
	}

	public Request(String data) {
		this.data = data;
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
}
