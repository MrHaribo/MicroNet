package micronet.network;

import java.util.HashMap;
import java.util.Map;

import micronet.serialization.Serialization;
import micronet.type.Vector2;
import micronet.type.Vector3;

public class Parameters {

	private Map<String, String> parameters = new HashMap<>();

	public String getString(Object code) {
		return parameters.get(code.toString());
	}

	public int getInt(Object code) {
		return Integer.parseInt(parameters.get(code.toString()));
	}

	public float getFloat(Object code) {
		return Float.parseFloat(parameters.get(code.toString()));
	}

	public long getLong(Object code) {
		return Long.parseLong(parameters.get(code.toString()));
	}

	public boolean getBool(Object code) {
		return Boolean.parseBoolean(parameters.get(code.toString()));
	}

	public Vector2 getVector2(Object code) {
		return Vector2.parseVector2(parameters.get(code.toString()));
	}

	public Vector3 getVector3(Object code) {
		return Vector3.parseVector3(parameters.get(code.toString()));
	}

	public void set(Object code, Object parameter) {
		parameters.put(code.toString(), parameter.toString());
	}

	public String serialize() {
		return Serialization.serialize(parameters);
	}

	public void deserialize(String data) {
		parameters = Serialization.deserializeParameters(data);
	}

	public boolean containsParameter(Object code) {
		return parameters.containsKey(code.toString());
	}
}
