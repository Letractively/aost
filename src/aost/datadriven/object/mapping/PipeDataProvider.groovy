package aost.datadriven.object.mapping

import aost.datadriven.object.mapping.mapping.PipeFileFieldSetObjectMapper
import aost.datadriven.object.mapping.type.TypeHandlerRegistry
import aost.datadriven.object.mapping.bind.VariableBinder
import aost.datadriven.object.mapping.mapping.FieldSetMapResult

/**
 * The data provider for Pipe format flat files
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class PipeDataProvider extends PipeFileFieldSetObjectMapper{
    
    protected VariableBinder binder = new VariableBinder()

    public PipeDataProvider(FieldSetRegistry fsr, TypeHandlerRegistry thr){
        super(fsr, thr)
    }

    public void start(String filePath){
        openFile(filePath)
    }

    public boolean lineStep(Closure c){
        FieldSetMapResult result = readFieldSetByLine()
        if(result != null){
            binder.updateFieldSetMapResult(result.getFieldSetName(), result)
            c()
            //file is not ended
            return true
        }
        //indicate the end of the file
        return false
    }

    public boolean nextLine(){
        FieldSetMapResult result = readFieldSetByLine()
        if(result != null && (!result.isEmpty())){
            binder.updateFieldSetMapResult(result.getFieldSetName(), result)
            //file is not ended
            return true
        }
        //indicate the end of the file
        return false
    }

    public def bind(String dataFieldId){
        return binder.bind(dataFieldId)
    }

    public void stop(){
        closeFile()
    }

}