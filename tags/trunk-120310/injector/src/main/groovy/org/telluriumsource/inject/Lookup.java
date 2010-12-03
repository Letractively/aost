package org.telluriumsource.inject;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 8, 2010
 */
public interface Lookup {

    boolean has(String name);
    
    Object getByName(String name);

    <T> T getByClass(Class<T> clazz);
}
