package org.telluriumsource.framework.dj;

import org.telluriumsource.exception.BeanNotFoundException;
import org.telluriumsource.exception.InstanceCreationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 4, 2010
 */
public class DefaultBeanFactory implements BeanFactory{

    private Map<String, Bean> map = new HashMap<String, Bean>();

    public DefaultBeanFactory() {
    }

    public DefaultBeanFactory(Map<String, BeanInfo> beanInfos) {
        if(beanInfos != null && (!beanInfos.isEmpty())){
            Set<String> keys = beanInfos.keySet();
            for(String key: keys){
                BeanInfo info = beanInfos.get(key);
                Bean bean;
                if(info.getScope() == Scope.Global){
                    bean = new GlobalBean();
                }else if(info.getScope() == Scope.Session){
                    bean = new SessionBean();
                }else {
                    bean = new RequestBean();
                }

                bean.setName(info.getName());
                bean.setClazz(info.getClazz());
                bean.setConcrete(info.getConcrete());
                bean.setScope(info.getScope());
                bean.setSingleton(info.isSingleton());
                map.put(key, bean);
            }
        }
    }

    public void initialize(Map<String, BeanInfo> beanInfos){
        if(beanInfos != null && (!beanInfos.isEmpty())){
            Set<String> keys = beanInfos.keySet();
            for(String key: keys){
                BeanInfo info = beanInfos.get(key);

                Bean bean;
                if(info.getScope() == Scope.Global){
                    bean = new GlobalBean();
                }else if(info.getScope() == Scope.Session){
                    bean = new SessionBean();
                }else {
                    bean = new RequestBean();
                }
                bean.setName(info.getName());
                bean.setClazz(info.getClazz());
                bean.setConcrete(info.getConcrete());
                bean.setScope(info.getScope());
                bean.setSingleton(info.isSingleton());
                map.put(key, bean);
            }
        }        
    }

    public void addBean(String name, BeanInfo info) {
        Bean bean;
        if (info.getScope() == Scope.Global) {
            bean = new GlobalBean();
        } else if (info.getScope() == Scope.Session) {
            bean = new SessionBean();
        } else {
            bean = new RequestBean();
        }
        bean.setName(info.getName());
        bean.setClazz(info.getClazz());
        bean.setConcrete(info.getConcrete());
        bean.setScope(info.getScope());
        bean.setSingleton(info.isSingleton());
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

    private synchronized Object getInstance(Bean bean){
        if(bean.isSingleton()){
            Object instance = bean.getInstance();
            if(instance == null){
                instance = createInstance(bean.getConcrete());
                bean.setInstance(instance);
            }

            return instance;
        }else{
            Object instance = createInstance(bean.getConcrete());
            bean.setInstance(instance);

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
