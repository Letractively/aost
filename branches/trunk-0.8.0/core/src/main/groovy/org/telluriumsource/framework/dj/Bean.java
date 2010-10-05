package org.telluriumsource.framework.dj;

import org.telluriumsource.framework.dj.BeanInfo;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 4, 2010
 */
public class Bean extends BeanInfo {

    private Object instance;

    public Bean() {
    }

    public Bean(String name, Class clazz, boolean singleton, Scope scope, Object instance) {
        super(name, clazz, singleton, scope);
        this.instance = instance;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
       
}
