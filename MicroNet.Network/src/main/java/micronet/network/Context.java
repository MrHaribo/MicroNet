package micronet.network;

import java.net.URI;
import java.util.function.Consumer;

/**
 * This class is injected into MicroNet services via the message handlers and
 * the start and stop methods
 * 
 * @author Jonas Biedermann
 *
 */
public class Context {

	private IPeer peer;

	/**
	 * Creates a Context from a Peer using the Peer's connection id as Service
	 * URI ("mn://connection_id")
	 * 
	 * @param peer
	 *            Peer instance the context is representing
	 */
	public Context(IPeer peer) {
		this(peer, "mn://" + peer.getConnectionID());
	}

	/**
	 * Creates a Context from a Peer using the provided hostAddress as Service
	 * URI ("mn://hostAddress")
	 * 
	 * @param peer
	 *            Peer instance the context is representing
	 * @param hostAddress
	 *            The Service Address the Peer will listen to
	 */
	public Context(IPeer peer, String hostAddress) {
		this.peer = peer;
		if (peer != null) {

			peer.startup(URI.create(hostAddress));

			peer.listen("/shutdown", (Request request) -> {
				shutdown();
			});
		}
	}

	/**
	 * Provides the underlying Peer for direct access bypassing the Context
	 * 
	 * @return The underlying Peer
	 */
	public IPeer getPeer() {
		return peer;
	}

	/**
	 * Provides the MicroNet Advisory System provided by the underlying Peer
	 * 
	 * @return The Advisory System Interface
	 */
	public IAdvisory getAdvisory() {
		return peer.getAdvisory();
	}

	/**
	 * Send a request to a Shared API destination
	 * 
	 * @param destination
	 *            The destination address, for example
	 *            "mn://my_service/my/method"
	 * @param request
	 *            The MicroNet Request containing Parameters and a Payload
	 */
	public void sendRequest(String destination, Request request) {
		peer.sendRequest(URI.create(destination), request);
	}

	/**
	 * Send a request to a Shared API destination and expect an asynchronous
	 * response within the default timeout
	 * {@link NetworkConstants#SYNC_REQUEST_TIMEOUT}
	 * 
	 * @see NetworkConstants#SYNC_REQUEST_TIMEOUT
	 * 
	 * @param destination
	 * @param request
	 * @param handler
	 */
	public void sendRequest(String destination, Request request, Consumer<Response> handler) {
		peer.sendRequest(URI.create(destination), request, handler);
	}

	/**
	 * Send a request to a Shared API destination and wait for a synchronous
	 * response within the default timeout
	 * 
	 * @param destination
	 *            The destination address, for example
	 *            "mn://my_service/my/method"
	 * @param request
	 *            The MicroNet Request containing Parameters and a Payload
	 * @return The response to the request or a response with
	 *         {@link StatusCode#REQUEST_TIMEOUT}
	 */
	public Response sendRequestBlocking(String destination, Request request) {
		return peer.sendRequestBlocking(URI.create(destination), request);
	}

	/**
	 * Send a request to a Shared API destination and wait for a synchronous
	 * response for the specified timeout
	 * 
	 * @param destination
	 *            The destination address, for example
	 *            "mn://my_service/my/method"
	 * @param request
	 *            The MicroNet Request containing Parameters and a Payload
	 * @param timeout
	 *            The time the method blocks before returning a
	 *            {@link StatusCode#REQUEST_TIMEOUT} response
	 * @return The response to the request or a response with
	 *         {@link StatusCode#REQUEST_TIMEOUT}
	 */
	public Response sendRequestBlocking(String destination, Request request, int timeout) {
		return peer.sendRequestBlocking(URI.create(destination), request, timeout);
	}

	/**
	 * Helper method to send Events to specific Users
	 * 
	 * @param userID
	 *            User ID of the User the event is sent to
	 * @param eventName
	 *            Name of the event
	 * @param data
	 *            Payload of the event in String representation
	 */
	public void sendEvent(int userID, String eventName, String data) {
		// TODO: Use Event Codes instead of EventName
		Request event = new Request(data);
		event.getParameters().set(NetworkConstants.USER_ID, userID);
		event.getParameters().set(NetworkConstants.EVENT, eventName);
		sendRequest("mn://gateway/forward/event", event);
	}

	/**
	 * Helper method to send Events to specific Users in Request representation
	 * which allows to add parameters to the event
	 * 
	 * @param userID
	 *            User ID of the User the event is sent to
	 * @param eventName
	 *            Name of the event
	 * @param request
	 *            Payload of the event in String representation
	 */
	public void sendEvent(int userID, String eventName, Request request) {
		request.getParameters().set(NetworkConstants.USER_ID, userID);
		request.getParameters().set(NetworkConstants.EVENT, eventName);
		sendRequest("mn://gateway/forward/event", request);
	}

	/**
	 * Broadcast an event to all Users connected to the application and is
	 * manily used for application wide announcements
	 * 
	 * @param eventName
	 *            Name of the event
	 * @param data
	 *            Payload of the event in String representation
	 */
	public void broadcastEvent(String eventName, String data) {
		Request event = new Request(data);
		event.getParameters().set(NetworkConstants.EVENT, eventName);
		sendRequest("mn://gateway/broadcast/event", event);
	}

	/**
	 * Closes the underlying Peer
	 */
	public void shutdown() {
		peer.shutdown();
		System.out.println("Peer Closed!!!");
	}
}
