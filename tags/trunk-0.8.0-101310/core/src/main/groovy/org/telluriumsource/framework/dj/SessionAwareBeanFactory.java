package org.telluriumsource.framework.dj;

import org.telluriumsource.framework.Session;

import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Oct 8, 2010
 */
public interface SessionAwareBeanFactory {

    void addBean(Session session, String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance);

    Object getByName(Session session, String name);

    <T> T getByClass(Session session, Class<T> clazz);

    List<Bean> getAllBeans();

    void destroy();
}
