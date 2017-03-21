package micronet.core.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public final class Config {

	private static JsonObject configObject;

	static {
		try {
			File file = new File("/usr/config.json");
			if (!file.exists()) {
				file = new File("../config.json");
			}

			JsonReader jsonReader = new JsonReader(new FileReader(file));
			configObject = new Gson().fromJson(jsonReader, JsonObject.class);
			jsonReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean getBool(String key) {
		return configObject.get(key).getAsBoolean();
	}

	public static String getString(String key) {
		return configObject.get(key).getAsString();
	}

	public static int getInt(String key) {
		return configObject.get(key).getAsInt();
	}
}
