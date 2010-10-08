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
    public static final String INSTANCES = "instances";

    public static final String INSTANCE_SIZE = "instanceSize";

    private Map<String, Object> instances;

    public SessionBean() {
        this.instances = new HashMap<String, Object>();
    }

    public SessionBean(String name, Class clazz, Class concrete, boolean singleton, Scope scope) {
        super(name, clazz, concrete, singleton, scope);
        this.instances = new HashMap<String, Object>();
    }

    public Object getInstance(Session session) {
        return instances.get(session.getSessionId());
    }

    public void setInstance(Session session, Object instance) {
        instances.put(session.getSessionId(), instance);
    }

    public String toString(){
        final int typicalLength = 128;
        final String avpSeparator = ": ";
        final String fieldSeparator = ", ";

        StringBuffer sb = new StringBuffer(typicalLength);
        sb.append("[").append(BEAN_INFO).append(avpSeparator).append(super.toString()).append(fieldSeparator)
                .append(INSTANCE_SIZE).append(avpSeparator).append(instances.size()).append(fieldSeparator)
                .append("]");

        return sb.toString();
    }
}
