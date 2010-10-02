package org.telluriumsource.framework

import org.telluriumsource.annotation.Provider

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 1, 2010
 * 
 */

@Provider
@Singleton
class Cached {
  private Map<String, Class> map

  public Map<String, Class> getCached(){
    return this.map
  }

  private def Cached() {
     map = new HashMap<String, Class>()
     initiate()
  }
  
  public void initiate(){

  }
}
