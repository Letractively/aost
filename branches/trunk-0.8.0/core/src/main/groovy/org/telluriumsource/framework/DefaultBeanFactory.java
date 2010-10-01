package org.telluriumsource.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 30, 2010
 */
public class DefaultBeanFactory implements BeanFactory{

    private Map<String, Clazz> map = new HashMap<String, Clazz>();

    public synchronized void provide(String name, Class clazz, boolean isSingleton) {
        if(name == null){
            name = clazz.getCanonicalName();
        }
        Clazz bean = new Clazz();
        bean.setName(name);
        bean.setClazz(clazz);
        bean.setSingleton(isSingleton);
        map.put(name, bean);
    }

    public synchronized <T> T getInstance(Class<T> clazz) {
        Clazz bean = map.get(clazz.getCanonicalName());

        return (T)bean.getInstance();
    }
}
