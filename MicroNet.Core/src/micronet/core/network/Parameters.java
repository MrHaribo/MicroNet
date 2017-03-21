package micronet.core.network;

import java.util.HashMap;
import java.util.Map;

import micronet.core.serialization.Serialization;
import micronet.core.type.Vector2;
import micronet.core.type.Vector3;

public class Parameters {

	private Map<Integer, String> parameters = new HashMap<>();

	public String getString(ParameterCode code) {
		return parameters.get(code.getValue());
	}

	public int getInt(ParameterCode code) {
		return Integer.parseInt(parameters.get(code.getValue()));
	}

	public float getFloat(ParameterCode code) {
		return Float.parseFloat(parameters.get(code.getValue()));
	}

	public long getLong(ParameterCode code) {
		return Long.parseLong(parameters.get(code.getValue()));
	}

	public boolean getBool(ParameterCode code) {
		return Boolean.parseBoolean(parameters.get(code.getValue()));
	}

	public Vector2 getVector2(ParameterCode code) {
		return Vector2.parseVector2(parameters.get(code.getValue()));
	}

	public Vector3 getVector3(ParameterCode code) {
		return Vector3.parseVector3(parameters.get(code.getValue()));
	}

	public void set(ParameterCode code, Object parameter) {
		parameters.put(code.getValue(), parameter.toString());
	}

	public String serialize() {
		return Serialization.serialize(parameters);
	}

	public void deserialize(String data) {
		parameters = Serialization.deserializeParameters(data);
	}

	public boolean containsParameter(ParameterCode code) {
		return parameters.containsKey(code.getValue());
	}
}
