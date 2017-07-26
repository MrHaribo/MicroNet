package micronet.network.factory;

import micronet.activemq.AMQClientPeer;
import micronet.activemq.AMQPeer;
import micronet.network.IPeer;

/**
 * Factory class to create a Peer according to the abstracted underlying
 * networking technology
 * 
 * @author Jonas Biedermann
 *
 */
public class PeerFactory {
	/**
	 * Creates a universal Peer to participate in a MicroNet application
	 * 
	 * @return Instance of IPeer
	 */
	public static IPeer createPeer() {
		return new AMQPeer();
	}

	/**
	 * Creates a client Peer which has limited access to the Shared API. Client
	 * Peers require an API Gateway to communicate with the MicroNet
	 * application.
	 * 
	 * @return Client Instance of IPeer
	 */
	public static IPeer createClientPeer() {
		return new AMQClientPeer();
	}
}
