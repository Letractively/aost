package org.tellurium.locator
/**
 * Meta command passed to Tellurium Engine
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 20, 2009
 * 
 */

public class MetaCmd {

  public static final String UID = "uid"

  //The UID the locator associated with
  protected String uid

  public static final String CACHEABLE = "cacheable"

  //Whether the locator could be cached or not
  protected boolean cacheable

  public static final String UNIQUE = "unique"

  //Whether the element/elements matching the locator should be unique or multiple
  protected boolean unique 


}