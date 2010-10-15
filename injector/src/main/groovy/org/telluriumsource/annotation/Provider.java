package org.telluriumsource.annotation;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 30, 2010
 * 
 */

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
@GroovyASTTransformationClass("org.telluriumsource.ast.ProviderASTTransformation")
public @interface Provider {
    String name() default "";
    Class type();
    String scope() default "Session";
    boolean singleton() default true;
}