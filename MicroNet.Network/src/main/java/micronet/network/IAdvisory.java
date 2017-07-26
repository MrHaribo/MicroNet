package micronet.network;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Interface for the Advisory System of MicroNet
 * 
 * @author Jonas Biedermann
 *
 */
public interface IAdvisory {

	/**
	 * The state of a queue
	 */
	public enum QueueState {
		OPEN, CLOSE
	}

	/**
	 * The state of a connection
	 */
	public enum ConnectionState {
		CONNECTED, DISCONNECTED
	}

	/**
	 * Registers a listener for queue state changes
	 * 
	 * @param queueName
	 *            Name of the queue to be observed
	 * @param onStateChanged
	 *            Callback called on state change
	 */
	public void registerQueueStateListener(String queueName, Consumer<QueueState> onStateChanged);

	/**
	 * Registers a listener for connection state changes of peers
	 * 
	 * @param onStateChanged
	 *            Callback called on state change providing the connection ID
	 *            and the state
	 */
	public void registerConnectionStateListener(BiConsumer<String, ConnectionState> onStateChanged);

	/**
	 * Unregister queue state listeners
	 * 
	 * @param queueName
	 *            Name of the queue to be observed
	 */
	public void unregisterQueueStateListener(String queueName);

	/**
	 * Listen on an arbitrary advisory queue
	 * 
	 * @param advisoryName
	 *            Name of the advisory. No restrictions are implied upon
	 *            advisory names. Queue names are similar to #hashtags
	 * @param handler Callback on the reception of an advisory. 
	 */
	public void listen(String advisoryName, Consumer<String> handler);

	/**
	 * Send a String message on an arbitrary advisory queue
	 * 
	 * @param advisoryName
	 *            Name of the advisory. No restrictions are implied upon
	 *            advisory names. Queue names are similar to #hashtags
	 * @param msg
	 *            Content of the advisory. ToString() methods of primitives can
	 *            be used for serialization and Json Strings can be used for more
	 *            sophisticated serialization
	 */
	public void send(String advisoryName, String msg);
}
