package org.aost.datadriven

import org.aost.datadriven.object.mapping.mapping.DataFieldSetObjectMapper
import org.aost.datadriven.object.mapping.type.TypeHandlerRegistry
import org.aost.datadriven.object.mapping.bind.VariableBinder
import org.aost.datadriven.object.mapping.mapping.FieldSetMapResult
import org.aost.datadriven.object.mapping.FieldSetRegistry

/**
 * The data provider for Pipe format flat files
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class DataProvider extends DataFieldSetObjectMapper{
    
    protected VariableBinder binder = new VariableBinder()

    public DataProvider(FieldSetRegistry fsr, TypeHandlerRegistry thr){
        super(fsr, thr)
    }

    public void useFile(String filePath){
        openFile(filePath)
    }

    public void useString(String data){
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