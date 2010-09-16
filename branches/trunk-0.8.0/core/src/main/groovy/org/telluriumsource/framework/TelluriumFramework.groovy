package org.telluriumsource.framework

import org.telluriumsource.ui.builder.UiObjectBuilder
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry

import org.telluriumsource.framework.config.CustomConfig
import org.telluriumsource.framework.config.TelluriumConfigurator

import org.telluriumsource.component.connector.SeleniumConnector

import org.telluriumsource.server.EmbeddedSeleniumServer

import org.telluriumsource.crosscut.i18n.IResourceBundle

import org.telluriumsource.util.Helper
import org.telluriumsource.entity.CachePolicy
import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.entity.EngineState;


import org.telluriumsource.util.BaseUtil
import java.lang.reflect.Field
import org.telluriumsource.dsl.SeleniumWrapper
import org.telluriumsource.dsl.TelluriumApi

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


  public Session createNewSession(String id, RuntimeEnvironment env){
    String name = (id == null ? "" : id);
    name = name + "@" + BaseUtil.toBase62(System.currentTimeMillis());
    Lookup lookup = new DefaultLookup();
    Assembler assembler = new Assembler(lookup, env, telluriumConfigurator);
    assembler.assemble();
    Session session = new Session();
    session.sessionId = name;
    session.env = env;
    session.lookup = lookup;
    session.api = lookup.lookById("api");
    session.wrapper = lookup.lookById("wrapper");
    session.i18nBundle = lookup.lookById("i18nBundle");

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

  private RuntimeEnvironment defaultEnvironment;

  public synchronized void start() {
    if (!isStarted) {
      IResourceBundle i18nBundle = new org.telluriumsource.crosscut.i18n.ResourceBundle();
      telluriumConfigurator = new TelluriumConfigurator();

      String fileName = System.properties.getProperty("telluriumConfigFile");
      if (fileName == null)
        fileName = "TelluriumConfig.groovy"
      File file = new File(fileName)
      if (file != null && file.exists()) {
        println i18nBundle.getMessage("TelluriumFramework.ParseFromRootDirectory", fileName)
        telluriumConfigurator.parse(file)
      } else {
        URL url = ClassLoader.getSystemResource(fileName)
        if (url != null) {
          println i18nBundle.getMessage("TelluriumFramework.ParseFromClassPath", fileName)
          telluriumConfigurator.parse(url)
        } else {
          println i18nBundle.getMessage("TelluriumFramework.CannotFindConfigFile", fileName)
        }
      }

      defaultEnvironment = telluriumConfigurator.createRuntimeEnvironment();

      Session session = reuseExistingOrCreateNewSession();
      SessionManager.setSession(session);
      this.isStarted = true;
    }
  }

  public synchronized void stop(){

  }

  public void disableEmbeddedSeleniumServer() {
    this.runEmbeddedSeleniumServer = false
  }

  public void startSeleniumServer() {
    server = new EmbeddedSeleniumServer()

    telluriumConfigurator.config(server)

    server.runSeleniumServer()

//    connector = new SeleniumConnector()
//    telluriumConfigurator.config(connector)
  }

  public void startServer(CustomConfig customConfig) {
    if (customConfig == null) {
      //if no custom configuration, still start using the default one
      startSeleniumServer()
    } else {

      server = new EmbeddedSeleniumServer()

      telluriumConfigurator.config(server)

      //overwrite the embedded server settings with these provided by custom configuration
      server.setProperty("runSeleniumServerInternally", customConfig.isRunInternally())
      server.setProperty("port", customConfig.getPort())
      server.setProperty("useMultiWindows", customConfig.isUseMultiWindows())
      server.setProperty("profileLocation", customConfig.getProfileLocation())
      IResourceBundle i18nBundle = defaultEnvironment.getResourceBundle()
      println i18nBundle.getMessage("TelluriumFramework.OverwriteSeleniumServerSettings")

      server.runSeleniumServer()

/*      connector = new SeleniumConnector()
      telluriumConfigurator.config(connector)

      //overwrite the selenium connector settings with these provided by custom configuration
      connector.setProperty("browser", customConfig.getBrowser())
      connector.setProperty("port", customConfig.getPort())
      if (customConfig.getServerHost() != null) {
        //only overwrite the server host if it is set
        connector.setProperty("seleniumServerHost", customConfig.getServerHost())
      }
      println i18nBundle.getMessage("TelluriumFramework.OverwriteSeleniumConnectorSettings")
*/

    }
  }

  public void stopServer(){
    if (runEmbeddedSeleniumServer && (server != null)) {
      server.stopSeleniumServer()
    }
  }

  public void connectServer(){
     SeleniumConnector connector = SessionManager.getSession().getLookup().lookById("connector");
     connector.connectSeleniumServer();
  }

  public void disconnectServer(){
     SeleniumConnector connector = SessionManager.getSession().getLookup().lookById("connector");
     connector.disconnectSeleniumServer()
  }

}