package org.telluriumsource.framework;

import org.telluriumsource.annotation.Provider;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 2, 2010
 */
public class AnnotationProcessor {

    public Clazz processProvider(Object obj){

        Provider provider = (Provider)obj.getClass().getAnnotation(Provider.class);
        if(provider == null)
            return null;
        
        Clazz bean = new Clazz();
        bean.setName(provider.name());
        bean.setClazz(provider.type());
        bean.setSingleton(provider.singleton());
        bean.setScope(provider.scope());

        return bean;
    }
}
