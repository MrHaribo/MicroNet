package micronet.api;

public class ServiceAPI {
	String serviceName;
	String serviceUri;
	ListenerAPI[] listeners;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceUri() {
		return serviceUri;
	}

	public void setServiceUri(String serviceUri) {
		this.serviceUri = serviceUri;
	}

	public ListenerAPI[] getListeners() {
		return listeners;
	}

	public void setListeners(ListenerAPI[] listeners) {
		this.listeners = listeners;
	}
}
