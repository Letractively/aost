package aost.dsl

import aost.server.EmbeddedSeleniumServer
import aost.connector.SeleniumConnector
import aost.bootstrap.AostSupport
import aost.test.DslAostSeleneseTestCase
import aost.framework.AostFramework

class DslScriptEngine extends DslContext{
    
    private DslAostSeleneseTestCase aost = new DslAostSeleneseTestCase()
//    private EmbeddedSeleniumServer server;
    protected AostFramework af

    protected def init(){
        af = AostSupport.addSupport()
        //server.runSeleniumServer()
        af.start()
//        connector = af.connector
//        connector = new SeleniumConnector()
//        aost.connector = connector
        aost.connector = af.connector
//        aost.connectSeleniumServer()
   }

    protected def openUrl(String url){
//        aost.openUrl(url)
        aost.connectUrl(url)
    }

    //try to delegate missing methods to the AostTestCase, if still could not find,
    //throw a MissingMethodException
     protected def methodMissing(String name, args) {
         if(name == "init")
            return init()
         if(name == "openUrl")
            return openUrl(args)
         if(name == "shutDown")
            return shutDown()
         
         if(DslAostSeleneseTestCase.metaClass.respondsTo(aost, name, args)){
              return aost.invokeMethod(name, args)
         }

        throw new MissingMethodException(name, DslScriptEngine.class, args)
     }

     protected void shutDown() {
       aost.disconnectSeleniumServer()
     }
}