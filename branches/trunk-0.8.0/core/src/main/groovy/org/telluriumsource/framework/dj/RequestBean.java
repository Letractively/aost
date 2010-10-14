package org.telluriumsource.framework.inject;

import org.telluriumsource.framework.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 7, 2010
 */
public class RequestBean extends BeanInfo implements Bean {

    public static final String INSTANCES = "instances";

    public static final String INSTANCE_SIZE = "instanceSize";

    private List<Object> instances;

    public RequestBean() {
        this.instances = new ArrayList<Object>();
    }

    public RequestBean(String name, Class clazz, Class concrete, boolean singleton, Scope scope) {
        super(name, clazz, concrete, singleton, scope);
        this.instances = new ArrayList<Object>();
    }

    public List<Object> getInstances() {
        return instances;
    }

    public Object getInstance(String sessionId) {
        return null;
    }

    public void setInstance(String sessionId, Object instance) {
        this.instances.add(instance);
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
