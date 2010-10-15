package org.telluriumsource.test.ddt

import org.telluriumsource.test.ddt.mapping.FieldSetParser
import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistryConfigurator
import org.telluriumsource.dsl.DdDslContext

/**
 * In this module, you can define the following things:
 *
 *    1) UI modules using "ui.XXX"
 *
 *    2) FieldSet, i.e., the input data format that applies to the actions defined in this module, using "fs.XXX"
 *       also include user-defined custom typehandler using "typeHandler"
 *
 *    3) Tests using "defineTest"
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 31, 2008
 *
 */
abstract class TelluriumDataDrivenModule extends DdDslContext {

    //define your Data Driven module in this method
    abstract void defineModule()

    protected TelluriumDataDrivenTest runner
  
    protected FieldSetParser fs = getFieldSetParser()

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
        TypeHandlerRegistryConfigurator.addCustomTypeHandler(getTypeHandlerRegistry(), typeName, fullClassName)
    }

    //DSL to bind variables to data read from the file
    public def bind(String dataFieldId){

        return dataProvider.bind(dataFieldId)
    }

    public void cacheVariable(String name, value){
        this.runner.cacheVariable(name, value)
    }

    public def getCachedVariable(String name){
        return this.runner.getCachedVariable(name)
    }
    
    public void defineTest(String name, Closure c){
        getTestRegistry().addTest(name, c)
    }

    public void checkResult(value, Closure c){
        runner?.recordResult(value, c)
    }

    public void logMessage(String message){
        runner?.logMessage(message)
    }

    //here this is only a stub, the actual method execution will be on the TelluriumDataDrivenTest class
    public void openUrl(String url){
        runner?.openUrl(url)
    }

    public void connectUrl(String url){
        runner?.connectUrl(url)
    }

}