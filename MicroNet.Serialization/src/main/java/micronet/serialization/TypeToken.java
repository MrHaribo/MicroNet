package micronet.serialization;

/**
 * TypeToken that can be used to define a generic object class for deserialization.
 * @author Jonas Biedermann
 *
 * @param <T> Generic type to indicate a Class for deserialization.
 */
public abstract class TypeToken<T> extends com.google.gson.reflect.TypeToken<T> {
}
