package org.telluriumsource.framework.inject;

import org.telluriumsource.exception.BeanNotFoundException;
import org.telluriumsource.exception.InstanceCreationException;
import org.telluriumsource.framework.SessionManager;

import java.util.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 4, 2010
 */
public class DefaultBeanFactory implements BeanFactory{

    private Map<String, Bean> map = new HashMap<String, Bean>();

    public void addBean(String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
        Bean bean;
        if (scope == Scope.Global) {
            bean = new GlobalBean();
        } else if (scope == Scope.Session) {
            bean = new SessionBean();
        } else {
            bean = new RequestBean();
        }
        bean.setName(name);
        bean.setClazz(clazz);
        bean.setConcrete(concrete);
        bean.setScope(scope);
        bean.setSingleton(singleton);
        if(instance != null){
            bean.setInstance(SessionManager.getSession(), instance);
        }

        map.put(name, bean);
    }

    public Object getByName(String name) {
        Bean bean = map.get(name);
        if(bean == null){
            throw new BeanNotFoundException("Bean " + name + " is not found");
        }

        return getInstance(bean);
    }

    public <T> T getByClass(Class<T> clazz) {
        String name = clazz.getCanonicalName();
        
        return (T)getByName(name);
    }

    public List<Bean> getAllBeans() {
        List<Bean> list = new ArrayList<Bean>();
        list.addAll(map.values());
        return list;    
    }

    public void destroy() {
        map = null;    
    }

    private synchronized Object getInstance(Bean bean){
        if(bean.isSingleton()){
            Object instance = bean.getInstance(SessionManager.getSession());
            if(instance == null){
                instance = createInstance(bean.getConcrete());
                bean.setInstance(SessionManager.getSession(), instance);
            }

            return instance;
        }else{
            Object instance = createInstance(bean.getConcrete());
            bean.setInstance(SessionManager.getSession(), instance);

            return instance;
        }
    }

    private Object createInstance(Class clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new InstanceCreationException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new InstanceCreationException(e.getMessage());
        }
    }
}
