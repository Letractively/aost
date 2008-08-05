package org.tellurium.test.ddt

import org.tellurium.dsl.DslContext
import org.tellurium.ddt.object.mapping.type.TypeHandlerRegistry
import org.tellurium.ddt.object.mapping.FieldSetRegistry
import org.tellurium.ddt.object.mapping.FieldSetParser
import org.tellurium.ddt.TestRegistry
import org.tellurium.ddt.object.mapping.type.TypeHandlerRegistryConfigurator
import org.tellurium.ddt.DataProvider

/**
 * In this module, you can define the following things:
 *
 *    1) UI modules using "ui.XXX"
 *
 *    2) FieldSet, i.e., the input data format that applies to the actions defined in this module, using "fs.XXX"
 *       also include user-defined custom typehandler using "typeHandler"
 *
 *    3) Actions using "defineAction"
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 31, 2008
 *
 */
abstract class TelluriumDataDrivenModule extends DslContext {

    //define your Data Driven module in this method
    abstract void defineModule()

    protected DataProvider dataProvider

    protected TypeHandlerRegistry thr
    protected FieldSetRegistry fsr

    protected FieldSetParser fs

    protected TestRegistry tr

    protected TelluriumDataDrivenTest runner

    //add delegator for all assertions
//    protected GroovyTestCase asserter = new GroovyTestCase()

    //this module will belong to which data driven test
    //this method will be used internal only
    public void belongTo(TelluriumDataDrivenTest tddTest){
//        asserter = (GroovyTestCase)tddTest
        this.runner = tddTest
    }

    // DSL to define your customer type handler such as
    // typeHandler "simpleDate", "tellurium.example.simpleDateTypeHandler"
    // here we assume that you have defined the tellurium.example.simpleDateTypeHandler class
    // it extends the TypeHandler interface
    public void typeHandler(String typeName, String fullClassName){
        TypeHandlerRegistryConfigurator.addCustomTypeHandler(thr, typeName, fullClassName)
    }

    //DSL to bind variables to data read from the file
    // def var1 = bind("dataset1.username")
    public def bind(String dataFieldId){

        return dataProvider.bind(dataFieldId)
    }

    public void defineTest(String name, Closure c){
        tr.addTest(name, c)
    }

    public void compareResult(expected, actual){
        runner?.recordResult(expected, actual, null)
    }

    public void compareResult(expected, actual, Closure c){
        runner?.recordResult(expected, actual, c)
    }

    //here this is only a stub, the actual method execution will be on the TelluriumDataDrivenTest class
    public void openUrl(String url){
        runner?.openUrl(url)
    }

    //add assertions here so that user can add custom compare result code in the closure
    public void assertTrue(boolean condition){
        runner?.assertTrue(condition)
//        asserter.assertTrue(condition)
    }

    public void assertFalse(boolean condition){
        runner?.assertFalse(condition)
    }

    public void fail(String message){
        runner?.fail(message)
    }

    public void assertEquals(expected, actual){
        runner?.assertEquals(expected, actual)
    }

    public void assertNotNull(object){
        runner?.assertNotNull(object)
    }

    public void assertNull(object){
        runner?.assertNull(object)
    }

    public void assertSame(expected, actual){
        runner?.assertSame(expected, actual)
    }

    public void assertNotSame(expected, actual){
        runner?.assertNotSame(expected, actual)
    }
}