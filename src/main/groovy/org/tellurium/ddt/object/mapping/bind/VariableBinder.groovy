package org.tellurium.ddt.object.mapping.bind

import org.tellurium.ddt.object.mapping.DataMappingException
import org.tellurium.ddt.object.mapping.mapping.FieldSetMapResult

/**
 *
 * Bind the variable in the test script to the input data read from the file
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class VariableBinder {

    public static final String ID_SEPARATOR = '\\.'
    protected final static String ERROR_INVALID_DATA_FIELD_ID = "Invalid data field id "
    protected final static String ERROR_DATA_FIELD_ID_CANNOT_BE_NULL = "data field id cannot be null"
    protected final static String ERROR_CANNOT_FIND_DATA_FIELD = "Cannot find data field "

    protected ObjectBindRegistry registry = new ObjectBindRegistry()

    //useString duck type here
    public def bind(String dataFieldId){
        if(dataFieldId == null)
            throw new DataMappingException(ERROR_DATA_FIELD_ID_CANNOT_BE_NULL)

        def obj 
        String[] fls = dataFieldId.split(ID_SEPARATOR)

        if(fls.length > 2)
            throw new DataMappingException(ERROR_INVALID_DATA_FIELD_ID + dataFieldId)

        //the FieldSet Id is omitted, this implies that there is only one FieldSet defined
        if(fls.length == 1){
            FieldSetMapResult result = registry.getUniqueOne()
            if(result == null)
               throw new DataMappingException(ERROR_CANNOT_FIND_DATA_FIELD + dataFieldId)
            obj = result.getDataField(fls[0].trim())
        }else{
            //we have fieldSetId and DataFieldName
            String fieldSetId = fls[0].trim()
            String dataFieldName = fls[1].trim()
            FieldSetMapResult result = registry.getFieldSetMapResult(fieldSetId)
            if(result == null)
               throw new DataMappingException(ERROR_CANNOT_FIND_DATA_FIELD + dataFieldId)
            obj = result.getDataField(dataFieldName)
        }

        return obj
    }

    public void updateFieldSetMapResult(String fieldSetId, FieldSetMapResult result){
       this.registry.addFieldSetMapResult(fieldSetId, result)
   }

}