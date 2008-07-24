package aost.datadriven.object.mapping.mapping

import aost.datadriven.object.mapping.type.TypeHandlerRegistry
import aost.datadriven.object.mapping.FieldSetRegistry
import aost.datadriven.object.mapping.io.PipeFieldSetReader

/**
 * Handle Pipe format file
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class PipeFileFieldSetObjectMapper extends BaseFieldSetObjectMapper{

    private BufferedReader br

    public PipeFileFieldSetObjectMapper(){
        reader = new PipeFieldSetReader()
        marshaller = new DefaultObjectUnmarshaller()
    }

    public void init(FieldSetRegistry fsr, TypeHandlerRegistry thr){
       marshaller.setTypeHandlerRegistry(thr)
       this.registry = fsr
    }

    public void openFile(String filePath){
        InputStreamReader reader = new InputStreamReader(new FileInputStream (filePath))
        br = new BufferedReader(reader)
    }

    public FieldSetMapResult readFieldSet(String fieldSetId){
        return this.unmarshalFieldSet(br, fieldSetId)
    }

    public void closeFile(){
        if(br != null)
            br.close()
    }
}