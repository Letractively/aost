package org.telluriumsource.framework.inject

import org.telluriumsource.annotation.Provider


/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 1, 2010
 * 
 */

@Provider
class Injector implements SessionAwareBeanFactory{

  private Map<String, Lookup> sLookup = new HashMap<String, Lookup>();

  private SessionQuery sQuery;

  public void setSessionQuery(SessionQuery query){
    this.sQuery = query;
  }

  private String getCurrentSessionId(){
    if(this.sQuery != null)
      return this.sQuery.getCurrentSessionId();

    return null;
  }

  public void addLookupForSession(String sessionId, Lookup lookup){
    this.sLookup.put(sessionId, lookup);
  }

  private SessionAwareBeanFactory beanFactory = new DefaultSessionAwareBeanFactory();

  public void addBeanInfo(String name, Class clazz, Class concrete, String scope, boolean singleton){
    
    addBean(name, clazz, concrete, Scope.valueOf(scope), singleton, null);
  }

  public Object getByName(String name) {
    Lookup lookup = this.sLookup.get(this.getCurrentSessionId());
    if(lookup != null && lookup.has(name)){
      return lookup.getByName(name);
    }

    return this.beanFactory.getByName(this.getCurrentSessionId(), name);
  }

  public <T> T getByClass(Class<T> clazz) {
    return this.beanFactory.getByClass(this.getCurrentSessionId(), clazz);
  }

  void addBean(String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(this.currentSessionId, name, clazz, concrete, scope, singleton, instance);
  }

  List<Bean> getAllBeans() {
    return this.beanFactory.getAllBeans();
  }

  void destroy() {
    this.beanFactory.destroy();
  }

  public void addBean(String sessionId, String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(sessionId, name, clazz, concrete, scope, singleton, instance);
  }

  public <T> T getByClass(String sessionId, Class<T> clazz) {
    return this.beanFactory.getByClass(sessionId, clazz)
  }

  public Object getByName(String sessionId, String name) {
    Lookup lookup = this.sLookup.get(sessionId);
    if(lookup != null && lookup.has(name)){
      return lookup.getByName(name);
    }

    return this.beanFactory.getByName(sessionId, name);
  }

  String showAllBeans() {
    final String fieldSeparator = ", ";

    List<Bean> list = this.getAllBeans();
    if (list != null && (!list.isEmpty())) {
      StringBuffer sb = new StringBuffer(64 * list.size());
      sb.append("{");
      sb.append(list.join(fieldSeparator));
      sb.append("}");

      return sb.toString();
    }

    return "No Bean Found!";
  }

}
