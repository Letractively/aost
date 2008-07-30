package org.tellurium.test

import org.tellurium.framework.AostFramework
import org.tellurium.bootstrap.AostSupport
import org.tellurium.connector.SeleniumConnector
import org.tellurium.dsl.UiDslParser
import org.tellurium.datadriven.object.mapping.FieldSetParser
import org.tellurium.dsl.DefaultDdDslContext
import org.tellurium.test.helper.TestResult

/**
 * AOST Data Driven test using Groovy.
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
abstract class AostDataDrivenTest extends GroovyTestCase{
    protected static final String STEP = "step"
    protected static final String STEP_OVER = "stepOver"
    protected static final String STEP_TO_END = "stepToEnd"
    protected static final String CLOSE_DATA = "closeData"
    
    //put all your test script here
    //For data stepToEnd test, you will only have one test method where you should put all
    //your test script there
    abstract void testDataDriven()
    
    protected DefaultDdDslContext ddc = new DefaultDdDslContext()
    protected UiDslParser ui = ddc.getUiDslParser()
    protected FieldSetParser fs = ddc.getFieldSetParser()

    protected AostFramework af
    protected SeleniumConnector connector

    public void openUrl(String url){
        getConnector().connectUrl(url)
    }

    public SeleniumConnector getConnector() {
        if(this.connector == null)
            return new SeleniumConnector()
        else
            return this.connector
    }

    protected def init(){
        af = AostSupport.addSupport()
        af.start()
        this.connector = af.getConnector()
   }

    //try to delegate missing methods to the DdDslContext, if still could not find,
    //throw a MissingMethodException
    protected def methodMissing(String name, args) {
         
         if(ddc.metaClass.respondsTo(ddc, name, args)){
              return ddc.invokeMethod(name, args)
         }

        throw new MissingMethodException(name, AostDataDrivenTest.class, args)
     }

     protected void shutDown() {
        if(af != null)
            af.stop()
     }

    public void setUp(){
        init()
    }

    public void tearDown(){
        shutDown()
    }

/*    public boolean compareResult(expected, actual){
        boolean passed = true

        try{
            assertEquals(expected, actual)
        }catch(Exception e){
            passed = false
        }

        TestResult result = new TestResult()
        result.expected = expected
        result.actual = actual
        result.passed = passed
        ddc.listenForResult(result)
    }*/

}