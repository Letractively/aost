package org.aost.dsl

import org.aost.datadriven.object.mapping.type.TypeHandlerRegistry
import org.aost.datadriven.object.mapping.FieldSetRegistry
import org.aost.datadriven.object.mapping.FieldSetParser
import org.aost.datadriven.object.mapping.type.TypeHandlerRegistryConfigurator
import org.aost.datadriven.DataProvider
import org.aost.datadriven.object.mapping.mapping.FieldSetMapResult
import org.aost.datadriven.object.mapping.FieldSet
import org.aost.datadriven.object.mapping.ActionField
import org.aost.datadriven.ActionRegistry
import org.aost.test.helper.DefaultResultListener
import org.aost.test.helper.TestResult
import org.aost.test.helper.ResultListener

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

    protected DataProvider dataProvider = new DataProvider(fsr, thr)

    protected FieldSetParser fs = new FieldSetParser(fsr)

    protected ActionRegistry ar = new ActionRegistry()

    protected ResultListener listener = new DefaultResultListener()

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
    public void stepToEnd(Closure c){
        boolean hasMore = true
        while(hasMore){
            hasMore = step(c)
        }
    }

//    def stepToEnd = this.&stepToEnd

    //read one line from the file and run the test script so that you can have different
    //test scripts for each line
    public boolean step(Closure c){
        //get data from the data stream
        FieldSetMapResult fsmr = dataProvider.nextFieldSet()
        //check if we reach the end of data stream
        if(fsmr != null && (!fsmr.isEmpty())){
            //check if the field set includes action name
            String action = getActionForFieldSet(fsmr.getFieldSetName())
            if(action != null){
                //if the field set includes action
                //get the pre-defined action and run it
                Closure closure = ar.getAction(action)
                closure()
            }

            //if there is other user defined closure, run it
            if(c != null){
                c()
            }

            return true
        }

        return false
    }

//    def step = this.&step

    //read one from the file but do not run the test script. This may apply to the scenario
    //that you need to read multiple lines before you can run the test
    //If the next line is of the same Field set as the current one, the data reading in will
    //be overwritten after this command
    public boolean stepOver(){
        FieldSetMapResult fsmr = dataProvider.nextFieldSet()
        //check if we reach the end of data stream
        if(fsmr != null && (!fsmr.isEmpty())){

            return true
        }

        return false
    }

    //make DSL more expressive, instead of put stepOver(), but define the following
    //you can simply write stepOver
//    def stepOver = this.&stepOver

    public void loadData(String filePath){
        dataProvider.useFile(filePath)
    }

    //useString data defined in the script file
    public void useData(String data){
        dataProvider.useString(data)
    }

    public void closeData(){
        dataProvider.stop()
    }

//    def closeData = this.&closeData

    public void defineAction(String name, Closure c){
        ar.addAction(name, c)
    }

    protected String getActionForFieldSet(String fieldSetName){
        FieldSet tfs = fsr.getFieldSetByName(fieldSetName)
        if(tfs != null){
            ActionField taf = tfs.getActionField()
            if(taf != null){
                String tid = fieldSetName + "." + taf.getName()
                return dataProvider.bind(tid)
            }
        }

        return null
    }

    public void listenForResult(TestResult result ){
        listener.listenForResult(result)    
    }
}