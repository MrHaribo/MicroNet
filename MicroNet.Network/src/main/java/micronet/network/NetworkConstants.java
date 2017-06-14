package micronet.network;

public enum NetworkConstants {
	USER_REQUEST,
	USER_ID,
	EVENT;
	
	public static final String REQUEST_QUEUE = "mn://request";
	public static final String COMMAND_QUEUE = "mn://cmd";
}
