package org.tellurium.dsl

import org.tellurium.ddt.object.mapping.type.TypeHandlerRegistry
import org.tellurium.ddt.object.mapping.FieldSetRegistry
import org.tellurium.ddt.object.mapping.FieldSetParser
import org.tellurium.ddt.object.mapping.type.TypeHandlerRegistryConfigurator
import org.tellurium.ddt.DataProvider
import org.tellurium.ddt.object.mapping.mapping.FieldSetMapResult
import org.tellurium.ddt.object.mapping.FieldSet
import org.tellurium.ddt.object.mapping.ActionField
import org.tellurium.ddt.ActionRegistry
import org.tellurium.test.helper.DefaultResultListener
import org.tellurium.test.helper.TestResult
import org.tellurium.test.helper.ResultListener
import org.tellurium.test.helper.StepStatus
import org.tellurium.test.helper.AssertionResult
import junit.framework.AssertionFailedError


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

    //the count of number of steps can also be used to identify the ith run of the test
    protected int stepCount = 0

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
            TestResult result = new TestResult()
            result.setProperty("testName", action)
            result.setProperty("stepId", ++stepCount)
            result.setProperty("start", System.nanoTime())
            result.setProperty("input", fsmr.getResults())
 //           result.setProperty("passed", true)

            try{
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
                result.setProperty("status", StepStatus.PROCEEDED)
            }catch(Exception e){
                result.setProperty("status", StepStatus.EXECPTION)
//                result.setProperty("passed", false)
                result.setProperty("exception", e)
            }
            result.setProperty("end", System.nanoTime())
            listener.listenForInput(result)

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
            //check if the field set includes action name
            String action = getActionForFieldSet(fsmr.getFieldSetName())

            TestResult result = new TestResult()
            result.setProperty("testName", action)
            result.setProperty("stepId", ++stepCount)
            result.setProperty("input", fsmr.getResults())
            result.setProperty("status", StepStatus.SKIPPED)
//            result.setProperty("passed", true)

            listener.listenForInput(result)

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
        listener.report()
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

    public boolean compareResult(expected, actual){
        boolean passed = true

        TestResult result = new TestResult()
        AssertionResult assertResult = new AssertionResult()

        result.setProperty("stepId", stepCount)
        assertResult.setProperty("expected", expected)
        assertResult.setProperty("actual", actual)

        try{
            junit.framework.Assert.assertEquals(expected, actual)
        }catch(AssertionFailedError e){
            passed = false
            assertResult.setProperty("error", e)
        }

        assertResult.setProperty("passed", passed)
        result.addAssertationResult(assertResult)
        listenForResult(result)
    }

}