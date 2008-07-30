package org.tellurium.framework

import org.tellurium.access.Accessor
import org.tellurium.access.AccessorMetaClass
import org.tellurium.builder.UiObjectBuilderRegistry
import org.tellurium.builder.UiObjectBuilderRegistryMetaClass
import org.tellurium.client.SeleniumClient
import org.tellurium.client.SeleniumClientMetaClass
import org.tellurium.connector.SeleniumConnector
import org.tellurium.connector.SeleniumConnectorMetaClass
import org.tellurium.dispatch.Dispatcher
import org.tellurium.dispatch.DispatcherMetaClass
import org.tellurium.event.EventHandler
import org.tellurium.event.EventHandlerMetaClass
import org.tellurium.locator.LocatorProcessor
import org.tellurium.locator.LocatorProcessorMetaClass
import org.tellurium.server.EmbeddedSeleniumServer
import org.tellurium.builder.UiObjectBuilder

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