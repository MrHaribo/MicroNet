package micronet.core.service;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseService {
/*
	private IPeer peer;

	public void start(URI host) {

		peer = createPeer();
		onBeforeStart();
		if (peer != null) {

			peer.startup(host);

			peer.listen("/shutdown", (Request request) -> {
				shutdown();
				return new Response(StatusCode.OK);
			});
		}
		onStart();
	}

	protected abstract IPeer createPeer();

	protected IPeer getPeer() {
		return peer;
	}

	protected IAdvisory getAdvisory() {
		return peer.getAdvisory();
	}

	protected void onBeforeStart() {
	}

	protected void onStart() {
	}

	protected void onStop() {
	}

	protected void listen(String path, Consumer<Request> handler) {
		peer.listen(path, handler);
	}

	protected void listen(String path, Function<Request, Response> handler) {
		peer.listen(path, handler);
	}

	protected void sendRequest(URI destination, Request request) {
		peer.sendRequest(destination, request);
	}

	protected void sendRequest(URI destination, Request request, Consumer<Response> handler) {
		peer.sendRequest(destination, request, handler);
	}

	protected Response sendRequestBlocking(URI destination, Request request) {
		return peer.sendRequestBlocking(destination, request);
	}

	protected Response sendRequestBlocking(URI destination, Request request, int timeout) {
		return peer.sendRequestBlocking(destination, request, timeout);
	}

	protected void sendEvent(int userID, String eventName, String data) {
		// TODO: Use Event Codes instead of EventName
		Request event = new Request(data);
		event.getParameters().set(ParameterCode.USER_ID, userID);
		event.getParameters().set(ParameterCode.EVENT, eventName);
		sendRequest(URI.create("mn://gateway/forward/event"), event);
	}

	public void shutdown() {
		onStop();

		peer.shutdown();
		System.out.println("Peer Closed!!!");

		System.exit(0);
	}
	*/
}
