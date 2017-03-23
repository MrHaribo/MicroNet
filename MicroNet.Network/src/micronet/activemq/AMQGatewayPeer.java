package micronet.activemq;

import java.net.URI;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.RemoveInfo;

import micronet.network.Request;
import micronet.network.Response;

public class AMQGatewayPeer extends AMQPeer {

	public AMQGatewayPeer(Consumer<String> clientDisconnected) {
		super();

		try {
			Destination dest = getSession().createTopic("ActiveMQ.Advisory.Connection");
			// Destination advisoryDestination =
			// AdvisorySupport.getProducerAdvisoryTopic(dest);
			MessageConsumer consumer = getSession().createConsumer(dest);
			consumer.setMessageListener(new MessageListener() {

				@Override
				public void onMessage(Message msg) {

					if (msg instanceof ActiveMQMessage) {
						ActiveMQMessage aMsg = (ActiveMQMessage) msg;

						// if (aMsg.getDataStructure() instanceof
						// ConnectionInfo) {
						// ConnectionInfo info = (ConnectionInfo)
						// aMsg.getDataStructure();
						// System.out.println("Connection Info: " + info);
						// }

						if (aMsg.getDataStructure() instanceof RemoveInfo) {
							RemoveInfo info = (RemoveInfo) aMsg.getDataStructure();
							// System.out.println("Remove Info: " + info);
							ConnectionId connectionId = (ConnectionId) info.getObjectId();
							clientDisconnected.accept(trimConnectionId(connectionId.getValue()));
						}
					}
				}
			});
		} catch (JMSException e) {
			System.err.println("Error processing Advisory Message: " + e);
		}
	}

	public void listen(URI queue, BiConsumer<String, Request> handler) {
		listenMsg(queue.toString(), (Message message) -> {
			try {
				if (message.getJMSReplyTo() != null)
					throw new JMSException("Wrong Type of Message: No Reply Handler");
				Request request = parseRequestMessage(message);
				String id = ((org.apache.activemq.command.Message) message).getProducerId().getConnectionId();
				String clientID = trimConnectionId(id);

				handler.accept(clientID, request);
			} catch (JMSException e) {
				System.err.println("Error processing gateway request: " + e);
			}
		});
	}

	public void listen(URI queue, BiFunction<String, Request, Response> handler) {
		listenMsg(queue.toString(), (Message message) -> {
			threadPool.submit(() -> {
				try {
					Request request = parseRequestMessage(message);
					String id = ((org.apache.activemq.command.Message) message).getProducerId().getConnectionId();
					String clientID = trimConnectionId(id);

					Response response = handler.apply(clientID, request);

					TextMessage responseMsg = createResponseMessage(response);
					sendMsg(message.getJMSReplyTo(), responseMsg);
				} catch (JMSException e) {
					System.err.println("Error processing gateway task request/response: " + e);
				}
			});
		});
	}

}
