package org.telluriumsource.ddt.object.mapping.mapping

import org.telluriumsource.ddt.object.mapping.type.TypeHandlerRegistry
import org.telluriumsource.ddt.object.mapping.type.TypeHandler
import org.telluriumsource.ddt.object.mapping.DataMappingException
import org.telluriumsource.i18n.InternationalizationManager;
import org.telluriumsource.i18n.InternationalizationManagerImpl;


/**
 * Default implementation to convert a data field to a Java object
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class DefaultObjectUnmarshaller implements ObjectUnmarshaller{

    protected InternationalizationManager i18nManager = new InternationalizationManagerImpl();

    protected TypeHandlerRegistry registry

    public void setTypeHandlerRegistry(TypeHandlerRegistry registry){
        this.registry = registry
    }

    public Object unmarshal(String type, String data) {
        TypeHandler handler = registry.getTypeHandler(type)
        if(handler == null)
            throw new DataMappingException(i18nManager.translate("ObjectUnmarshaller.UnsupportedFieldType"))
        return handler.valueOf(data)
    }

}