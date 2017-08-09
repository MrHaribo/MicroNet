package micronet.activemq;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import micronet.network.NetworkConstants;
import micronet.network.Request;
import micronet.network.Response;

public class AMQClientPeer extends AMQPeer {

	private Map<String, List<Consumer<Request>>> eventHandlers = new HashMap<>();

	public AMQClientPeer() {
		super();
		super.startup(URI.create("mn://" + getConnectionID()));
        
		super.listen("/event", request->{
	        String eventName = request.getParameters().getString(NetworkConstants.EVENT.getCode());
	        //Debug.Log("Event: " + eventName);
	        if (eventName != null)
	        {
	            List<Consumer<Request>> handlers = eventHandlers.get(eventName);
	            if (handlers != null)
	            {
	                for (Consumer<Request> handler : handlers)
	                    handler.accept(request);
	            }
	        }
		});
		
    }

	@Override
	public void listen(String eventName, Consumer<Request> handler)
    {
        if (!eventHandlers.containsKey(eventName))
            eventHandlers.put(eventName, new ArrayList<>());
        eventHandlers.get(eventName).add(handler);
    }
	
	@Override
	public void sendRequest(URI destination, Request request, Consumer<Response> messageHandler) {
		request.getParameters().set(NetworkConstants.USER_REQUEST.getCode(), destination);
		super.sendRequest(URI.create(NetworkConstants.REQUEST_QUEUE), request, messageHandler);
	}

	@Override
	public void sendRequest(URI destination, Request request) {
        request.getParameters().set(NetworkConstants.USER_REQUEST.getCode(), destination);
        super.sendRequest(URI.create(NetworkConstants.COMMAND_QUEUE), request);
    }

}
