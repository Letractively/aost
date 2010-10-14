package org.telluriumsource.framework;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 10, 2010
 */
public interface Observable {

    void addObserver(String id, LifeCycleObserver observer);
    
    void removeObserver(String id);

}
