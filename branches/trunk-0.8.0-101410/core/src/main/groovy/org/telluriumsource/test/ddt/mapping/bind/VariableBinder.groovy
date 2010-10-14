package org.telluriumsource.test.ddt.mapping.bind

import org.telluriumsource.test.ddt.mapping.DataMappingException
import org.telluriumsource.test.ddt.mapping.mapping.FieldSetMapResult
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.annotation.Inject;



/**
 *
 * Bind the variable in the test script to the input data read from the file
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class VariableBinder {

    public static final String ID_SEPARATOR = '\\.'

    @Inject(name="i18nBundle", lazy=true)
    private IResourceBundle i18nBundle ;

    protected ObjectBindRegistry registry = new ObjectBindRegistry()

    public def bind(String dataFieldId){
        if(dataFieldId == null)

            throw new DataMappingException(i18nBundle.getMessage("VariableBinder.DataFieldCannotBeNull"))

        def obj
        String[] fls = dataFieldId.split(ID_SEPARATOR)

        if(fls.length > 2)
            throw new DataMappingException(i18nBundle.getMessage("VariableBinder.InvalidDataFieldId" , dataFieldId)
)

        //the FieldSet Id is omitted, this implies that there is only one FieldSet defined
        if(fls.length == 1){
            FieldSetMapResult result = registry.getUniqueOne()
            if(result == null)
               throw new DataMappingException(i18nBundle.getMessage("VariableBinder.CannotFindDataField" , dataFieldId))
            obj = result.getDataField(fls[0].trim())
        }else{
            //we have fieldSetId and DataFieldName
            String fieldSetId = fls[0].trim()
            String dataFieldName = fls[1].trim()
            FieldSetMapResult result = registry.getFieldSetMapResult(fieldSetId)
            if(result == null)
                throw new DataMappingException(i18nBundle.getMessage("VariableBinder.CannotFindDataField" , dataFieldId))
            obj = result.getDataField(dataFieldName)
        }

        return obj
    }

    public void updateFieldSetMapResult(String fieldSetId, FieldSetMapResult result){
       this.registry.addFieldSetMapResult(fieldSetId, result)
   }

}