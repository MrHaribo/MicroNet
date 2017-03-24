package micronet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import micronet.network.ParameterCode;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface MessageParameter {
    public ParameterCode type();
    public Class<?> valueType();
}


