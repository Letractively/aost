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

  private boolean runEmbeddedSeleniumServer = true;

  private EmbeddedSeleniumServer server;

  private SessionFactory factory = new SessionFactory();

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

//  private SeleniumConnector connector;

//  private SeleniumClient client;

  private RuntimeEnvironment defaultEnvironment;

//  private GlobalDslContext global;

  public void load() {

//    defaultEnvironment = Environment.instance;

//    By default ExpandoMetaClass doesn't do inheritance. To enable this you must call ExpandoMetaClass.enableGlobally()
//    before your app starts such as in the main method or servlet bootstrap
//        ExpandoMetaClass.enableGlobally()

/*
    def registry = GroovySystem.metaClassRegistry

    registry.setMetaClass(UiObjectBuilderRegistry, new UiObjectBuilderRegistryMetaClass())

    registry.setMetaClass(SeleniumClient, new SeleniumClientMetaClass())

    registry.setMetaClass(Dispatcher, new DispatcherMetaClass())

    registry.setMetaClass(Extension, new ExtensionMetaClass())

    registry.setMetaClass(Accessor, new AccessorMetaClass())

    registry.setMetaClass(EventHandler, new EventHandlerMetaClass())

    registry.setMetaClass(LocatorProcessor, new LocatorProcessorMetaClass())

    registry.setMetaClass(SeleniumConnector, new SeleniumConnectorMetaClass())
    registry.setMetaClass(TelluriumConfigurator, new TelluriumConfiguratorMetaClass())
*/


//    IResourceBundle i18nBundle = session.i18nBundle;
    IResourceBundle i18nBundle =  new org.telluriumsource.crosscut.i18n.ResourceBundle();
    telluriumConfigurator = new TelluriumConfigurator();

//    String fileName = "TelluriumConfig.groovy"
    //Honor the JSON String configuration over the file
/*
    String jsonConf = defaultEnvironment.configString;
    if (jsonConf != null && jsonConf.trim().length() > 0) {
      println i18nBundle.getMessage("TelluriumFramework.ParseFromJSONString", jsonConf)
      telluriumConfigurator.parseJSON(jsonConf)
    } else {

      String fileName = System.properties.getProperty("telluriumConfigFile");
      if(fileName == null)
        fileName = defaultEnvironment.configFileName;

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
    }
*/
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


/*    //configure custom UI ojects
    telluriumConfigurator.config(new UiObjectBuilderRegistry())

    //configure widgets
    telluriumConfigurator.config(new WidgetConfigurator())

    //configure Event Handler
    telluriumConfigurator.config(new EventHandler())

    //configure Data Accessor
    telluriumConfigurator.config(new Accessor())

    //configure Dispatcher
    telluriumConfigurator.config(new Dispatcher())

    //configure runtime environment
    telluriumConfigurator.config(defaultEnvironment)


    //global methods
    this.global = new GlobalDslContext();
*/
    defaultEnvironment = telluriumConfigurator.createRuntimeEnvironment();
    
    Session session = reuseExistingOrCreateNewSession();
    SessionManager.setSession(session);   
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

  public SeleniumConnector getCurrentConnector(){
     return SessionManager.getSession().getLookup().lookById("connector");
  }

/*
  public void stop() {
    if (connector != null) {
      connector.disconnectSeleniumServer()
    }

    if (runEmbeddedSeleniumServer && (server != null)) {
      server.stopServer()
    }
  }
*/
  //register ui object builder
  //users can overload the builders or add new builders for new ui objects
  //by call this method
  public void registerBuilder(String uiObjectName, UiObjectBuilder builder) {
    UiObjectBuilderRegistry registry = new UiObjectBuilderRegistry()
    registry.registerBuilder(uiObjectName, builder)
  }

  public SeleniumConnector getConnector() {
//    return this.connector;
     return SessionManager.getSession().getLookup().lookById("connector");
  }

  public void useMacroCmd(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setUseBundle(isUse);
  }

  public setMaxMacroCmd(int max) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setMaxMacroCmd(max);
  }

  public int getMaxMacroCmd() {
    RuntimeEnvironment env = SessionManager.getSession().env;
    return env.getMaxMacroCmd();
  }

  public void helpTest(){
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.helpTest();
  }

  public void noTest(){
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.helpTest();
  }
  
  /*public void enableLogging(LogLevels loggingLevel) {
    defaultEnvironment.enableLogging(loggingLevel);
    this.global.enableLogging(loggingLevel);
  }
  */

  public void useTrace(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setUseTrace(isUse);
  }

  public void generateBugReport(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setUseBugReport(isUse);
  }

  public void showTrace() {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.showTrace();
  }

  public void setEnvironment(String name, Object value) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setCustomEnvironment(name, value);
  }

  public Object getEnvironment(String name) {
    RuntimeEnvironment env = SessionManager.getSession().env;

    return env.getCustomEnvironment(name);
  }

  public void useClosestMatch(boolean isUse){
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    if (isUse) {
      wrapper.enableClosestMatch();
    } else {
      wrapper.disableClosestMatch();
    }      
  }

  public void useCssSelector(boolean isUse) {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    if (isUse) {
      wrapper.enableCssSelector();
    } else {
      wrapper.disableCssSelector();
    }
  }

  public void cleanCache() {
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");
    api.cleanCache();
  }

  public void setCacheMaxSize(int size) {
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");
    api.setCacheMaxSize(size);
  }

  public int getCacheSize() {
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");
    return api.getCacheSize();
  }

  public int getCacheMaxSize() {
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");
    return api.getCacheMaxSize();
  }

  public String getCacheUsage() {
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");
    return api.getCacheUsage();
  }

  public void useCachePolicy(CachePolicy policy) {
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");

    if (policy != null) {
      switch (policy) {
        case CachePolicy.DISCARD_NEW:
          api.useDiscardNewCachePolicy();
          break;
        case CachePolicy.DISCARD_OLD:
          api.useDiscardOldCachePolicy();
          break;
        case CachePolicy.DISCARD_LEAST_USED:
          api.useDiscardLeastUsedCachePolicy();
          break;
        case CachePolicy.DISCARD_INVALID:
          api.useDiscardInvalidCachePolicy();
          break;
      }
    }
  }

  public String getCurrentCachePolicy() {
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");

    return api.getCurrentCachePolicy();
  }

  public void useDefaultXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.useDefaultXPathLibrary();
  }

  public void useJavascriptXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.useJavascriptXPathLibrary();
  }

  public void useAjaxsltXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.useAjaxsltXPathLibrary();
  }

  public void allowNativeXpath(boolean allow) {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.allowNativeXpath(allow);
  }

  public void registerNamespace(String prefix, String namespace) {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.registerNamespace(prefix, namespace);
  }

  public String getNamespace(String prefix) {
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.getNamespace(prefix);
  }

  public void addScript(String scriptContent, String scriptTagId){
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.addScript(scriptContent, scriptTagId);
  }

  public void removeScript(String scriptTagId){
    SeleniumWrapper wrapper = SessionManager.getSession().getLookup().lookById("wrapper");
    wrapper.removeScript(scriptTagId);
  }

  public EngineState getEngineState(){
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");
    return api.getEngineState();
  }

  def pause(int milliseconds) {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = BundleProcessor.instance
    processor.flush()
    
    Helper.pause(milliseconds);
  }

  public void useEngineLog(boolean isUse){
    TelluriumApi api = SessionManager.getSession().getLookup().lookById("api");
    api.useEngineLog(isUse);
  }

  public void dumpEnvironment(){
    println  SessionManager.getSession().env.toString();
  }

}