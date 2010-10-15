package org.telluriumsource.inject


/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 1, 2010
 * 
 */

abstract class Injector implements SessionAwareBeanFactory{

  private Map<String, Lookup> sLookup = new HashMap<String, Lookup>();

  private SessionAwareBeanFactory beanFactory = new DefaultSessionAwareBeanFactory();

  public abstract String getCurrentSessionId();

  public void addLookupForSession(String sessionId, Lookup lookup){
    this.sLookup.put(sessionId, lookup);
  }

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

  public void addBean(String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(this.getCurrentSessionId(), name, clazz, concrete, scope, singleton, instance);
  }

  public List<Bean> getAllBeans() {
    return this.beanFactory.getAllBeans();
  }

  public void destroy() {
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

  public String showAllBeans() {
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
