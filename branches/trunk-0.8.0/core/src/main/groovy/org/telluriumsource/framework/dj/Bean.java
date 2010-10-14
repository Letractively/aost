package org.telluriumsource.framework.inject;

import org.telluriumsource.framework.Session;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 4, 2010
 */
public interface Bean{

    String getName();

    void setName(String name);

    Class getClazz();

    void setClazz(Class clazz);

    Class getConcrete();

    void setConcrete(Class concrete);

    boolean isSingleton();

    void setSingleton(boolean singleton);

    Scope getScope();

    void setScope(Scope scope);

    Object getInstance(String sessionId);

    void setInstance(String sessionId, Object instance);

    String toString();
       
}
