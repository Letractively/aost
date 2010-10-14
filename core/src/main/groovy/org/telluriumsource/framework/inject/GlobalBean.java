package org.telluriumsource.framework.inject;

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

    public Object getInstance(String sessionId) {
        return instance;
    }

    public void setInstance(String sessionId, Object instance) {
        this.instance = instance;
    }

    public String toString(){
        final int typicalLength = 128;
        final String avpSeparator = ": ";
        final String fieldSeparator = ", ";

        StringBuffer sb = new StringBuffer(typicalLength);
        sb.append("[").append(BEAN_INFO).append(avpSeparator).append(super.toString()).append(fieldSeparator)
                .append(INSTANCE).append(avpSeparator).append(instance).append(fieldSeparator)
                .append("]");

        return sb.toString();      
    }
}
