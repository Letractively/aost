package org.telluriumsource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Sep 30, 2010
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface Env {
    String key() default "";
    String value() default "";
}
