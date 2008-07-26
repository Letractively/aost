package aost.test

import aost.framework.AostFramework
import aost.bootstrap.AostSupport
import aost.connector.SeleniumConnector
import aost.dsl.UiDslParser
import aost.datadriven.object.mapping.FieldSetParser
import aost.dsl.DefaultDdDslContext

/**
 * AOST Data Driven test using Groovy.
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
abstract class AostDataDrivenTest extends GroovyTestCase{
    //put all your test script here
    //For data driveToEnd test, you will only have one test method where you should put all
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
}