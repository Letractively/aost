package org.tellurium.dsl

import org.tellurium.bootstrap.AostSupport
import org.tellurium.test.DslAostSeleneseTestCase
import org.tellurium.framework.AostFramework
import org.tellurium.test.helper.TestResult

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