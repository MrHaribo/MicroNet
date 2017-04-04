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
    public Class<?> requestDataType() default String.class;
    public Class<?> responseDataType() default String.class;
    public MessageParameter[] requestParameters() default {};
    public MessageParameter[] responseParameters() default {};
}