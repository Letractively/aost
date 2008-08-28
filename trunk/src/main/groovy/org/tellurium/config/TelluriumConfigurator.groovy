package org.tellurium.config

import org.tellurium.config.TelluriumConfigParser
import org.tellurium.server.EmbeddedSeleniumServer
import org.tellurium.connector.SeleniumConnector
import org.tellurium.ddt.DataProvider
import org.tellurium.ddt.object.mapping.io.PipeDataReader
import org.tellurium.test.helper.DefaultResultListener
import org.tellurium.test.helper.SimpleResultReporter
import org.tellurium.test.helper.XMLResultReporter
import org.tellurium.test.helper.StreamXMLResultReporter
import org.tellurium.test.helper.ConsoleOutput
import org.tellurium.test.helper.FileOutput
import org.tellurium.ddt.object.mapping.io.CSVDataReader

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
        server.setProperty("runSeleniumServerInternally", conf.tellurium.embeddedserver.runInternally)
    }

    protected void configEmbeededServerDefaultValues(EmbeddedSeleniumServer server){
        server.setProperty("port", 4444)
        server.setProperty("useMultiWindows", false)
        server.setProperty("runSeleniumServerInternally", true)        
    }

    protected void configSeleniumConnector(SeleniumConnector connector){
        connector.setProperty("port", Integer.parseInt(conf.tellurium.connector.port))
        connector.setProperty("baseURL", conf.tellurium.connector.baseUrl)
        connector.setProperty("browser", conf.tellurium.connector.browser)
    }

    protected void configSeleniumConnectorDefaultValues(SeleniumConnector connector){
        connector.setProperty("port", 4444)
        connector.setProperty("baseURL", "http://localhost:8080")
        connector.setProperty("browser", "*chrome")
    }

    protected void configDataProvider(DataProvider dataProvider){
        if("PipeFileReader".equalsIgnoreCase(conf.tellurium.datadriven.dataprovider.reader)){
            dataProvider.setProperty("reader", new PipeDataReader())
        }else if("CVSFileReader".equalsIgnoreCase(conf.tellurium.datadriven.dataprovider.reader)){
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
            }else{
                println "Unsupported Configurable type!"
            }

        }

    }

}