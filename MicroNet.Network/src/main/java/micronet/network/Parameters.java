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

	private Map<Integer, String> parameters = new HashMap<>();

	/**
	 * Reads a String parameter
	 * @param code ParameterCode of the parameter
	 * @return String representation of the parameter
	 */
	public String getString(int code) {
		return parameters.get(code);
	}
	
	/**
	 * Reads a Integer parameter
	 * @param code ParameterCode of the parameter
	 * @return Integer representation of the parameter
	 */
	public int getInt(int code) {
		return Integer.parseInt(parameters.get(code));
	}
	/**
	 * Reads a Float parameter
	 * @param code ParameterCode of the parameter
	 * @return Float representation of the parameter
	 */
	public float getFloat(int code) {
		return Float.parseFloat(parameters.get(code));
	}
	/**
	 * Reads a Long parameter
	 * @param code ParameterCode of the parameter
	 * @return Long representation of the parameter
	 */
	public long getLong(int code) {
		return Long.parseLong(parameters.get(code));
	}
	/**
	 * Reads a Short parameter
	 * @param code ParameterCode of the parameter
	 * @return Short representation of the parameter
	 */
	public short getShort(int code) {
		return Short.parseShort(parameters.get(code));
	}
	/**
	 * Reads a Double parameter
	 * @param code ParameterCode of the parameter
	 * @return Double representation of the parameter
	 */
	public double getDouble(int code) {
		return Double.parseDouble(parameters.get(code));
	}
	/**
	 * Reads a Byte parameter
	 * @param code ParameterCode of the parameter
	 * @return Byte representation of the parameter
	 */
	public byte getByte(int code) {
		return Byte.parseByte(parameters.get(code));
	}
	/**
	 * Reads a Boolean parameter
	 * @param code ParameterCode of the parameter
	 * @return Boolean representation of the parameter
	 */
	public boolean getBool(int code) {
		return Boolean.parseBoolean(parameters.get(code));
	}
	/**
	 * Reads a Vector2 parameter
	 * @param code ParameterCode of the parameter
	 * @return Vector2 representation of the parameter
	 */
	public Vector2 getVector2(int code) {
		return Vector2.parseVector2(parameters.get(code));
	}
	/**
	 * Reads a Vector3 parameter
	 * @param code ParameterCode of the parameter
	 * @return Vector3 representation of the parameter
	 */
	public Vector3 getVector3(int code) {
		return Vector3.parseVector3(parameters.get(code));
	}
	/**
	 * Puts any parameter into the parameter list
	 * @param code ParameterCode of the Parameter defined in the Shared Model
	 * @param parameter value in string representation
	 */
	public void set(int code, Object parameter) {
		parameters.put(code, parameter.toString());
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
		TypeToken<Map<Integer, String>> token = new TypeToken<Map<Integer, String>>() {
		};
		parameters = Serialization.deserialize(data, token);
	}
	/**
	 * States if a parameter is present in the parameter map
	 * @param code ParameterCode of the Parameter defined in the Shared Model
	 * @return true if the parameter is present and false if the parameter is not present
	 */
	public boolean containsParameter(int code) {
		return parameters.containsKey(code);
	}
}
