package org.telluriumsource.framework.inject;

import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Oct 8, 2010
 */
public interface SessionAwareBeanFactory {

    void addBean(String sessionId, String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance);

    Object getByName(String sessionId, String name);

    <T> T getByClass(String sessionId, Class<T> clazz);

    List<Bean> getAllBeans();

    void destroy();
}
