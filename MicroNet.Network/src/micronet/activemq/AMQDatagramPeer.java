package micronet.activemq;

import java.net.URI;
import java.util.function.Consumer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AMQDatagramPeer implements ExceptionListener {

	private static final String messageBrokerUrl = "udp://localhost:8123";

	protected Session session;
	private Connection connection;

	private MessageProducer producer;

	public AMQDatagramPeer() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			connection.setExceptionListener(this);
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(null);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			System.out.println("Opened UDP: " + connection.getClientID());
		} catch (JMSException e) {
			System.err.println("Error creating Session: " + e);
		}

	}

	void processMsg(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println("Received: " + text);
			} else {
				System.out.println("Received: " + message);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			producer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			System.err.println("Error closing Session: " + e);
		}
	}

	public void send(URI address, String msg) {
		try {
			Destination destination = session.createQueue(address.toString());
			TextMessage message = session.createTextMessage(msg);
			producer.send(destination, message);
		} catch (JMSException e) {
			System.err.println("Sending error: " + e);
		}
	}

	public void listen(URI address, Consumer<Message> handler) {
		try {
			Destination destination = session.createQueue(address.toString());
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					handler.accept(message);
				}
			});
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}

	public synchronized void onException(JMSException ex) {
		System.out.println("JMS Exception occured: " + ex);
	}

}
