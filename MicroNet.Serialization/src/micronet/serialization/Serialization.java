package micronet.serialization;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Serialization {

	private static Gson gson = new Gson();

	public static String serialize(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T deserialize(String data, Class<T> c) {
		return gson.fromJson(data, c);
	}

	public static Map<Integer, String> deserializeParameters(String data) {
		Type typeOfHashMap = new TypeToken<Map<Integer, String>>() {
		}.getType();
		return gson.fromJson(data, typeOfHashMap);
	}
}
