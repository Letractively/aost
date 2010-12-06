package org.telluriumsource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 30, 2010
 * 
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
@GroovyASTTransformationClass("org.telluriumsource.ast.InjectASTTransformation")
public @interface Inject {
    String name() default "";
    boolean lazy() default false;
}