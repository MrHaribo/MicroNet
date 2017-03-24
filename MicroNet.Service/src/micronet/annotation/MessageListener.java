package micronet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import micronet.annotation.MessageParameter;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface MessageListener {
    public String uri();
    public MessageParameter[] parameters() default {};
}