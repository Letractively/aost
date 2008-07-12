package aost.framework

import aost.access.Accessor
import aost.access.AccessorMetaClass
import aost.builder.UiObjectBuilderRegistry
import aost.builder.UiObjectBuilderRegistryMetaClass
import aost.client.SeleniumClient
import aost.client.SeleniumClientMetaClass
import aost.connector.SeleniumConnector
import aost.connector.SeleniumConnectorMetaClass
import aost.dispatch.Dispatcher
import aost.dispatch.DispatcherMetaClass
import aost.event.EventHandler
import aost.event.EventHandlerMetaClass
import aost.locator.LocatorProcessor
import aost.locator.LocatorProcessorMetaClass
import aost.server.EmbeddedSeleniumServer
import aost.builder.UiObjectBuilder

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
}