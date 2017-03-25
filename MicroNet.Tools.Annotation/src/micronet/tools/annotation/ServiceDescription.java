package micronet.tools.annotation;

import java.util.Set;

import javax.lang.model.element.Element;

import micronet.annotation.MessageService;

class ServiceDescription {
	Element service;
	Set<? extends Element> messageListeners;
	Set<? extends Element> startMethods;
	Set<? extends Element> stopMethods;
	
	public Element getService() {
		return service;
	}
	public void setService(Element service) {
		this.service = service;
	}
	public Set<? extends Element> getMessageListeners() {
		return messageListeners;
	}
	public void setMessageListeners(Set<? extends Element> messageListeners) {
		this.messageListeners = messageListeners;
	}
	public Set<? extends Element> getStartMethods() {
		return startMethods;
	}
	public void setStartMethods(Set<? extends Element> startMethods) {
		this.startMethods = startMethods;
	}
	public Set<? extends Element> getStopMethods() {
		return stopMethods;
	}
	public void setStopMethods(Set<? extends Element> stopMethods) {
		this.stopMethods = stopMethods;
	}
	
	public String getName() {
		if (service == null)
			return null;
		return service.getSimpleName().toString();
	}

	public String getServiceVariable() {
		return "service";
	}
	
	public String getPeerVariable() {
		return "peer";
	}
	public String getURI() {
		if (service == null)
			return null;
		return service.getAnnotation(MessageService.class).uri();
	}
}