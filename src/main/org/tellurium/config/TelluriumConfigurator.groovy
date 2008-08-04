package org.tellurium.config

import org.tellurium.config.TelluriumConfigParser
import org.tellurium.server.EmbeddedSeleniumServer
import org.tellurium.connector.SeleniumConnector

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

    protected void configSeleniumConnector(SeleniumConnector connector){
        connector.setProperty("port", Integer.parseInt(conf.tellurium.connector.port))
        connector.setProperty("baseURL", conf.tellurium.connector.baseUrl)
        connector.setProperty("browser", conf.tellurium.connector.browser)
    }

    public void config(Configurable configurable) {
        if(conf != null){
            if(configurable instanceof EmbeddedSeleniumServer){
                println "Configure Embedded Selenium Server"
                configEmbeededServer(configurable)
            }else if(configurable instanceof SeleniumConnector){
                println "Configure Selenium Client"
                configSeleniumConnector(configurable)
            }else{
                println "Unsupported Configurable type!"
            }
        }

    }

}