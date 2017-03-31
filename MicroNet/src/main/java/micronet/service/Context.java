package micronet.service;

import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

import micronet.model.ParameterCode;
import micronet.network.IAdvisory;
import micronet.network.IPeer;
import micronet.network.Request;
import micronet.network.Response;
import micronet.network.StatusCode;

public class Context {

	private IPeer peer;
	
	public Context(IPeer peer, String hostAddress) {
		this.peer = peer;
		if (peer != null) {

			peer.startup(URI.create(hostAddress));

			peer.listen("/shutdown", (Request request) -> {
				shutdown();
				return new Response(StatusCode.OK);
			});
		}
	}

	public IPeer getPeer() {
		return peer;
	}

	public IAdvisory getAdvisory() {
		return peer.getAdvisory();
	}

	public void sendRequest(String destination, Request request) {
		peer.sendRequest(URI.create(destination), request);
	}

	public void sendRequest(String destination, Request request, Consumer<Response> handler) {
		peer.sendRequest(URI.create(destination), request, handler);
	}

	public Response sendRequestBlocking(String destination, Request request) {
		return peer.sendRequestBlocking(URI.create(destination), request);
	}

	public Response sendRequestBlocking(String destination, Request request, int timeout) {
		return peer.sendRequestBlocking(URI.create(destination), request, timeout);
	}

	public void sendEvent(int userID, String eventName, String data) {
		// TODO: Use Event Codes instead of EventName
		Request event = new Request(data);
		event.getParameters().set(ParameterCode.USER_ID, userID);
		event.getParameters().set(ParameterCode.EVENT, eventName);
		sendRequest("mn://gateway/forward/event", event);
	}

	public void shutdown() {
		peer.shutdown();
		System.out.println("Peer Closed!!!");
	}
	
}