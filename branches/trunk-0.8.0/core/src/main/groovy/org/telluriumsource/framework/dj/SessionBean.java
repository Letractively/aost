package org.telluriumsource.framework.dj;

import org.telluriumsource.framework.Session;
import org.telluriumsource.framework.SessionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 6, 2010
 */
public class SessionBean extends BeanInfo implements Bean {

    private Map<String, Object> instances;

    public SessionBean() {
    }

    public SessionBean(String name, Class clazz, Class concrete, boolean singleton, Scope scope) {
        super(name, clazz, concrete, singleton, scope);
        this.instances = new HashMap<String, Object>();
    }

    public Object getInstance() {
        Session session = SessionManager.getSession();
        return instances.get(session.getSessionId());
    }

    public void setInstance(Object instance) {
        Session session = SessionManager.getSession();

        this.instances.put(session.getSessionId(), instance);
    }
}
