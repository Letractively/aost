package org.tellurium.bundle

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 19, 2009
 * 
 */
public interface BundleStrategy {

  public boolean shouldAppend(SelenCmd newcmd);

}