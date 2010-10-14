package org.telluriumsource.framework.dj;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 8, 2010
 */
public interface Lookup {
    
    Object getByName(String name);

    <T> T getByClass(Class<T> clazz);
}
