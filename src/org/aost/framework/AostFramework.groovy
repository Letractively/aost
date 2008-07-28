package org.aost.framework

import org.aost.access.Accessor
import org.aost.access.AccessorMetaClass
import org.aost.builder.UiObjectBuilderRegistry
import org.aost.builder.UiObjectBuilderRegistryMetaClass
import org.aost.client.SeleniumClient
import org.aost.client.SeleniumClientMetaClass
import org.aost.connector.SeleniumConnector
import org.aost.connector.SeleniumConnectorMetaClass
import org.aost.dispatch.Dispatcher
import org.aost.dispatch.DispatcherMetaClass
import org.aost.event.EventHandler
import org.aost.event.EventHandlerMetaClass
import org.aost.locator.LocatorProcessor
import org.aost.locator.LocatorProcessorMetaClass
import org.aost.server.EmbeddedSeleniumServer
import org.aost.builder.UiObjectBuilder

/**
 * Put all initialization and cleanup jobs for the AOST framework here
 *
 * User: Jian Fang (Jian.Fang@jtv.com) 
 * Date: Jun 2, 2008
 */
class AostFramework {

    private EmbeddedSeleniumServer server

    private SeleniumConnector connector

    private SeleniumClient client

    private boolean runEmbeddedSeleniumServer = true

    AostFramework(){
        
       def registry = GroovySystem.metaClassRegistry

       registry.setMetaClass(UiObjectBuilderRegistry, new UiObjectBuilderRegistryMetaClass())

       registry.setMetaClass(SeleniumClient, new SeleniumClientMetaClass())

       registry.setMetaClass(Dispatcher, new DispatcherMetaClass())

       registry.setMetaClass(Accessor, new AccessorMetaClass())

       registry.setMetaClass(EventHandler, new EventHandlerMetaClass())

       registry.setMetaClass(LocatorProcessor, new LocatorProcessorMetaClass())

       registry.setMetaClass(SeleniumConnector, new SeleniumConnectorMetaClass())
    }

    public void disableEmbeddedSeleniumServer(){
       this.runEmbeddedSeleniumServer = false
    }

    public void start(){
        server = new EmbeddedSeleniumServer();
        server.runSeleniumServerInternally = this.runEmbeddedSeleniumServer

        server.runSeleniumServer();

        connector  = new SeleniumConnector();
        connector.connectSeleniumServer();
    }

    public void stop(){
        if(connector != null){
            connector.disconnectSeleniumServer()
        }            
    }

    //register ui object builder
    //users can overload the builders or add new builders for new ui objects
    //by call this method
    public void registerBuilder(String uiObjectName, UiObjectBuilder builder){
        UiObjectBuilderRegistry registry = new UiObjectBuilderRegistry()
        registry.registerBuilder(uiObjectName, builder)
    }

    public SeleniumConnector getConnector(){
        return this.connector
    }
}