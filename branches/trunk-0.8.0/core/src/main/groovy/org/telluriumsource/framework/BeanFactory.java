package org.telluriumsource.framework;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 30, 2010
 */
public interface BeanFactory {

    void provide(String name, Class clazz, String scope, boolean isSingleton);

    <T> T getInstance(Class<T> clazz);
}
