package org.telluriumsource.framework.dj;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 7, 2010
 */
public class RequestBean extends BeanInfo implements Bean {

    private List<Object> instances;

    public RequestBean() {
    }

    public RequestBean(String name, Class clazz, Class concrete, boolean singleton, Scope scope) {
        super(name, clazz, concrete, singleton, scope);
        this.instances = new ArrayList<Object>();
    }

    public List<Object> getInstances() {
        return instances;
    }

    public Object getInstance() {
        return null;
    }

    public void setInstance(Object instance) {
        this.instances.add(instance);
    }
}
