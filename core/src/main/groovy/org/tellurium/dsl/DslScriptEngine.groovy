package org.tellurium.dsl

import org.tellurium.bootstrap.TelluriumSupport
import org.tellurium.test.groovy.DslTelluriumGroovyTestCase
import org.tellurium.framework.TelluriumFramework

class DslScriptEngine extends DdDslContext{
    
    private DslTelluriumGroovyTestCase aost = new DslTelluriumGroovyTestCase()
    protected TelluriumFramework af

    protected def init(){
        af = TelluriumSupport.addSupport()
        af.start()
        aost.conn = af.connector
   }

    protected def openUrl(String url){
        aost.connectUrl(url)
    }

    //try to delegate missing methods to the AostTestCase, if still could not find,
    //throw a MissingMethodException
     protected def methodMissing(String name, args) {
         if("init".equals(name))
            return init()
         if("openUrl".equals(name))
            return openUrl(args)
         if("shutDown".equals(name))
            return shutDown()
         
         if(DslTelluriumGroovyTestCase.metaClass.respondsTo(aost, name, args)){
              return aost.invokeMethod(name, args)
         }

        throw new MissingMethodException(name, DslScriptEngine.class, args)
     }

     protected void shutDown() {
        if(af != null)
            af.stop()
     }
}