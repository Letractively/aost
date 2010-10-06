package org.telluriumsource.framework.dj

import org.telluriumsource.annotation.Provider

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 1, 2010
 * 
 */

@Provider
class Injector {
  
  private Map<String, BeanInfo> map = new HashMap<String, BeanInfo>()

  public Set<String> getNames(){
    return this.map.keySet();
  }

  public Set<BeanInfo> getBeanInfos(){
    return this.map.values();
  }

  public Map<String, BeanInfo> getRegistry(){
    return this.map;
  }

  public void addBeanInfo(String name, Class clazz, Class concrete, String scope, boolean singleton){
    BeanInfo info = new BeanInfo();
    info.setName(name);
    info.setClazz(clazz);
    info.setConcrete(concrete);
    info.setScope(Scope.valueOf(scope));
    info.setSingleton(singleton);

    this.map.put(name, info);
  }
}
