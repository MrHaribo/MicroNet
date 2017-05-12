package micronet.network.factory;

import micronet.activemq.AMQPeer;
import micronet.network.IPeer;

public class PeerFactory {
	public static IPeer createPeer() {
		return new AMQPeer();
	}
}
