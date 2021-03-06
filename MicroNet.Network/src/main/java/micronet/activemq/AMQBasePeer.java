package micronet.activemq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class AMQBasePeer {

	private static BrokerService broker = null;

	private static final String messageBrokerUrl = System.getenv("message_broker_address") != null ? 
			"tcp://" + System.getenv("message_broker_address") : "tcp://localhost:61616";

	private Session session;
	private Connection connection;

	private MessageProducer producer;

	protected ExecutorService threadPool = Executors.newCachedThreadPool();

	public static void startBroker() {
		startBroker(messageBrokerUrl);
	}

	public static void startBroker(String url) {
		try {
			stopBroker();
			broker = new BrokerService();
			broker.setAdvisorySupport(true);
			broker.setPersistent(false);
			broker.setPopulateJMSXUserID(true);
			broker.setUseJmx(false);
			broker.addConnector(url);
			broker.start();
		} catch (Exception e) {
			System.err.println("Error start broker: " + e);
		}
	}

	public static void stopBroker() {
		try {
			if (broker != null)
				broker.stop();
		} catch (Exception e) {
			System.err.println("Error stopping broker: " + e);
		}
	}

	public AMQBasePeer() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(null);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			System.out.println("Opened: " + connection.getClientID());
		} catch (JMSException e) {
			System.err.println("Error creating Session: " + e);
		}
	}

	public void shutdown() {
		threadPool.shutdown();
		try {
			connection.stop();

			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			System.err.println("Error closing Session: " + e);
		}
	}

	public String trimConnectionId(String id) {
		id = id.split(":")[1];
		return id.substring(0, id.length() - 2).toLowerCase();
	}

	public void listenMsg(String queue, Consumer<Message> handler) {
		try {
			final Destination recieverQueue = session.createQueue(queue);
			final MessageConsumer consumer = session.createConsumer(recieverQueue);
			consumer.setMessageListener((Message message) -> {
				threadPool.submit(() -> {
					try {
						handler.accept(message);
					} catch (Exception e) {
						System.err.println("Listen Message Processing Error: " + e);
					}
				});
			});

		} catch (JMSException e) {
			System.err.println("Error setup msg consumer: " + e);
		}
	}

	public void sendMsg(String queue, Message msg) {
		sendMsg(queue, msg, null);
	}

	public void sendMsg(String queue, Message msg, Consumer<Message> handler) {
		try {
			if (handler != null) {
				// TODO: Reuse Temp Dest (CorrelationID)
				Destination tempDest = session.createTemporaryQueue();
				MessageConsumer responseConsumer = session.createConsumer(tempDest);
				responseConsumer.setMessageListener((Message message) -> {
					try {
						handler.accept(message);
					} catch (Exception e) {
						System.err.println("Processing Response Error: " + e);
					}
				});
				msg.setJMSReplyTo(tempDest);
			}
			Destination reciever = session.createQueue(queue);
			sendMsg(reciever, msg);
		} catch (JMSException e) {
			System.err.println("Error sending request: " + e);
		}
	}

	public Message sendMsgBlocking(String queue, Message msg, int timeout) {
		try {
			Destination tempDest = session.createTemporaryQueue();
			MessageConsumer responseConsumer = session.createConsumer(tempDest);

			// System.out.println("Waiting for reply: " + tempDest);

			class ResponseHolder {
				public Message response = null;
			}

			ResponseHolder responseHolder = new ResponseHolder();

			responseConsumer.setMessageListener((Message message) -> {
				synchronized (responseHolder) {
					// System.out.println("Response received, notifyAll");
					responseHolder.response = message;
					responseHolder.notifyAll();
				}
			});

			msg.setJMSReplyTo(tempDest);
			sendMsg(queue, msg);

			long endTime = System.currentTimeMillis() + timeout;
			// System.out.println("Start waiting: " + System.currentTimeMillis()
			// + " : " + endTime);
			synchronized (responseHolder) {
				while (responseHolder.response == null && System.currentTimeMillis() < endTime) {
					long remainingTime = endTime - System.currentTimeMillis();
					responseHolder.wait(remainingTime);
				}
				responseHolder.notifyAll();
			}
			// System.out.println("Response after: " +
			// System.currentTimeMillis() + " : " + responseHolder.response);

			responseConsumer.close();
			return responseHolder.response;
		} catch (InterruptedException e) {
			System.err.println("Interrupted while waiting for blocking message response");
		} catch (JMSException e) {
			System.err.println("Error sending blocking message: " + e);
		}
		return null;
	}

	public void sendMsg(Destination destination, Message msg) {
		try {
			producer.send(destination, msg);
		} catch (JMSException e) {
			System.err.println("Error sending msg: " + e);
		}
	}

	public String readTextMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				return textMessage.getText();
			}
		} catch (JMSException e) {
			System.err.println("Error parsing msg: " + e);
		}
		return null;
	}

	public TextMessage createTextMessage() {
		try {
			return session.createTextMessage();
		} catch (JMSException e) {
			System.err.println("Error creating text msg: " + e);
			return null;
		}
	}

	public Session getSession() {
		return this.session;
	}
	
    public static String TrimConnectionID(String id)
    {
        id = id.split(":")[1];
        return id.substring(0, id.length() - 2).toLowerCase();
    }
    
	public String getConnectionID() {
		try {
			return TrimConnectionID(connection.getClientID());
		} catch (JMSException e) {
			e.printStackTrace();
			return null;
		}
	}
}
