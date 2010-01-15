package org.telluriumsource.framework

import org.telluriumsource.access.Accessor
import org.telluriumsource.access.AccessorMetaClass
import org.telluriumsource.builder.UiObjectBuilder
import org.telluriumsource.builder.UiObjectBuilderRegistry
import org.telluriumsource.builder.UiObjectBuilderRegistryMetaClass
import org.telluriumsource.client.SeleniumClient
import org.telluriumsource.client.SeleniumClientMetaClass
import org.telluriumsource.config.CustomConfig
import org.telluriumsource.config.TelluriumConfigurator
import org.telluriumsource.config.TelluriumConfiguratorMetaClass
import org.telluriumsource.connector.SeleniumConnector
import org.telluriumsource.connector.SeleniumConnectorMetaClass
import org.telluriumsource.dispatch.Dispatcher
import org.telluriumsource.dispatch.DispatcherMetaClass
import org.telluriumsource.event.EventHandler
import org.telluriumsource.event.EventHandlerMetaClass
import org.telluriumsource.extend.Extension
import org.telluriumsource.extend.ExtensionMetaClass
import org.telluriumsource.locator.LocatorProcessor
import org.telluriumsource.locator.LocatorProcessorMetaClass
import org.telluriumsource.server.EmbeddedSeleniumServer
import org.telluriumsource.widget.WidgetConfigurator
import org.telluriumsource.i18n.IResourceBundle
import org.telluriumsource.dsl.GlobalDslContext
import org.telluriumsource.util.Helper
import org.telluriumsource.entity.CachePolicy
import org.telluriumsource.bundle.BundleProcessor;

/**
 * Put all initialization and cleanup jobs for the Tellurium framework here
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jun 2, 2008
 */
class TelluriumFramework {

  private EmbeddedSeleniumServer server;

  private SeleniumConnector connector;

  private SeleniumClient client;

  private boolean runEmbeddedSeleniumServer = true;

  private TelluriumConfigurator telluriumConfigurator;

  private Environment env;

  private GlobalDslContext global;

  TelluriumFramework() {

    env = Environment.instance;

//    By default ExpandoMetaClass doesn't do inheritance. To enable this you must call ExpandoMetaClass.enableGlobally()
//    before your app starts such as in the main method or servlet bootstrap
//        ExpandoMetaClass.enableGlobally()

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

    telluriumConfigurator = new TelluriumConfigurator()

    String fileName = "TelluriumConfig.groovy"
    IResourceBundle i18nBundle = env.myResourceBundle()

//    telluriumConfigurator.parse("TelluriumConfig.groovy")
    File file = new File(fileName)
    if (file != null && file.exists()) {
      println i18nBundle.getMessage("TelluriumFramework.ParseFromRootDirectory" , fileName)
      telluriumConfigurator.parse(file)
    } else {
      URL url = ClassLoader.getSystemResource(fileName)
      if (url != null) {
        println i18nBundle.getMessage("TelluriumFramework.ParseFromClassPath" , fileName)
        telluriumConfigurator.parse(url)
      } else {
          println i18nBundle.getMessage("TelluriumFramework.CannotFindConfigFile" , fileName)
      }
    }

    //configure custom UI ojects
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
  }

  public void disableEmbeddedSeleniumServer() {
    this.runEmbeddedSeleniumServer = false
  }

  public void start() {
    server = new EmbeddedSeleniumServer()

    telluriumConfigurator.config(server)

    server.runSeleniumServer()

    connector = new SeleniumConnector()
    telluriumConfigurator.config(connector)

//    connector.connectSeleniumServer()
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
      IResourceBundle i18nBundle = env.myResourceBundle()
      println i18nBundle.getMessage("TelluriumFramework.OverwriteSeleniumServerSettings")

      server.runSeleniumServer()

      connector = new SeleniumConnector()
      telluriumConfigurator.config(connector)

      //overwrite the selenium connector settings with these provided by custom configuration
      connector.setProperty("browser", customConfig.getBrowser())
      connector.setProperty("port", customConfig.getPort())
      if (customConfig.getServerHost() != null) {
        //only overwrite the server host if it is set
        connector.setProperty("seleniumServerHost", customConfig.getServerHost())
      }
      println i18nBundle.getMessage("TelluriumFramework.OverwriteSeleniumConnectorSettings")

//      connector.connectSeleniumServer()

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
    return this.connector
  }

  public void useMacroCmd(boolean isUse) {
    env.useBundle(isUse)
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

  public void useTrace(boolean isUse) {
    env.useTrace(isUse);
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

  public Map<String, Long> getCacheUsage() {
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

  def pause(int milliseconds) {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = BundleProcessor.instance
    processor.flush()
    
    Helper.pause(milliseconds);
  }

}