package micronet.activemq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.RemoveInfo;

import micronet.network.IAdvisory;
import micronet.network.IAdvisory.ConnectionState;

public class AMQAdvisory implements IAdvisory {

	private Session session;
	protected ExecutorService threadPool;

	private Map<String, MessageConsumer> advisoryConsumers = new HashMap<>();

	public AMQAdvisory(Session session, ExecutorService threadPool) {
		this.session = session;
		this.threadPool = threadPool;
	}

	public void shutdown() {
		try {
			for (MessageConsumer consumer : advisoryConsumers.values()) {
				consumer.close();
			}
		} catch (JMSException e) {
			System.err.println("Error closing Advisory Consumers: " + e);
		}
	}

	@Override
	public void send(String advisoryName, String msg) {
		try {
			Destination dest = session.createTopic(advisoryName);
			MessageProducer producer = session.createProducer(null);
			TextMessage textMessage = session.createTextMessage(msg);
			producer.send(dest, textMessage);
		} catch (JMSException e) {
			System.err.println("Error sending Advisory Message: " + e);
		}
	}

	@Override
	public void listen(String advisoryName, Consumer<String> handler) {
		try {
			Destination dest = session.createTopic(advisoryName);
			MessageConsumer consumer = session.createConsumer(dest);
			consumer.setMessageListener((Message msg) -> {
				threadPool.submit(() -> {
					if (msg instanceof TextMessage) {
						try {
							TextMessage textMessage = (TextMessage) msg;
							handler.accept(textMessage.getText());
						} catch (Exception e) {
							System.err.println("Error listening Advisory Message: " + e);
						}
					}
				});
			});
		} catch (JMSException e) {
			System.err.println("Error processing Advisory Message: " + e);
		}
	}
	
	@Override
	public void registerConnectionStateListener(BiConsumer<String, ConnectionState> onStateChanged) {
		try {
			Destination dest = session.createTopic("ActiveMQ.Advisory.Connection");
			MessageConsumer consumer = session.createConsumer(dest);
			consumer.setMessageListener((Message msg) -> {
				if (msg instanceof ActiveMQMessage) {
					ActiveMQMessage aMsg = (ActiveMQMessage) msg;
					if (aMsg.getDataStructure() instanceof ConnectionInfo) {
						ConnectionInfo dataStructure = (ConnectionInfo) aMsg.getDataStructure();
						String clientID = AMQBasePeer.TrimConnectionID(dataStructure.getConnectionId().getValue());
						threadPool.submit(() -> onStateChanged.accept(clientID, ConnectionState.CONNECTED));
					}
					if (aMsg.getDataStructure() instanceof RemoveInfo) {
						RemoveInfo dataStructure = (RemoveInfo) aMsg.getDataStructure();
						ConnectionId connectionId = (ConnectionId) dataStructure.getObjectId();
						String clientID = AMQBasePeer.TrimConnectionID(connectionId.getValue());
						threadPool.submit(() -> onStateChanged.accept(clientID, ConnectionState.DISCONNECTED));
					}
				}
			});
			advisoryConsumers.put("ActiveMQ.Advisory.Connection", consumer);
		} catch (JMSException e) {
			System.err.println("Error processing Advisory Message: " + e);
		}
	}

	@Override
	public void registerQueueStateListener(String queueName, Consumer<QueueState> queueStateChanged) {
		try {
			Destination dest = session.createTopic("ActiveMQ.Advisory.Consumer.Queue." + queueName);
			MessageConsumer consumer = session.createConsumer(dest);
			consumer.setMessageListener((Message msg) -> {
				if (msg instanceof ActiveMQMessage) {
					ActiveMQMessage aMsg = (ActiveMQMessage) msg;
					if (aMsg.getDataStructure() instanceof ConsumerInfo) {
						threadPool.submit(() -> queueStateChanged.accept(IAdvisory.QueueState.OPEN));
					}
					if (aMsg.getDataStructure() instanceof RemoveInfo) {
						threadPool.submit(() -> queueStateChanged.accept(IAdvisory.QueueState.CLOSE));
					}
				}
			});
			advisoryConsumers.put(queueName, consumer);
		} catch (JMSException e) {
			System.err.println("Error processing Advisory Message: " + e);
		}
	}

	@Override
	public void unregisterQueueStateListener(String queueName) {
		try {
			MessageConsumer consumer = advisoryConsumers.remove(queueName);
			if (consumer != null) {
				consumer.close();
			}
		} catch (JMSException e) {
			System.err.println("Error closing Advisory Consumer: " + e);
		}
	}

}
