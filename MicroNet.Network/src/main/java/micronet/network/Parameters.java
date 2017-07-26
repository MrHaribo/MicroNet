package micronet.network;

import java.util.HashMap;
import java.util.Map;

import micronet.serialization.Serialization;
import micronet.serialization.TypeToken;
import micronet.type.Vector2;
import micronet.type.Vector3;

/**
 * Message Parameter class which provides a convenient way to add Parameters to
 * MicroNet Requests and Responses
 * 
 * @author Jonas Biedermann
 *
 */
public class Parameters {

	private Map<String, String> parameters = new HashMap<>();

	/**
	 * Reads a String parameter
	 * @param code ParameterCode of the parameter
	 * @return String representation of the parameter
	 */
	public String getString(Object code) {
		return parameters.get(code.toString());
	}
	
	/**
	 * Reads a Integer parameter
	 * @param code ParameterCode of the parameter
	 * @return Integer representation of the parameter
	 */
	public int getInt(Object code) {
		return Integer.parseInt(parameters.get(code.toString()));
	}
	/**
	 * Reads a Float parameter
	 * @param code ParameterCode of the parameter
	 * @return Float representation of the parameter
	 */
	public float getFloat(Object code) {
		return Float.parseFloat(parameters.get(code.toString()));
	}
	/**
	 * Reads a Long parameter
	 * @param code ParameterCode of the parameter
	 * @return Long representation of the parameter
	 */
	public long getLong(Object code) {
		return Long.parseLong(parameters.get(code.toString()));
	}
	/**
	 * Reads a Short parameter
	 * @param code ParameterCode of the parameter
	 * @return Short representation of the parameter
	 */
	public short getShort(Object code) {
		return Short.parseShort(parameters.get(code.toString()));
	}
	/**
	 * Reads a Double parameter
	 * @param code ParameterCode of the parameter
	 * @return Double representation of the parameter
	 */
	public double getDouble(Object code) {
		return Double.parseDouble(parameters.get(code.toString()));
	}
	/**
	 * Reads a Byte parameter
	 * @param code ParameterCode of the parameter
	 * @return Byte representation of the parameter
	 */
	public byte getByte(Object code) {
		return Byte.parseByte(parameters.get(code.toString()));
	}
	/**
	 * Reads a Boolean parameter
	 * @param code ParameterCode of the parameter
	 * @return Boolean representation of the parameter
	 */
	public boolean getBool(Object code) {
		return Boolean.parseBoolean(parameters.get(code.toString()));
	}
	/**
	 * Reads a Vector2 parameter
	 * @param code ParameterCode of the parameter
	 * @return Vector2 representation of the parameter
	 */
	public Vector2 getVector2(Object code) {
		return Vector2.parseVector2(parameters.get(code.toString()));
	}
	/**
	 * Reads a Vector3 parameter
	 * @param code ParameterCode of the parameter
	 * @return Vector3 representation of the parameter
	 */
	public Vector3 getVector3(Object code) {
		return Vector3.parseVector3(parameters.get(code.toString()));
	}
	/**
	 * Puts any parameter into the parameter list
	 * @param code ParameterCode of the Parameter defined in the Shared Model
	 * @param parameter value in string representation
	 */
	public void set(Object code, Object parameter) {
		parameters.put(code.toString(), parameter.toString());
	}
	/**
	 * Helper function to serialize the whole parameter map
	 * @return The whole parameter map in String representation
	 */
	public String serialize() {
		return Serialization.serialize(parameters);
	}
	/**
	 * Helper function to deserialize the whole parameter map
	 * @param data String representation of the shole parameter map
	 */
	public void deserialize(String data) {
		TypeToken<Map<String, String>> token = new TypeToken<Map<String, String>>() {
		};
		parameters = Serialization.deserialize(data, token);
	}
	/**
	 * States if a parameter is present in the parameter map
	 * @param code ParameterCode of the Parameter defined in the Shared Model
	 * @return true if the parameter is present and false if the parameter is not present
	 */
	public boolean containsParameter(Object code) {
		return parameters.containsKey(code.toString());
	}
}
