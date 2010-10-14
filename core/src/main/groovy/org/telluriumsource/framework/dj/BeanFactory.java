package org.telluriumsource.framework.inject;

import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 4, 2010
 */
public interface BeanFactory {

    void addBean(String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance);

    Object getByName(String name);

    <T> T getByClass(Class<T> clazz);

    List<Bean> getAllBeans();

    void destroy();
}
