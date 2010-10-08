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
class Injector implements BeanFactory{

  private BeanFactory beanFactory = new DefaultBeanFactory();

  public void addBeanInfo(String name, Class clazz, Class concrete, String scope, boolean singleton){
    
    addBean(name, clazz, concrete, Scope.valueOf(scope), singleton, null);
  }

  public Object getByName(String name) {
    return this.beanFactory.getByName(name);
  }

  public <T> T getByClass(Class<T> clazz) {
    return this.beanFactory.getByClass(clazz);
  }

  void addBean(String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(name, clazz, concrete, scope, singleton, instance);
  }

  List<Bean> getAllBeans() {
    return this.beanFactory.getAllBeans();
  }

  String showAllBeans(){
    final int typicalLength = 64;
    final String fieldSeparator = ", ";


    List<Bean> list = this.getAllBeans();
    if(list != null && (!list.isEmpty())){
      StringBuffer sb = new StringBuffer(typicalLength*list.size());
      sb.append("{");
      sb.append(list.join(fieldSeparator));
/*      for(Bean bean: list){
        sb.append(bean.toString()).append(fieldSeparator);
      }
      */
      sb.append("}");

      return sb.toString();
    }

    return "No Bean Found!";
  }

  void destroy() {
    this.beanFactory.destroy();
  }
}
