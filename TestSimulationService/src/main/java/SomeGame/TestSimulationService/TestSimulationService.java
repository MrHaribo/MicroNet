package SomeGame.TestSimulationService;

import java.net.URI;

import micronet.network.Context;
import micronet.network.IAdvisory;
import micronet.network.Request;
import micronet.network.factory.PeerFactory;

public class TestSimulationService {

	public static void main(String[] args) {
		Context context = new Context(PeerFactory.createPeer());
				
		context.getAdvisory().registerConnectionStateListener((String id, IAdvisory.ConnectionState state) -> {
			System.out.println("My ID: " + context.getPeer().getConnectionID());
			System.out.println("Other ID: " + id + " : " + state);
		});
		
		context.getPeer().listen("/open", request -> {
			System.out.println("Request open" + request.getData());
		});
		
		context.getPeer().sendRequest(URI.create("mn://world/instance/add"), new Request(context.getPeer().getConnectionID()), response -> {
			System.out.println("Free instance added " + response);
		});
	}
}
