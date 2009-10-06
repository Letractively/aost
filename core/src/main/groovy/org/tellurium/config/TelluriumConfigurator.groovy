package org.tellurium.config

import org.tellurium.access.Accessor
import org.tellurium.builder.UiObjectBuilder
import org.tellurium.builder.UiObjectBuilderRegistry
import org.tellurium.config.Configurable
import org.tellurium.config.Configurator
import org.tellurium.config.TelluriumConfigParser
import org.tellurium.connector.SeleniumConnector
import org.tellurium.ddt.DataProvider
import org.tellurium.ddt.object.mapping.io.CSVDataReader
import org.tellurium.ddt.object.mapping.io.PipeDataReader
import org.tellurium.dispatch.Dispatcher
import org.tellurium.event.EventHandler
import org.tellurium.server.EmbeddedSeleniumServer
import org.tellurium.widget.WidgetConfigurator
import org.tellurium.test.helper.*
import org.tellurium.connector.CustomSelenium
import org.tellurium.i18n.InternationalizationManager;

/**
 * Tellurium Configurator
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 3, 2008
 *
 */
class TelluriumConfigurator extends TelluriumConfigParser implements Configurator{

    protected void configEmbeededServer(EmbeddedSeleniumServer server) {
        server.setProperty("port", Integer.parseInt(conf.tellurium.embeddedserver.port))
        server.setProperty("useMultiWindows", conf.tellurium.embeddedserver.useMultiWindows)
        server.setProperty("trustAllSSLCertificates", conf.tellurium.embeddedserver.trustAllSSLCertificates)
        server.setProperty("runSeleniumServerInternally", conf.tellurium.embeddedserver.runInternally)
        server.setProperty("profileLocation", conf.tellurium.embeddedserver.profile)
        server.setProperty("userExtension", conf.tellurium.embeddedserver.userExtension)
    }

  protected void configi18nManager(InternationalizationManager i18nManager , conf) {
    String definedLocales = null
    def locale = null

    if(conf != null  && conf.tellurium!=null && conf.tellurium.i18n!=null && conf.tellurium.i18n.locales!=null ){
      definedLocales = conf.tellurium.i18n.locales
      String[] localeString = definedLocales.split("_")
      if(localeString.length == 2)
    	  locale = new Locale(localeString[0] , localeString[1])
      else
    	  locale = Locale.getDefault();
    }
    else
      locale = Locale.getDefault();
    
    if(locale == null)
    	locale = new Locale("en" , "EN");
    i18nManager.createDefaultResourceBundle(locale);
  }

  protected void configEmbeededServerDefaultValues(EmbeddedSeleniumServer server){
        server.setProperty("port", 4444)
        server.setProperty("useMultiWindows", false)
        server.setProperty("runSeleniumServerInternally", true)        
    }

    protected void configSeleniumConnector(SeleniumConnector connector){
        connector.setProperty("seleniumServerHost", conf.tellurium.connector.serverHost)
        connector.setProperty("port", Integer.parseInt(conf.tellurium.connector.port))
        connector.setProperty("baseURL", conf.tellurium.connector.baseUrl)
        connector.setProperty("browser", conf.tellurium.connector.browser)
        connector.setProperty("userExtension", conf.tellurium.embeddedserver.userExtension)
        String clazz = conf.tellurium.connector.customClass
        if(clazz != null && clazz.trim().length() > 0)
          connector.setProperty("customClass", Class.forName(clazz).newInstance())
        String options = conf.tellurium.connector.options
        if(options != null && options.trim().length() > 0){
          connector.setProperty("options", options);
        }
    }

    protected void configSeleniumConnectorDefaultValues(SeleniumConnector connector){
        connector.setProperty("seleniumServerHost", "localhost")
        connector.setProperty("port", 4444)
        connector.setProperty("baseURL", "http://localhost:8080")
        connector.setProperty("browser", "*chrome")
        connector.setProperty("customClass", null)
        connector.setProperty("options", null)
    }

    protected void configDataProvider(DataProvider dataProvider){
        if("PipeFileReader".equalsIgnoreCase(conf.tellurium.datadriven.dataprovider.reader)){
            dataProvider.setProperty("reader", new PipeDataReader())
        }else if("CSVFileReader".equalsIgnoreCase(conf.tellurium.datadriven.dataprovider.reader)){
            dataProvider.setProperty("reader", new CSVDataReader())
        }else{        	
            println i18nManager.translate("TelluriumConfigurator.UnsupportedReader" , {conf.tellurium.datadriven.dataprovider.reader})
        }
    }

    protected void configDataProviderDefaultValues(DataProvider dataProvider){
       dataProvider.setProperty("reader", new PipeDataReader())     
    }

    protected void configResultListener(DefaultResultListener resultListener){
        if("SimpleResultReporter".equalsIgnoreCase(conf.tellurium.test.result.reporter)){
            resultListener.setProperty("reporter", new SimpleResultReporter())
        }
        if("XMLResultReporter".equalsIgnoreCase(conf.tellurium.test.result.reporter)){
             resultListener.setProperty("reporter", new XMLResultReporter())
        }
        if("StreamXMLResultReporter".equalsIgnoreCase(conf.tellurium.test.result.reporter)){
             resultListener.setProperty("reporter", new StreamXMLResultReporter())
        }
        if("Console".equalsIgnoreCase(conf.tellurium.test.result.output)){
            resultListener.setProperty("output", new ConsoleOutput())     
        }
        if("File".equalsIgnoreCase(conf.tellurium.test.result.output)){
            resultListener.setProperty("output", new FileOutput())     
        }
    }

    protected void configResultListenerDefaultValues(DefaultResultListener resultListener){
        resultListener.setProperty("reporter", new XMLResultReporter())
        resultListener.setProperty("output", new ConsoleOutput())
    }

    protected void configFileOutput(FileOutput fileOutput){
        fileOutput.setProperty("fileName", conf.tellurium.test.result.filename)
    }

    protected void configFileOutputDefaultValues(FileOutput fileOutput){
        fileOutput.setProperty("fileName", "TestResult.output")
    }

    protected void configUiObjectBuilder(UiObjectBuilderRegistry uobRegistry){
        Map builders = conf.tellurium.uiobject.builder

        if(builders != null && (!builders.isEmpty())){
            builders.each { key, value ->
                UiObjectBuilder builder = (UiObjectBuilder) Class.forName(value).newInstance()
                uobRegistry.registerBuilder(key, builder)
            }
        }
    }

    protected void configUiObjectBuilderDefaultValues(UiObjectBuilderRegistry uobRegistry){

    }

    protected void configWidgetModule(WidgetConfigurator wgConfigurator){
        wgConfigurator.configWidgetModule(conf.tellurium.widget.module.included)
    }

    protected void configWidgetModuleDefaultValues(WidgetConfigurator wgConfigurator){

    }

    protected void configEventHandler(EventHandler eventHandler){
        if(conf.tellurium.eventhandler.checkElement){
            eventHandler.mustCheckElement()
        }else{
            eventHandler.notCheckElement()
        }
        if(conf.tellurium.eventhandler.extraEvent){
            eventHandler.useExtraEvent()
        }else{
            eventHandler.noExtraEvent()
        }
    }

    protected void configEventHandlerDefaultValues(EventHandler eventHandler){
//        eventHandler.mustCheckElement()
//        eventHandler.useExtraEvent()
          eventHandler.notCheckElement()
          eventHandler.noExtraEvent()
    }

    protected void configAccessor(Accessor accessor){
        if(conf.tellurium.accessor.checkElement){
            accessor.mustCheckElement()
        }else{
            accessor.notCheckElement()
        }
    }

    protected void configAccessorDefaultValues(Accessor accessor){
        accessor.mustCheckElement()
    }

    protected void configDispatcher(Dispatcher dispatcher){
        dispatcher.captureScreenshot = conf.tellurium.test.exception.captureScreenshot
        dispatcher.filenamePattern = conf.tellurium.test.exception.filenamePattern
    }

    protected void configDispatcherDefaultValues(Dispatcher dispatcher){
        dispatcher.captureScreenshot = false
        dispatcher.filenamePattern = "Screenshot?.png"
    }

  public void config(Configurable configurable) {
    //configuration file TelluriumConfig.groovy exists
    InternationalizationManager i18nManager = new InternationalizationManager();
    configi18nManager(i18nManager , conf) ;
    
    if (conf != null) {
      if (configurable instanceof EmbeddedSeleniumServer) {
        println i18nManager.translate("TelluriumConfigurator.EmbeddedSeleniumServer")
        configEmbeededServer(configurable)
      } else if (configurable instanceof SeleniumConnector) {
        println i18nManager.translate("TelluriumConfigurator.SeleniumClient")
        configSeleniumConnector(configurable)
      } else if (configurable instanceof DataProvider) {
        println i18nManager.translate("TelluriumConfigurator.DataProvider")
        configDataProvider(configurable)
      } else if (configurable instanceof DefaultResultListener) {
        println i18nManager.translate("TelluriumConfigurator.ResultListener")
        configResultListener(configurable)
      } else if (configurable instanceof FileOutput) {
        println i18nManager.translate("TelluriumConfigurator.FileOutput")
        configFileOutput(configurable)
      } else if (configurable instanceof UiObjectBuilderRegistry) {
        println i18nManager.translate("TelluriumConfigurator.UIObjectBuilder")
        configUiObjectBuilder(configurable)
      } else if (configurable instanceof WidgetConfigurator) {
        println i18nManager.translate("TelluriumConfigurator.WidgetModules");
        configWidgetModule(configurable)
      } else if (configurable instanceof EventHandler) {
        println i18nManager.translate("TelluriumConfigurator.EventHandler");
        configEventHandler(configurable)
      } else if (configurable instanceof Accessor) {
        println i18nManager.translate("TelluriumConfigurator.DataAccessor");
        configAccessor(configurable)
      } else if (configurable instanceof Dispatcher) {
        println i18nManager.translate("TelluriumConfigurator.Dispatcher");
        configDispatcher(configurable)
      } else {
        println i18nManager.translate("TelluriumConfigurator.UnsupportedType");
      }
    } else {
      //use default values instead
      if (configurable instanceof EmbeddedSeleniumServer) {
        println i18nManager.translate("TelluriumConfigurator.EmbeddedSeleniumServer.default")
        configEmbeededServerDefaultValues(configurable)
      } else if (configurable instanceof SeleniumConnector) {
        println i18nManager.translate("TelluriumConfigurator.SeleniumClient.default")
        configSeleniumConnectorDefaultValues(configurable)
      } else if (configurable instanceof DataProvider) {
        println i18nManager.translate("TelluriumConfigurator.DataProvider.default")
        configDataProviderDefaultValues(configurable)
      } else if (configurable instanceof DefaultResultListener) {
        println i18nManager.translate("TelluriumConfigurator.ResultListener.default")
        configResultListenerDefaultValues(configurable)
      } else if (configurable instanceof FileOutput) {
        println i18nManager.translate("TelluriumConfigurator.FileOutput.default")
        configFileOutputDefaultValues(configurable)
      } else if (configurable instanceof UiObjectBuilderRegistry) {
        println i18nManager.translate("TelluriumConfigurator.UIObjectBuilder.default")
        configUiObjectBuilderDefaultValues(configurable)
      } else if (configurable instanceof WidgetConfigurator) {
        println i18nManager.translate("TelluriumConfigurator.WidgetConfigurator.default")
        configWidgetModuleDefaultValues(configurable)
      } else if (configurable instanceof EventHandler) {
        println i18nManager.translate("TelluriumConfigurator.EventHandler.default")
        configEventHandlerDefaultValues(configurable)
      } else if (configurable instanceof Accessor) {
        println i18nManager.translate("TelluriumConfigurator.DataAccessor.default")
        configAccessorDefaultValues(configurable)
      } else if (configurable instanceof Dispatcher) {
        println i18nManager.translate("TelluriumConfigurator.Dispatcher.default")
        configDispatcherDefaultValues(configurable)
      } else {
        println i18nManager.translate("TelluriumConfigurator.UnsupportedType");
      }

    }

  }

}