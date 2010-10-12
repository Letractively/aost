package org.telluriumsource.framework.dj

import org.telluriumsource.annotation.Provider
import org.telluriumsource.framework.Session
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.dsl.SeleniumWrapper
import org.telluriumsource.dsl.TelluriumApi
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.component.connector.CustomSelenium
import org.telluriumsource.exception.FrameworkWiringException
import org.telluriumsource.component.connector.SeleniumConnector

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 1, 2010
 * 
 */

@Provider
class Injector implements SessionAwareBeanFactory{

  private SessionAwareBeanFactory beanFactory = new DefaultSessionAwareBeanFactory();

  public void addBeanInfo(String name, Class clazz, Class concrete, String scope, boolean singleton){
    
    addBean(name, clazz, concrete, Scope.valueOf(scope), singleton, null);
  }

  public Object getByName(String name) {
    Session session = SessionManager.getSession();
    if(session.getEnv().hasKey(name)){
      return session.getEnv().getEnvironmentVariable(name);
    }

    return this.beanFactory.getByName(session, name);
  }

  public <T> T getByClass(Class<T> clazz) {
    return this.beanFactory.getByClass(SessionManager.getSession(),clazz);
  }

  void addBean(String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(SessionManager.getSession(), name, clazz, concrete, scope, singleton, instance);
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

  public synchronized void assembleFramework(Session session){
    Session original = SessionManager.getSession();
    SessionManager.setSession(session);
    try{
      RuntimeEnvironment env = session.getEnv();
      addBean(session, RuntimeEnvironment.class.getCanonicalName(),  RuntimeEnvironment.class, RuntimeEnvironment.class, Scope.Session, true, env);
      IResourceBundle i18nBundle =  new org.telluriumsource.crosscut.i18n.ResourceBundle();
      String[] split = env.getLocale().split("_");
      Locale loc = new Locale(split[0], split[1]);
      i18nBundle.updateDefaultLocale(loc);

      env.setResourceBundle(i18nBundle);
      addBean(session, "i18nBundle",  IResourceBundle.class, org.telluriumsource.crosscut.i18n.ResourceBundle.class, Scope.Session, true, i18nBundle);
      getByClass(session, SeleniumConnector.class);
      SeleniumWrapper wrapper = getByName(session, "SeleniumWrapper");
      session.wrapper = wrapper;
      TelluriumApi api = getByName(session, "TelluriumApi");
      session.api = api;
    }catch(Exception e){
      e.printStackTrace();
      throw new FrameworkWiringException("Error Wiring Tellurium Framework", e);
    }finally{
      SessionManager.setSession(original);
    }

  }

  public void addBean(Session session, String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(session, name, clazz, concrete, scope, singleton, instance);
  }

  public List<Bean> getAllBeans(Session session) {
    return this.beanFactory.getAllBeans();
  }

  public <T> T getByClass(Session session, Class<T> clazz) {
    return this.beanFactory.getByClass(session, clazz)
  }

  public Object getByName(Session session, String name) {
    if(session.getEnv().hasKey(name)){
      return session.getEnv().getEnvironmentVariable(name);
    }
    
    return this.beanFactory.getByName(session, name);
  }
}
