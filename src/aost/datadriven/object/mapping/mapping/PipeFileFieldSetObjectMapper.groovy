package aost.datadriven.object.mapping.mapping

import aost.datadriven.object.mapping.type.TypeHandlerRegistry
import aost.datadriven.object.mapping.FieldSetRegistry
import aost.datadriven.object.mapping.io.PipeFieldSetReader
import aost.datadriven.object.mapping.type.TypeHandlerRegistryConfigurator

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

//    public PipeFileFieldSetObjectMapper(){
//        reader = new PipeFieldSetReader()
//        marshaller = new DefaultObjectUnmarshaller()
//    }
    
    public PipeFileFieldSetObjectMapper(FieldSetRegistry fsr, TypeHandlerRegistry thr){
        reader = new PipeFieldSetReader()
        marshaller = new DefaultObjectUnmarshaller()
        //configurate the default type handlers and put them into the registry
        TypeHandlerRegistryConfigurator.configure(thr)
        marshaller.setTypeHandlerRegistry(thr)
        this.registry = fsr
    }

//    public void init(FieldSetRegistry fsr, TypeHandlerRegistry thr){
//       TypeHandlerRegistryConfigurator.configure(thr)
//       marshaller.setTypeHandlerRegistry(thr)
//       this.registry = fsr
//    }

    protected void openFile(String filePath){
        InputStreamReader isr = new InputStreamReader(new FileInputStream (filePath))
        br = new BufferedReader(isr)
    }

    protected FieldSetMapResult readFieldSet(String fieldSetId){
        return this.unmarshalFieldSet(br, fieldSetId)
    }

    protected void closeFile(){
        if(br != null)
            br.close()
    }
}