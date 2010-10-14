package org.telluriumsource.framework

import org.telluriumsource.framework.config.CustomConfig
import org.telluriumsource.framework.config.TelluriumConfigurator

import org.telluriumsource.component.connector.SeleniumConnector

import org.telluriumsource.server.EmbeddedSeleniumServer

import org.telluriumsource.crosscut.i18n.IResourceBundle

import org.telluriumsource.util.BaseUtil
import java.lang.reflect.Field

import org.telluriumsource.framework.inject.Injector
import org.telluriumsource.framework.inject.Scope
import org.telluriumsource.ui.builder.UiObjectBuilder
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry
import org.telluriumsource.ui.widget.WidgetConfigurator
import org.telluriumsource.dsl.SeleniumWrapper
import org.telluriumsource.dsl.TelluriumApi
import org.telluriumsource.exception.FrameworkWiringException
import org.telluriumsource.framework.inject.SessionQuery

/**
 * Put all initialization and cleanup jobs for the Tellurium framework here
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jun 2, 2008
 */

@Singleton
public class TelluriumFramework {

  private TelluriumConfigurator telluriumConfigurator;

  private boolean runEmbeddedSeleniumServer = false;

  private EmbeddedSeleniumServer server;

  private boolean isStarted = false;

  private RuntimeEnvironment defaultEnvironment;

  private SessionQuery sQuery = new DefaultSessionQuery();

  public Session createNewSession(String id, RuntimeEnvironment env){
    String name = (id == null ? "" : id);
    name = name + "@" + BaseUtil.toBase62(System.currentTimeMillis());
    Session session = new Session();
    session.sessionId = name;
    session.env = env;
    session.beanFactory = Injector.instance;

    println "Created new session: \n" + session.toString() + "\n";

    return session;
  }
  
  public Session createNewSession(RuntimeEnvironment env){
    return createNewSession(Thread.currentThread().getName(), env)
  }

  public Session createNewSession(String id){
    return createNewSession(id, defaultEnvironment);
  }

  public Session createNewSession(String id, Map<String, Object> map){

    return createNewSession(Thread.currentThread().getName(), createNewEnvironment(map))
  }

  public Session createNewSession(Map<String, Object> map){

    return createNewSession(Thread.currentThread().getName(), createNewEnvironment(map))
  }

  public Session createNewSession(){
    return createNewSession(Thread.currentThread().getName(), defaultEnvironment)
  }

  public Session reuseExistingOrCreateNewSession(){
    Session session = SessionManager.getSession();
    if(session == null){
      session = createNewSession();
    }

    return session;
  }

  private createNewEnvironment(Map<String, Object> map){
    RuntimeEnvironment newEnv = defaultEnvironment.clone();
    if(map != null && (!map.isEmpty())){
      Set<String> keySet = map.keySet();
      for(String key: keySet){
        Field field = RuntimeEnvironment.class.getField(key);
        Object val = map.get(key);
        if(field != null){
          String fieldTypeName = field.getType().getName();
          if("boolean".equals(fieldTypeName)){
            field.setBoolean(newEnv, val);
          }else if("int".equals(fieldTypeName)){
            field.setInt(newEnv, val);
          }else if(String.class.isAssignableFrom(field.getType())){
            field.set(newEnv, val);
          }else{
            throw new RuntimeException("Unsupported type " + fieldTypeName);
          }
        }else{
          newEnv.setCustomEnvironment(key, val);
        }
      }

    }

    return newEnv;
  }

  public synchronized void start() {
    if (!isStarted) {
      IResourceBundle i18nBundle = new org.telluriumsource.crosscut.i18n.ResourceBundle();
      telluriumConfigurator = new TelluriumConfigurator();
      telluriumConfigurator.i18nBundle = i18nBundle;
      
      String fileName = System.properties.getProperty("telluriumConfigFile");
      if (fileName == null)
        fileName = "TelluriumConfig.groovy"
      File file = new File(fileName)
      if (file != null && file.exists()) {
        println i18nBundle.getMessage("TelluriumFramework.ParseFromRootDirectory", fileName)
        telluriumConfigurator.parse(file)
        defaultEnvironment = telluriumConfigurator.createRuntimeEnvironment();
      } else {
        URL url = ClassLoader.getSystemResource(fileName)
        if (url != null) {
          println i18nBundle.getMessage("TelluriumFramework.ParseFromClassPath", fileName)
          telluriumConfigurator.parse(url)
          defaultEnvironment = telluriumConfigurator.createRuntimeEnvironment();
        } else {
          println i18nBundle.getMessage("TelluriumFramework.CannotFindConfigFile", fileName)
          defaultEnvironment = telluriumConfigurator.createDefaultRuntimeEnvironment();
        }
      }


      Session session = reuseExistingOrCreateNewSession();
      SessionManager.setSession(session);
      assembleFramework(session);
     
      this.isStarted = true;
    }
  }

  public synchronized void stop() {
    SessionManager.setSession(null);
    this.isStarted = false;
  }

  public void disableEmbeddedSeleniumServer() {
    this.runEmbeddedSeleniumServer = false
  }

  public void startSeleniumServer() {
    server = new EmbeddedSeleniumServer()

//    telluriumConfigurator.config(server)

    server.runSeleniumServer()
  }

  public void startServer(CustomConfig customConfig) {
    if (customConfig == null) {
      //if no custom configuration, still start using the default one
      startSeleniumServer()
    } else {

      server = new EmbeddedSeleniumServer()

//      telluriumConfigurator.config(server)

      //overwrite the embedded server settings with these provided by custom configuration
      server.setProperty("runSeleniumServerInternally", customConfig.isRunInternally())
      server.setProperty("port", customConfig.getPort())
      server.setProperty("useMultiWindows", customConfig.isUseMultiWindows())
      server.setProperty("profileLocation", customConfig.getProfileLocation())
      IResourceBundle i18nBundle = defaultEnvironment.getResourceBundle()
      println i18nBundle.getMessage("TelluriumFramework.OverwriteSeleniumServerSettings")

      server.runSeleniumServer()
    }
  }

  public void stopServer(){
    if (runEmbeddedSeleniumServer && (server != null)) {
      server.stopSeleniumServer()
    }
  }

  public void connectServer(){
     SeleniumConnector connector = SessionManager.getSession().getByClass(SeleniumConnector.class);
     connector.connectSeleniumServer();
  }

  public void disconnectServer(){
     SeleniumConnector connector = SessionManager.getSession().getByClass(SeleniumConnector.class);
     connector.disconnectSeleniumServer()
  }
  
  public synchronized void assembleFramework(Session session){
    Injector injector = Injector.instance;
    Session original = SessionManager.getSession();
    String sessionId = session.getSessionId();
    injector.setSessionQuery(this.sQuery);
    SessionManager.setSession(session);
    try{
      RuntimeEnvironment env = session.getEnv();
      injector.addLookupForSession(sessionId, env);
      injector.addBean(sessionId, RuntimeEnvironment.class.getCanonicalName(),  RuntimeEnvironment.class, RuntimeEnvironment.class, Scope.Session, true, env);
      IResourceBundle i18nBundle =  new org.telluriumsource.crosscut.i18n.ResourceBundle();
      String[] split = env.getLocale().split("_");
      Locale loc = new Locale(split[0], split[1]);
      i18nBundle.updateDefaultLocale(loc);
      env.setResourceBundle(i18nBundle);
      injector.addBean(sessionId, "i18nBundle",  IResourceBundle.class, org.telluriumsource.crosscut.i18n.ResourceBundle.class, Scope.Session, true, i18nBundle);

      Map<String, UiObjectBuilder> customBuilders = env.getEnvironmentVariable("tellurium.uiobject.builder");

      if(customBuilders != null && (!customBuilders.isEmpty())){
        UiObjectBuilderRegistry uobRegistry = injector.getByClass(sessionId, UiObjectBuilderRegistry.class);
          customBuilders.each {key, value ->
            UiObjectBuilder builder = (UiObjectBuilder) Class.forName(value).newInstance()
            uobRegistry.registerBuilder(key, builder)
        }
      }

      String widgetModules = env.getEnvironmentVariable("tellurium.widget.module.included");

      if(widgetModules != null && (!widgetModules.isEmpty())){
        WidgetConfigurator widgetConfigurator = injector.getByClass(sessionId, WidgetConfigurator.class);
        widgetConfigurator.configWidgetModule(widgetModules);
      }

      injector.getByClass(sessionId, SeleniumConnector.class);

      SeleniumWrapper wrapper = injector.getByName(sessionId, "SeleniumWrapper");
      session.wrapper = wrapper;
      TelluriumApi api = injector.getByName(sessionId, "TelluriumApi");
      session.api = api;
    }catch(Exception e){
      e.printStackTrace();
      throw new FrameworkWiringException("Error Wiring Tellurium Framework", e);
    }finally{
      SessionManager.setSession(original);
    }

  }

}