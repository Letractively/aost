package org.telluriumsource.ddt

import org.telluriumsource.ddt.object.mapping.mapping.DataFieldSetObjectMapper
import org.telluriumsource.ddt.object.mapping.type.TypeHandlerRegistry
import org.telluriumsource.ddt.object.mapping.bind.VariableBinder
import org.telluriumsource.ddt.object.mapping.mapping.FieldSetMapResult
import org.telluriumsource.ddt.object.mapping.FieldSetRegistry
import org.telluriumsource.config.Configurable
import org.telluriumsource.config.TelluriumConfigurator

/**
 * The data provider for different formats of input
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class DataProvider extends DataFieldSetObjectMapper implements Configurable{
    
    protected VariableBinder binder = new VariableBinder()
    
    public DataProvider(FieldSetRegistry fsr, TypeHandlerRegistry thr){
       super(fsr, thr)
     }

    public void useFile(String filePath){
        //get the singleton configurator
        TelluriumConfigurator configurator = new TelluriumConfigurator()
        //configure the reader
        configurator.config(this)
        openFile(filePath)
    }

    public void useString(String data){
        //get the singleton configurator
        TelluriumConfigurator configurator = new TelluriumConfigurator()
        //configure the reader
        configurator.config(this)
        this.readData(data)
    }

    public FieldSetMapResult nextFieldSet(){
        FieldSetMapResult result = readNextFieldSet()
        if(result != null && (!result.isEmpty())){
            binder.updateFieldSetMapResult(result.getFieldSetName(), result)
        }

        return result
    }

    public def bind(String dataFieldId){
        return binder.bind(dataFieldId)
    }

    public void stop(){
        close()
    }

}