package aost.dsl

import aost.datadriven.object.mapping.type.TypeHandlerRegistry
import aost.datadriven.object.mapping.FieldSetRegistry
import aost.datadriven.object.mapping.FieldSetParser
import aost.datadriven.object.mapping.type.TypeHandlerRegistryConfigurator
import aost.datadriven.object.mapping.PipeDataProvider

/**
 *
 * Extended DslContext for Data Driven Test
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
abstract class DdDslContext extends DslContext{
    
    protected TypeHandlerRegistry thr  = new TypeHandlerRegistry()
    protected FieldSetRegistry fsr = new FieldSetRegistry()

    protected PipeDataProvider dataProvider = new PipeDataProvider(fsr, thr)

    protected FieldSetParser fs = new FieldSetParser(fsr)

    // DSL to define your customer type handler such as
    // typeHandler "simpleDate", "aost.example.simpleDateTypeHandler"
    // here we assume that you have defined the aost.example.simpleDateTypeHandler class
    // it extends the TypeHandler interface
    public void typeHandler(String typeName, String fullClassName){
        TypeHandlerRegistryConfigurator.addCustomTypeHandler(thr, typeName, fullClassName)
    }

    //DSL to bind variables to data read from the file
    // def var1 = bind("dataset1.username")
    public def bind(String dataFieldId){

        return dataProvider.bind(dataFieldId)
    }

    //flow control
    public void driven(String fieldSetId, Closure c){
        while(dataProvider.eachLine(fieldSetId)){
            c()
        }
    }

    public void loadData(String filePath){
        dataProvider.start(filePath)
    }

    public void closeData(){
        dataProvider.stop()
    }
}