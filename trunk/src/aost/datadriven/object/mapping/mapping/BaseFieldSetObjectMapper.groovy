package aost.datadriven.object.mapping.mapping

import aost.datadriven.object.mapping.FieldSetRegistry
import aost.datadriven.object.mapping.io.FieldSetReader
import aost.datadriven.object.mapping.FieldSet
import aost.datadriven.object.mapping.Field
import aost.datadriven.object.mapping.DataMappingException
import aost.datadriven.object.mapping.Field

/**
 * The default implemention of the FieldSet Object Mapper
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
abstract class BaseFieldSetObjectMapper implements FieldSetObjectMapper{

    protected final static String CANNOT_FIND_FIELD_SET = "Cannot find field set "
    protected final static String ERROR_DATA_FIELD_SIZE_NOT_MATCH = "The data size does not match the size of the Field Set "

    protected FieldSetRegistry registry

    protected FieldSetReader reader

    protected ObjectUnmarshaller marshaller

    public FieldSetMapResult unmarshalFieldSet(BufferedReader inputReader, String fieldSetId) {
        FieldSet fs =  registry.getFieldSet(fieldSetId)

        if(fs == null)
            throw new DataMappingException(CANNOT_FIND_FIELD_SET + fieldSetId)
        
        //read the field set from the input stream
		Map<String, String> fieldData = reader.readFieldSet(fs, inputReader)

        FieldSetMapResult result = new FieldSetMapResult()
        result.setFieldSetId(fieldSetId)

        if(fieldData != null && fieldData.size() > 0){
			//check all the type maps for this field set
            if(fieldData.size != fs.getFields().size())
                throw new DataMappingException(ERROR_DATA_FIELD_SIZE_NOT_MATCH + fieldSetId)
            
            for(Field df : fs.getFields()){
                def value = marshaller.unmarshal(df.getType(), fieldData.get(df.getName()))
                result.addDataField(df.getName(), value)
            }
		}

		return result
	}

}