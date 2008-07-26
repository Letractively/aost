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
    //read the file and run the test script until it reaches the end of the file
    public void driveToEnd(Closure c){
        while(dataProvider.nextLine()){
            c()
        }
    }

    //read one line from the file and run the test script so that you can have different
    //test scripts for each line
    public boolean stepOneLine(Closure c){
        if(dataProvider.nextLine()){
            c()

            return true
        }

        return false
    }
    
    public void loadData(String filePath){
        dataProvider.start(filePath)
    }

    public void closeData(){
        dataProvider.stop()
    }

    def closeData = this.&closeData
}