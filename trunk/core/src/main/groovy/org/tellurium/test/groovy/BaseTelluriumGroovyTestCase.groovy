package org.tellurium.test.groovy

import org.tellurium.config.CustomConfig
import org.tellurium.connector.SeleniumConnector

abstract class BaseTelluriumGroovyTestCase extends GroovyTestCase{
    //custom configuration
    protected CustomConfig customConfig = null

    public abstract SeleniumConnector getConnector()

    public void openUrl(String url){
        getConnector().connectSeleniumServer()
        getConnector().connectUrl(url)
    }

    public void connectUrl(String url) {
         getConnector().connectUrl(url)
    }

    public void connectSeleniumServer(){
        getConnector().connectSeleniumServer()
    }
    
    public void disconnectSeleniumServer(){
         getConnector().disconnectSeleniumServer()
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation)
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost)
    }
}