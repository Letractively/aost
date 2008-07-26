package aost.datadriven.object.mapping.mapping

import aost.datadriven.object.mapping.FieldSetRegistry
import aost.datadriven.object.mapping.io.DataReader
import aost.datadriven.object.mapping.FieldSet
import aost.datadriven.object.mapping.Field
import aost.datadriven.object.mapping.DataMappingException
import aost.datadriven.object.mapping.validator.FieldSetValidator
import aost.datadriven.object.mapping.io.DataReader

/**
 * The default implemention of the FieldSet Object Mapper
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
abstract class BaseFieldSetObjectMapper implements FieldSetObjectMapper{
    protected final static String ERROR_INVALID_DATA = "Invalid Data "
    protected final static String CANNOT_FIND_FIELD_SET = "Cannot find a matching field set "
    protected final static String ERROR_DATA_FIELD_SIZE_NOT_MATCH = "The data size does not match the size of the Field Set "

    protected FieldSetRegistry registry

    protected DataReader reader

    protected ObjectUnmarshaller marshaller

    public FieldSetMapResult unmarshalFieldSet(BufferedReader inputReader) {
        //read the data from the input stream
		List<String, String> fieldData = reader.readLine(inputReader)

        //end of file
        if(fieldData == null || fieldData.size() < 1)
            return null
//            throw new DataMappingException(ERROR_INVALID_DATA)

        //logic to autmoatically detect which field set by its field set identifier
        //or use the only one that exists in the registry

        //check the fieldset registry first
        FieldSet fs
        if(registry.size() == 1){
            //there is only one field set in the registry
            fs = registry.getUniqueOne()
        }else{
            //check the identifier and assume it always to be the first one
            String identifier = fieldData.get(0)
            fs = registry.getFieldSetByIdentifier(identifier)
        }

        if(fs == null)
            throw new DataMappingException(CANNOT_FIND_FIELD_SET)

        FieldSetValidator.validate(fs, fieldData)
        FieldSetMapResult result = new FieldSetMapResult()
        result.setFieldSetName(fs.getName())

        if(fieldData != null && fieldData.size() > 0){
			//check all the type maps for this field set
            if(fieldData.size() != fs.getFields().size())
                throw new DataMappingException(ERROR_DATA_FIELD_SIZE_NOT_MATCH + fs.getName())

            for(int i=0; i<fieldData.size(); i++){
                Field df = fs.getFields().get(i)
                def value = marshaller.unmarshal(df.getType(), fieldData.get(i))
                result.addDataField(df.getName(), value)
            }
		}

		return result
	}

}