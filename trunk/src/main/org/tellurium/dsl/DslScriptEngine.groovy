package org.tellurium.dsl

import org.tellurium.bootstrap.TelluriumSupport
import org.tellurium.test.groovy.DslTelluriumGroovyTestCase
import org.tellurium.framework.TelluriumFramework
import org.tellurium.test.helper.TestResult
import org.tellurium.test.groovy.DslTelluriumGroovyTestCase
import org.tellurium.bootstrap.TelluriumSupport
import org.tellurium.test.groovy.DslTelluriumGroovyTestCase

class DslScriptEngine extends DdDslContext{
    
    private DslTelluriumGroovyTestCase aost = new DslTelluriumGroovyTestCase()
    protected TelluriumFramework af

    protected def init(){
        af = TelluriumSupport.addSupport()
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
         
         if(DslTelluriumGroovyTestCase.metaClass.respondsTo(aost, name, args)){
              return aost.invokeMethod(name, args)
         }

        throw new MissingMethodException(name, DslScriptEngine.class, args)
     }

     protected void shutDown() {
        if(af != null)
            af.stop()
     }

/*    public boolean compareResult(expected, actual){
        boolean passed = true

        try{
            tellurium.assertEquals(expected, actual)
        }catch(Exception e){
            passed = false
        }

        TestResult result = new TestResult()
        result.expected = expected
        result.actual = actual
        result.passed = passed
        listenForResult(result)
    }*/
}