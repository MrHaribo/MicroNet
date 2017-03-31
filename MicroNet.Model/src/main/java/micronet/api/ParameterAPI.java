package micronet.api;

import micronet.model.ParameterCode;

public class ParameterAPI {
    private ParameterCode type;
    private String valueType;
    
	public ParameterCode getType() {
		return type;
	}
	public void setType(ParameterCode type) {
		this.type = type;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
}
