package org.telluriumsource.framework

import org.telluriumsource.component.data.Accessor
import org.telluriumsource.component.data.AccessorMetaClass
import org.telluriumsource.ui.builder.UiObjectBuilder
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry
import org.telluriumsource.ui.builder.UiObjectBuilderRegistryMetaClass
import org.telluriumsource.component.client.SeleniumClient
import org.telluriumsource.component.client.SeleniumClientMetaClass
import org.telluriumsource.framework.config.CustomConfig
import org.telluriumsource.framework.config.TelluriumConfigurator
import org.telluriumsource.framework.config.TelluriumConfiguratorMetaClass
import org.telluriumsource.component.connector.SeleniumConnector
import org.telluriumsource.component.connector.SeleniumConnectorMetaClass
import org.telluriumsource.component.dispatch.Dispatcher
import org.telluriumsource.component.dispatch.DispatcherMetaClass
import org.telluriumsource.component.event.EventHandler
import org.telluriumsource.component.event.EventHandlerMetaClass
import org.telluriumsource.component.custom.Extension
import org.telluriumsource.component.custom.ExtensionMetaClass
import org.telluriumsource.ui.locator.LocatorProcessor
import org.telluriumsource.ui.locator.LocatorProcessorMetaClass
import org.telluriumsource.server.EmbeddedSeleniumServer
import org.telluriumsource.ui.widget.WidgetConfigurator
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.dsl.GlobalDslContext
import org.telluriumsource.util.Helper
import org.telluriumsource.entity.CachePolicy
import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.entity.EngineState;
import org.telluriumsource.util.LogLevels
import org.telluriumsource.util.BaseUtil
import java.lang.reflect.Field

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
    name = name + BaseUtil.toBase62(System.currentTimeMillis());
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
    return createNewSession(id, env);
  }

  public Session createNewSession(String id, Map<String, Object> map){

    return createNewSession(Thread.currentThread().getName(), createNewEnvironment(map))
  }

  public Session createNewSession(Map<String, Object> map){

    return createNewSession(Thread.currentThread().getName(), createNewEnvironment(map))
  }

  public Session createNewSession(){
    return createNewSession(Thread.currentThread().getName(), env)
  }

  private createNewEnvironment(Map<String, Object> map){
    RuntimeEnvironment newEnv = env.clone();
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

  private SeleniumConnector connector;

  private SeleniumClient client;

  private RuntimeEnvironment env;

  private GlobalDslContext global;

  public void load() {

//    env = Environment.instance;

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
    String jsonConf = env.configString;
    if (jsonConf != null && jsonConf.trim().length() > 0) {
      println i18nBundle.getMessage("TelluriumFramework.ParseFromJSONString", jsonConf)
      telluriumConfigurator.parseJSON(jsonConf)
    } else {

      String fileName = System.properties.getProperty("telluriumConfigFile");
      if(fileName == null)
        fileName = env.configFileName;

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
    telluriumConfigurator.config(env)


    //global methods
    this.global = new GlobalDslContext();
*/
    env = telluriumConfigurator.createRuntimeEnvironment();
    
    Session session = createNewSession();
    SessionManager.setSession(session);   
  }

  public void disableEmbeddedSeleniumServer() {
    this.runEmbeddedSeleniumServer = false
  }

  public void start() {
    server = new EmbeddedSeleniumServer()

    telluriumConfigurator.config(server)

    server.runSeleniumServer()

//    connector = new SeleniumConnector()
//    telluriumConfigurator.config(connector)
  }

  public void start(CustomConfig customConfig) {
    if (customConfig == null) {
      //if no custom configuration, still start using the default one
      start()
    } else {

      server = new EmbeddedSeleniumServer()

      telluriumConfigurator.config(server)

      //overwrite the embedded server settings with these provided by custom configuration
      server.setProperty("runSeleniumServerInternally", customConfig.isRunInternally())
      server.setProperty("port", customConfig.getPort())
      server.setProperty("useMultiWindows", customConfig.isUseMultiWindows())
      server.setProperty("profileLocation", customConfig.getProfileLocation())
      IResourceBundle i18nBundle = env.getResourceBundle()
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

  public void stop() {
    if (connector != null) {
      connector.disconnectSeleniumServer()
    }

    if (runEmbeddedSeleniumServer && (server != null)) {
      server.stopSeleniumServer()
    }
  }

  //register ui object builder
  //users can overload the builders or add new builders for new ui objects
  //by call this method
  public void registerBuilder(String uiObjectName, UiObjectBuilder builder) {
    UiObjectBuilderRegistry registry = new UiObjectBuilderRegistry()
    registry.registerBuilder(uiObjectName, builder)
  }

  public SeleniumConnector getConnector() {
    return this.connector;
  }

  public boolean isUseLocatorWithCache(){
    return env.isUseLocatorWithCache();
  }

  public void useLocatorWithCache(boolean isUse){
    env.useLocatorWithCache(isUse);
  }

  public void useMacroCmd(boolean isUse) {
    env.useBundle(isUse);
  }

  public setMaxMacroCmd(int max) {
    env.useMaxMacroCmd(max);
  }

  public int getMaxMacroCmd() {
    return env.myMaxMacroCmd();
  }

  public boolean isUseTelluriumApi() {
    return env.isUseTelluriumApi();
  }

  public void useTelluriumApi(boolean isUse) {
    env.useTelluriumApi(isUse);
    this.global.useTelluriumApi(isUse);
  }

  public void helpTest(){
    this.global.helpTest();
  }

  public void noTest(){
    this.global.noTest();
  }
  
  /*public void enableLogging(LogLevels loggingLevel) {
    env.enableLogging(loggingLevel);
    this.global.enableLogging(loggingLevel);
  }
  */

  public void useTrace(boolean isUse) {
    env.useTrace(isUse);
  }

  public void generateBugReport(boolean isUse) {
    env.generateBugReport(isUse);
  }

  public void showTrace() {
    this.global.showTrace();
  }

  public void setEnvironment(String name, Object value) {
    env.setCustomEnvironment(name, value);
  }

  public Object getEnvironment(String name) {
    return env.getCustomEnvironment(name);
  }

  public void useClosestMatch(boolean isUse){
    if (isUse) {
      this.global.enableClosestMatch();
    } else {
      this.global.disableClosestMatch();
    }      
  }

  public void useCssSelector(boolean isUse) {

    if (isUse) {
      this.global.enableCssSelector();
    } else {
      this.global.disableCssSelector();
    }
  }

  public void useCache(boolean isUse) {
    env.useCache(isUse);
    if (isUse) {
      this.global.enableCache();
    } else {
      this.global.disableCache();
    }
  }

  public void cleanCache() {
    this.global.cleanCache();
  }

  public boolean isUsingCache() {
    return this.global.getCacheState();
  }

  public void setCacheMaxSize(int size) {
    this.global.setCacheMaxSize(size);
  }

  public int getCacheSize() {
    return this.global.getCacheSize();
  }

  public int getCacheMaxSize() {
    return this.global.getCacheMaxSize();
  }

  public String getCacheUsage() {
    return this.global.getCacheUsage();
  }

  public void useCachePolicy(CachePolicy policy) {
    if (policy != null) {
      switch (policy) {
        case CachePolicy.DISCARD_NEW:
          this.global.useDiscardNewCachePolicy();
          break;
        case CachePolicy.DISCARD_OLD:
          this.global.useDiscardOldCachePolicy();
          break;
        case CachePolicy.DISCARD_LEAST_USED:
          this.global.useDiscardLeastUsedCachePolicy();
          break;
        case CachePolicy.DISCARD_INVALID:
          this.global.useDiscardInvalidCachePolicy();
          break;
      }
    }
  }

  public String getCurrentCachePolicy() {
    return this.global.getCurrentCachePolicy();
  }

  public void useDefaultXPathLibrary() {
    this.global.useDefaultXPathLibrary();
  }

  public void useJavascriptXPathLibrary() {
    this.global.useJavascriptXPathLibrary();
  }

  public void useAjaxsltXPathLibrary() {
    this.global.useAjaxsltXPathLibrary();
  }

  public void allowNativeXpath(boolean allow) {
    this.global.allowNativeXpath(allow);
  }

  public void registerNamespace(String prefix, String namespace) {
    this.global.registerNamespace(prefix, namespace);
  }

  public String getNamespace(String prefix) {
    this.global.getNamespace(prefix);
  }

  public void addScript(String scriptContent, String scriptTagId){
    this.global.addScript(scriptContent, scriptTagId);
  }

  public void removeScript(String scriptTagId){
    this.global.removeScript(scriptTagId);
  }

  public EngineState getEngineState(){
    return this.global.getEngineState();  
  }

  def pause(int milliseconds) {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = BundleProcessor.instance
    processor.flush()
    
    Helper.pause(milliseconds);
  }

  public void useEngineLog(boolean isUse){
    this.global.useEngineLog(isUse);
  }

  public void dumpEnvironment(){
    println env.toString();
  }

}