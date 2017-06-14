package micronet.network.factory;

import micronet.activemq.AMQClientPeer;
import micronet.activemq.AMQPeer;
import micronet.network.IPeer;

public class PeerFactory {
	public static IPeer createPeer() {
		return new AMQPeer();
	}
	public static IPeer createClientPeer() {
		return new AMQClientPeer();
	}
}
