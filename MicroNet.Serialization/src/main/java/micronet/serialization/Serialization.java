package micronet.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Serialization {

	private static Gson gson = new Gson();
	private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

	public static String serialize(Object obj) {
		return gson.toJson(obj);
	}
	
	public static String serializePretty(Object obj) {
		return gsonPretty.toJson(obj);
	}

	public static <T> T deserialize(String data, Class<T> c) {
		return gson.fromJson(data, c);
	}

	public static <T> T deserialize(String data, TypeToken<T> typeToken) {
		return gson.fromJson(data, typeToken.getType());
	}
}
