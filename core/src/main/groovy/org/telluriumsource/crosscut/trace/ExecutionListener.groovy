package org.telluriumsource.crosscut.trace

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */
public interface ExecutionListener {

  public void publish(String testName, long start, long duration);

}