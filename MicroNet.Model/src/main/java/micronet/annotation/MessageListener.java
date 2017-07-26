package micronet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define a Method as a message listener
 * 
 * @author Jonas Biedermann
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface MessageListener {

	/**
	 * The URI of the message listener path relative to the service URI for
	 * example "/my/path" in the MicroNet API URI "mn:/my_service/my/path"
	 * 
	 * @return The message listener relative URI
	 */
	public String uri();
}