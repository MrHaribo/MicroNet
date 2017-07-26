package micronet.activemq;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import micronet.network.IPeer;
import micronet.network.NetworkConstants;
import micronet.network.Request;
import micronet.network.Response;
import micronet.network.StatusCode;

public class AMQPeer extends AMQBasePeer implements IPeer {

	private Map<String, Consumer<Request>> requestHandlers = new HashMap<>();
	private Map<String, Function<Request, Response>> requestResponseHandlers = new HashMap<>();

	private AMQAdvisory advisory;

	@Override
	public void startup(URI host) {
		listenMsg("mn://" + host.getHost(), (Message message) -> {
			try {
				String path = message.getStringProperty(NetworkConstants.PATH_PROPERTY);

				Function<Request, Response> handler = requestResponseHandlers.get(path);
				if (handler != null) {
					Request request = parseRequestMessage(message);
					Response response = handler.apply(request);

					TextMessage responseMsg = createResponseMessage(response);
					sendMsg(message.getJMSReplyTo(), responseMsg);
					return;
				}

				Consumer<Request> consumer = requestHandlers.get(path);
				if (consumer != null) {
					Request request = parseRequestMessage(message);
					consumer.accept(request);
					return;
				}

				System.err.println("Message handler not found: " + host.toString() + path);
			} catch (JMSException e) {
				System.err.println("JMS Error processing request: " + e);
			} catch (Exception e) {
				System.err.println("Error processing request: mn://" + host.getHost());
				e.printStackTrace();
			}
		});
	}

	@Override
	public void shutdown() {
		if (advisory != null)
			advisory.shutdown();
		super.shutdown();
	}

	@Override
	public void listen(String path, Consumer<Request> handler) {
		requestHandlers.put(path, handler);
	}

	@Override
	public void listen(String path, Function<Request, Response> handler) {
		requestResponseHandlers.put(path, handler);
	}

	@Override
	public void sendRequest(URI destination, Request request) {
		try {
			TextMessage txtMessage = createRequestMessage(request);
			txtMessage.setStringProperty(NetworkConstants.PATH_PROPERTY, destination.getPath());
			sendMsg("mn://" + destination.getHost(), txtMessage);
		} catch (JMSException e) {
			System.err.println("Error sending msg: " + e);
		}
	}

	@Override
	public void sendRequest(URI destination, Request request, Consumer<Response> handler) {
		try {
			TextMessage txtMessage = createRequestMessage(request);
			txtMessage.setStringProperty(NetworkConstants.PATH_PROPERTY, destination.getPath());
			sendMsg("mn://" + destination.getHost(), txtMessage, (Message msg) -> {
				try {
					Response response = parseResponseMessage(msg);
					handler.accept(response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (JMSException e) {
			System.err.println("Error sending request with response: " + e);
		} catch (Exception e) {
			System.err.println("Error processing response: " + destination);
			e.printStackTrace();
		}
	}

	@Override
	public Response sendRequestBlocking(URI destination, Request request) {
		return sendRequestBlocking(destination, request, NetworkConstants.SYNC_REQUEST_TIMEOUT);
	}

	@Override
	public Response sendRequestBlocking(URI destination, Request request, int timeout) {
		try {
			TextMessage txtMessage = createRequestMessage(request);
			txtMessage.setStringProperty(NetworkConstants.PATH_PROPERTY, destination.getPath());

			Message msg = sendMsgBlocking("mn://" + destination.getHost(), txtMessage, timeout);
			if (msg == null)
				return new Response(StatusCode.REQUEST_TIMEOUT);
			return parseResponseMessage(msg);
		} catch (JMSException e) {
			System.err.println("Error sending blocking request: " + e);
			return null;
		}
	}

	protected Request parseRequestMessage(Message message) throws JMSException {
		Request request = new Request(readTextMessage(message));
		request.getParameters().deserialize(message.getStringProperty(NetworkConstants.PARAMETER_PROPERTY));
		return request;
	}

	protected Response parseResponseMessage(Message message) throws JMSException {
		StatusCode status = StatusCode.fromInt(message.getIntProperty(NetworkConstants.STATUS_CODE_PROPERTY));
		Response response = new Response(status, readTextMessage(message));
		response.getParameters().deserialize(message.getStringProperty(NetworkConstants.PARAMETER_PROPERTY));
		return response;
	}

	protected TextMessage createRequestMessage(Request request) throws JMSException {
		TextMessage txtMessage = createTextMessage();
		txtMessage.setText(request.getData());
		txtMessage.setStringProperty(NetworkConstants.PARAMETER_PROPERTY, request.getParameters().serialize());
		return txtMessage;
	}

	protected TextMessage createResponseMessage(Response response) throws JMSException {
		TextMessage txtMessage = createTextMessage();
		txtMessage.setText(response.getData());
		txtMessage.setIntProperty(NetworkConstants.STATUS_CODE_PROPERTY, response.getStatus().getValue());
		txtMessage.setStringProperty(NetworkConstants.PARAMETER_PROPERTY, response.getParameters().serialize());
		return txtMessage;
	}

	public AMQAdvisory getAdvisory() {
		if (advisory == null)
			advisory = new AMQAdvisory(getSession(), threadPool);
		return advisory;
	}
}