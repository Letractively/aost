package aost.test

import aost.connector.SeleniumConnector

abstract class AostSeleneseTestCase extends GroovyTestCase{

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

}