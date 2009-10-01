package org.tellurium.ddt.object.mapping.mapping

import org.tellurium.ddt.object.mapping.type.TypeHandlerRegistry
import org.tellurium.ddt.object.mapping.type.TypeHandler
import org.tellurium.ddt.object.mapping.DataMappingException
import org.tellurium.i8n.InternationalizationManager;

/**
 * Default implementation to convert a data field to a Java object
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class DefaultObjectUnmarshaller implements ObjectUnmarshaller{

    protected InternationalizationManager i8nManager = new InternationalizationManager();

    protected TypeHandlerRegistry registry

    public void setTypeHandlerRegistry(TypeHandlerRegistry registry){
        this.registry = registry
    }

    public Object unmarshal(String type, String data) {
        TypeHandler handler = registry.getTypeHandler(type)
        if(handler == null)
            throw new DataMappingException(i8nManager.translate("ObjectUnmarshaller.UnsupportedFieldType"))
        return handler.valueOf(data)
    }

}