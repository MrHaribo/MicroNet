package micronet.annotation;

import java.lang.Class;
import java.lang.String;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MessageParameter {
  String code();

  Class<?> type();

  String desc() default "";
}
