package micronet.network;

/**
 * Network Constants to semantically compose the networking system
 * @author Jonas Biedermann
 *
 */
public enum NetworkConstants {
	USER_ID(0),
	USER_REQUEST(1),
	EVENT(2);
	
	private int code;
	
	private NetworkConstants(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

	public static final int SYNC_REQUEST_TIMEOUT = 10000;
	public static final String STATUS_CODE_PROPERTY = "code";
	public static final String PARAMETER_PROPERTY = "param";
	public static final String PATH_PROPERTY = "path";
	
	public static final String REQUEST_QUEUE = "mn://request";
	public static final String COMMAND_QUEUE = "mn://cmd";
}
