package org.telluriumsource.framework

import org.telluriumsource.annotation.Provider

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 1, 2010
 * 
 */

class Cached {
  private Map<String, Class> map = new HashMap<String, Class>()

  public Set<String> getNames(){
    return this.map.keySet();
  }

  public Set<Class> getCachedClasses(){
    return this.map.values();
  }

  public void addCache(String name, Class clazz){
    this.map.put(name, clazz);
  }
}
