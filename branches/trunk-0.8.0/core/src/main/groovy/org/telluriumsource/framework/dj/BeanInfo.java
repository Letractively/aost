package org.telluriumsource.framework.dj;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 4, 2010
 */
public class BeanInfo {

    private String name;

    private Class clazz;

    private Class concrete;

    private boolean singleton = true;

    private Scope scope;

    public BeanInfo(){

    }

    public BeanInfo(String name, Class clazz, Class concrete, boolean singleton, Scope scope) {
        this.name = name;
        this.clazz = clazz;
        this.concrete = concrete;
        this.singleton = singleton;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Class getConcrete() {
        return concrete;
    }

    public void setConcrete(Class concrete) {
        this.concrete = concrete;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

}
