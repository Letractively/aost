package org.tellurium.test.ddt

import org.tellurium.dsl.DslContext
import org.tellurium.ddt.object.mapping.type.TypeHandlerRegistry
import org.tellurium.ddt.object.mapping.FieldSetRegistry
import org.tellurium.ddt.object.mapping.FieldSetParser
import org.tellurium.ddt.ActionRegistry
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

    protected ActionRegistry ar 

    protected TelluriumDataDrivenTest runner

    //this module will belong to which data driven test
    //this method will be used internal only
    public void belongTo(TelluriumDataDrivenTest tddTest){
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

    public void defineAction(String name, Closure c){
        ar.addAction(name, c)
    }

    public void compareResult(expected, actual){
        //junit.framework.Assert.assertEquals(expected, actual)
        runner?.recordResult(expected, actual)
    }

    //here this is only a stub, the actual method execution will be on the TelluriumDataDrivenTest class
    public void openUrl(String url){
        runner?.openUrl(url)
    }

}