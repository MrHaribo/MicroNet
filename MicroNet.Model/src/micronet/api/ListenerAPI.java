package micronet.api;

public class ListenerAPI {
    private String listenerUri;
    private String requestDataType;
    private String responseDataType;
    private ParameterAPI[] requestParameters;
    private ParameterAPI[] responseParameters;
    
	public String getListenerUri() {
		return listenerUri;
	}
	public void setListenerUri(String listenerUri) {
		this.listenerUri = listenerUri;
	}
	public String getRequestDataType() {
		return requestDataType;
	}
	public void setRequestDataType(String requestDataType) {
		this.requestDataType = requestDataType;
	}
	public String getResponseDataType() {
		return responseDataType;
	}
	public void setResponseDataType(String responseDataType) {
		this.responseDataType = responseDataType;
	}
	public ParameterAPI[] getRequestParameters() {
		return requestParameters;
	}
	public void setRequestParameters(ParameterAPI[] requestParameters) {
		this.requestParameters = requestParameters;
	}
	public ParameterAPI[] getResponseParameters() {
		return responseParameters;
	}
	public void setResponseParameters(ParameterAPI[] responseParameters) {
		this.responseParameters = responseParameters;
	}
    
    
}
