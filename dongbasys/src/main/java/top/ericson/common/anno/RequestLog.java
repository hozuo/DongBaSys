package top.ericson.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Ericson
 * @class RequestLog
 * @date 2020/02/08 23:43
 * @description 
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface RequestLog {
    String value() default "operation";
}
