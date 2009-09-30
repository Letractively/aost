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
        server.setProperty("avoidProxy", conf.tellurium.embeddedserver.avoidProxy)
        server.setProperty("browserSessionReuse", conf.tellurium.embeddedserver.browserSessionReuse)
        server.setProperty("ensureCleanSession", conf.tellurium.embeddedserver.ensureCleanSession)
        server.setProperty("debugMode", conf.tellurium.embeddedserver.debugMode)
        server.setProperty("interactive", conf.tellurium.embeddedserver.interactive)
        server.setProperty("timeoutInSeconds", conf.tellurium.embeddedserver.timeoutInSeconds)
        server.setProperty("profileLocation", conf.tellurium.embeddedserver.profile)
        server.setProperty("userExtension", conf.tellurium.embeddedserver.userExtension)
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
            println "Unsupported reader ${conf.tellurium.datadriven.dataprovider.reader} for data provider"
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
        if(conf != null){
            if(configurable instanceof EmbeddedSeleniumServer){
                println "Configure Embedded Selenium Server using configuration file"
                configEmbeededServer(configurable)
            }else if(configurable instanceof SeleniumConnector){
                println "Configure Selenium Client using configuration file"
                configSeleniumConnector(configurable)
            }else if(configurable instanceof DataProvider){
                println "Configure Data Provider using configuration file"
                configDataProvider(configurable)
            }else if(configurable instanceof DefaultResultListener){
                println "Configure Result Listener using configuration file"
                configResultListener(configurable)
            }else if(configurable instanceof FileOutput){
                println "Configure File Output using configuration file"
                configFileOutput(configurable)
            }else if(configurable instanceof UiObjectBuilderRegistry){
                println "Configure UI Object Builders using configuration file"
                configUiObjectBuilder(configurable)
            }else if(configurable instanceof WidgetConfigurator){
                println "Configure widget modules using configuration file"
                configWidgetModule(configurable)
            }else if(configurable instanceof EventHandler){
                println "Configure event handler using configuration file"
                configEventHandler(configurable)
            }else if(configurable instanceof Accessor){
                println "Configure data accessor using configuration file"
                configAccessor(configurable)
            }else if(configurable instanceof Dispatcher){
                println "Configure dispatcher using configuration file"
                configDispatcher(configurable)
            }else{
                println "Unsupported Configurable type!"
            }
        }else{
            //use default values instead
            if(configurable instanceof EmbeddedSeleniumServer){
                println "Configure Embedded Selenium Server with default values"
                configEmbeededServerDefaultValues(configurable)
            }else if(configurable instanceof SeleniumConnector){
                println "Configure Selenium Client with default values"
                configSeleniumConnectorDefaultValues(configurable)
            }else if(configurable instanceof DataProvider){
                println "Configure Data Provider with default values"
                configDataProviderDefaultValues(configurable)
            }else if(configurable instanceof DefaultResultListener){
                println "Configure Result Listener with default values"
                configResultListenerDefaultValues(configurable)
            }else if(configurable instanceof FileOutput){
                println "Configure File Output with default values"
                configFileOutputDefaultValues(configurable)               
            }else if(configurable instanceof UiObjectBuilderRegistry){
                println "Configure UI Object Builders with default values"
                configUiObjectBuilderDefaultValues(configurable)
            }else if(configurable instanceof WidgetConfigurator){
                println "Configure widget modules with default values"
                configWidgetModuleDefaultValues(configurable)
            }else if(configurable instanceof EventHandler){
                println "Configure event handler with default values"
                configEventHandlerDefaultValues(configurable)
            }else if(configurable instanceof Accessor){
                println "Configure data accessor with default values"
                configAccessorDefaultValues(configurable)
            }else if(configurable instanceof Dispatcher){
                println "Configure dispatcher with default values"
                configDispatcherDefaultValues(configurable)
            }else{
                println "Unsupported Configurable type!"
            }

        }

    }

}