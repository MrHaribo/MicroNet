package micronet.network;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IPeer {

	// URI: [scheme:][//host][path][?query][#fragment]
	// Example: mn://account/login

	void startup(URI host);

	void shutdown();

	void sendRequest(URI destination, Request request);

	void sendRequest(URI destination, Request request, Consumer<Response> handler);

	Response sendRequestBlocking(URI destination, Request request);

	Response sendRequestBlocking(URI destination, Request request, int timeout);

	void listen(String path, Consumer<Request> handler);

	void listen(String path, Function<Request, Response> handler);

	IAdvisory getAdvisory();
	
	String getConnectionID();
}
