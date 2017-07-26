package micronet.network;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The networking interface that can be used to participate in a MicroNet
 * application. It allows messaging according to the MicroNet Shared API
 * concept.
 * 
 * @author Jonas Biedermann
 *
 */
public interface IPeer {

	public static int TTT = 42;

	// URI: [scheme:][//host][path][?query][#fragment]
	// Example: mn://account/login

	/**
	 * Startup registers the provided service host URI with the underlying
	 * network.
	 * 
	 * @param host
	 *            URI of the service for example "mn://my_service"
	 */
	void startup(URI host);

	/**
	 * Shutdown unregisters the service URI with the underlying network and
	 * closes the connection.
	 */
	void shutdown();

	/**
	 * Send a request to a Shared API destination
	 * 
	 * @param destination
	 *            The destination address, for example
	 *            "mn://my_service/my/method"
	 * @param request
	 *            The MicroNet Request containing Parameters and a Payload
	 */
	void sendRequest(URI destination, Request request);

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
	void sendRequest(URI destination, Request request, Consumer<Response> handler);

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
	Response sendRequestBlocking(URI destination, Request request);

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
	Response sendRequestBlocking(URI destination, Request request, int timeout);

	/**
	 * Listen on a queue on the provided path relative to the service URI. No
	 * response is expected for this version of listen
	 * 
	 * @param path
	 *            Path of the queue like for example "/my/path"
	 * @param handler
	 *            Callback to inject the message handling code using lambda
	 *            expressions
	 */
	void listen(String path, Consumer<Request> handler);

	/**
	 * Listen on a queue on the provided path relative to the service URI and
	 * return a immediate response
	 * 
	 * @param path
	 *            Path of the queue like for example "/my/path"
	 * @param handler
	 *            Callback to inject the message handling code using lambda
	 *            expressions. The injected handler is expected to return a
	 *            Response. This is the only differentiation to
	 *            {@link IPeer#listen(String, Consumer)}
	 */
	void listen(String path, Function<Request, Response> handler);

	/**
	 * Access to the advisory system of MicroNet
	 * @return Instance of the advisory system through the {@link IAdvisory} interface
	 */
	IAdvisory getAdvisory();

	/**
	 * Returns the connection ID of this peer
	 * @return String representation of the connection ID
	 */
	String getConnectionID();
}
