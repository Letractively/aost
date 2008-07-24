package aost.datadriven.object.mapping.mapping

import aost.datadriven.object.mapping.type.TypeHandlerRegistry
import aost.datadriven.object.mapping.type.TypeHandler
import aost.datadriven.object.mapping.DataMappingException

/**
 * Default implementation to convert a data field to a Java object
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class DefaultObjectUnmarshaller implements ObjectUnmarshaller{

    protected final static String ERROR_UNSUPPORTED_FIELD_TYPE = "Unsupported field type";

    protected TypeHandlerRegistry registry

    public void setTypeHandlerRegistry(TypeHandlerRegistry registry){
        this.registry = registry
    }

    public Object unmarshal(String type, String data) {
        TypeHandler handler = registry.getTypeHandler(type)
        if(handler == null)
            throw new DataMappingException(ERROR_UNSUPPORTED_FIELD_TYPE)
        return handler.valueOf(data)
    }

}