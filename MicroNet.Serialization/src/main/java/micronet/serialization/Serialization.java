package micronet.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * A wrapper class for the Google Gson serialization library. It allows
 * universal access so serialization features for Java classes.
 * 
 * @author Jonas Biedermann
 * 
 */
public class Serialization {

	private static Gson gson = new Gson();
	private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * Serializes any Java object to Json
	 * 
	 * @param obj
	 *            The Object to serialize
	 * @return The object serialized as a String
	 */
	public static String serialize(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * Serializes any Java object to Pretty Json
	 * 
	 * @param obj
	 *            The Object to serialize
	 * @return The object serialized as a human readable String
	 */
	public static String serializePretty(Object obj) {
		return gsonPretty.toJson(obj);
	}

	/**
	 * Deserializes a Java Object from a Json String according to a Java Class.
	 * 
	 * @param data
	 *            A String containing the object's data in Json format
	 * @param c
	 *            The Type of the object's Class
	 * @param <T>
	 *            Generic type to indicate the Class used for deserialization.
	 * @return Instance of the deserialized Object
	 * 
	 */
	public static <T> T deserialize(String data, Class<T> c) {
		return gson.fromJson(data, c);
	}

	/**
	 * Deserializes a Java Object from a Json String using a TypeToken<T> of a
	 * generic a Java Class.
	 * 
	 * @param data
	 *            A String containing the object's data in Json format
	 * @param c
	 *            The TypeToken of the generic object's Class
	 * @param <T>
	 *            Generic type to indicate the Class used for deserialization.
	 * @return Instance of the deserialized Object
	 * 
	 */
	public static <T> T deserialize(String data, TypeToken<T> typeToken) {
		return gson.fromJson(data, typeToken.getType());
	}
}
