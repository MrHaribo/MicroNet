package micronet.serialization;

import java.lang.reflect.Type;
import java.util.Map;

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

	public static Map<String, String> deserializeParameters(String data) {
		Type typeOfHashMap = new TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(data, typeOfHashMap);
	}
}
