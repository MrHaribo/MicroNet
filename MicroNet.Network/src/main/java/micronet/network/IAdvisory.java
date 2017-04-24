package micronet.network;

import java.util.function.Consumer;

public interface IAdvisory {

	public enum QueueState {
		OPEN, CLOSE
	}

	public void registerQueueStateListener(String queueName, Consumer<QueueState> onStateChanged);

	public void unregisterQueueStateListener(String queueName);

	public void listen(String advisoryName, Consumer<String> handler);

	public void send(String advisoryName, String msg);
}
