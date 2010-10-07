package org.telluriumsource.framework.dj;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 6, 2010
 */

public class GlobalBean extends BeanInfo implements Bean {
    private Object instance;

    public GlobalBean() {
    }

    public GlobalBean(String name, Class clazz, Class concrete, boolean singleton, Scope scope, Object instance) {
        super(name, clazz, concrete, singleton, scope);
        this.instance = instance;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
