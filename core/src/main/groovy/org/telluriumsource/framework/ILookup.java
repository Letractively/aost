package org.telluriumsource.framework;

import java.util.List;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 10, 2010
 */
public interface ILookup {

    void register(String id, Object obj);

    Object lookById(String id);

    List lookByClass(String className);
}
