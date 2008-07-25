package aost.dsl

import aost.bootstrap.AostSupport
import aost.test.DslAostSeleneseTestCase
import aost.framework.AostFramework

class DslScriptEngine extends DdDslContext{
    
    private DslAostSeleneseTestCase aost = new DslAostSeleneseTestCase()
    protected AostFramework af

    protected def init(){
        af = AostSupport.addSupport()
        af.start()
        aost.connector = af.connector
   }

    protected def openUrl(String url){
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
        if(af != null)
            af.stop()
     }
}