package org.tellurium.framework

import org.tellurium.access.Accessor
import org.tellurium.access.AccessorMetaClass
import org.tellurium.builder.UiObjectBuilder
import org.tellurium.builder.UiObjectBuilderRegistry
import org.tellurium.builder.UiObjectBuilderRegistryMetaClass
import org.tellurium.client.SeleniumClient
import org.tellurium.client.SeleniumClientMetaClass
import org.tellurium.config.CustomConfig
import org.tellurium.config.TelluriumConfigurator
import org.tellurium.config.TelluriumConfiguratorMetaClass
import org.tellurium.connector.SeleniumConnector
import org.tellurium.connector.SeleniumConnectorMetaClass
import org.tellurium.dispatch.Dispatcher
import org.tellurium.dispatch.DispatcherMetaClass
import org.tellurium.event.EventHandler
import org.tellurium.event.EventHandlerMetaClass
import org.tellurium.extend.Extension
import org.tellurium.extend.ExtensionMetaClass
import org.tellurium.locator.LocatorProcessor
import org.tellurium.locator.LocatorProcessorMetaClass
import org.tellurium.server.EmbeddedSeleniumServer
import org.tellurium.widget.WidgetConfigurator
import org.tellurium.i18n.InternationalizationManager;
import org.tellurium.i18n.InternationalizationManagerMetaClass;

/**
 * Put all initialization and cleanup jobs for the Tellurium framework here
 *
 * User: Jian Fang (Jian.Fang@jtv.com) 
 * Date: Jun 2, 2008
 */
class TelluriumFramework {

  private EmbeddedSeleniumServer server

  private SeleniumConnector connector

  private SeleniumClient client

  private boolean runEmbeddedSeleniumServer = true

  private TelluriumConfigurator telluriumConfigurator


  TelluriumFramework() {

//    By default ExpandoMetaClass doesn't do inheritance. To enable this you must call ExpandoMetaClass.enableGlobally()
//    before your app starts such as in the main method or servlet bootstrap
//        ExpandoMetaClass.enableGlobally()

    def registry = GroovySystem.metaClassRegistry

    registry.setMetaClass(UiObjectBuilderRegistry, new UiObjectBuilderRegistryMetaClass())

    registry.setMetaClass(SeleniumClient, new SeleniumClientMetaClass())

    registry.setMetaClass(Dispatcher, new DispatcherMetaClass())

    registry.setMetaClass(Extension, new ExtensionMetaClass())

    registry.setMetaClass(InternationalizationManager ,new InternationalizationManagerMetaClass())
   

    registry.setMetaClass(Accessor, new AccessorMetaClass())

    registry.setMetaClass(EventHandler, new EventHandlerMetaClass())

    registry.setMetaClass(LocatorProcessor, new LocatorProcessorMetaClass())

    registry.setMetaClass(SeleniumConnector, new SeleniumConnectorMetaClass())
    registry.setMetaClass(TelluriumConfigurator, new TelluriumConfiguratorMetaClass())

    telluriumConfigurator = new TelluriumConfigurator()

    String fileName = "TelluriumConfig.groovy"

//    telluriumConfigurator.parse("TelluriumConfig.groovy")
    File file = new File(fileName)
    if(file != null && file.exists()){
      println "Parse configuration file: ${fileName} from project root directory..."
      telluriumConfigurator.parse(file)
    }else{
      URL url = ClassLoader.getSystemResource(fileName)
      if(url != null){
        println "Parse configuration file: ${fileName} from class path..."
        telluriumConfigurator.parse(url)
      }else{
        println "Cannot find configuration file: ${fileName}, use default values"
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
      InternationalizationManager i18nManager = new InternationalizationManager()
      println i18nManager.translate("TelluriumFramework.OverwriteSeleniumServerSettings")

      server.runSeleniumServer()

      connector = new SeleniumConnector()
      telluriumConfigurator.config(connector)

      //overwrite the selenium connector settings with these provided by custom configuration
      connector.setProperty("browser", customConfig.getBrowser())
      connector.setProperty("port", customConfig.getPort())
      if(customConfig.getServerHost() != null){
        //only overwrite the server host if it is set
        connector.setProperty("seleniumServerHost", customConfig.getServerHost())
      }
      println i18nManager.translate("TelluriumFramework.OverwriteSeleniumConnectorSettings")

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
}