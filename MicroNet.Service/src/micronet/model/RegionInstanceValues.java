package micronet.model;

public class RegionInstanceValues {
	private ID regionID;
	private String host;
	private int port;

	public ID getRegionID() {
		return regionID;
	}

	public void setRegionID(ID regionID) {
		this.regionID = regionID;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}