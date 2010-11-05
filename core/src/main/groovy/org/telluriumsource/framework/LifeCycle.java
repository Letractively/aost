package org.telluriumsource.framework;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 10, 2010
 */
public interface LifeCycle {

    void preStart();

    void start();

    void postStart();

    void preStop();

    void stop();
    
    void postStop();
}
