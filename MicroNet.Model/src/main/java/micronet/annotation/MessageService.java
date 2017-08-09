package micronet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to deine a class as a message service
 * 
 * @author Jonas Biedermann
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MessageService {

	/**
	 * The URI of the message service for example mn://my_service" in the
	 * MicroNet API URI "mn:/my_service/my/path"
	 * 
	 * @return The message listener relative URI
	 */
	public String uri();
	
	public String desc() default "";
}
