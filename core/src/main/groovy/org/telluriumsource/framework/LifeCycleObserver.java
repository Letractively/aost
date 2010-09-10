package org.telluriumsource.framework;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 10, 2010
 */
public interface LifeCycleObserver {

    void onPreStart();

    void onPostStart();

    void onPreStop();

    void onPostStop();
}
