package org.telluriumsource.test.ddt.mapping.mapping

import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistry
import org.telluriumsource.test.ddt.mapping.type.TypeHandler
import org.telluriumsource.test.ddt.mapping.DataMappingException
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.annotation.Inject
import org.telluriumsource.annotation.Provider

/**
 * Default implementation to convert a data field to a Java object
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
@Provider(type=ObjectUnmarshaller.class)
class DefaultObjectUnmarshaller implements ObjectUnmarshaller{

    @Inject(name="i18nBundle", lazy=true)
    protected IResourceBundle i18nBundle ;

    @Inject
    protected TypeHandlerRegistry registry

    public void setTypeHandlerRegistry(TypeHandlerRegistry registry){
        this.registry = registry
    }

    public Object unmarshal(String type, String data) {
        TypeHandler handler = registry.getTypeHandler(type)
        if(handler == null)
          throw new DataMappingException(i18nBundle.getMessage("ObjectUnmarshaller.UnsupportedFieldType"))
//            throw new DataMappingException(SessionManager.getSession().getI18nBundle().getMessage("ObjectUnmarshaller.UnsupportedFieldType"))

        return handler.valueOf(data)
    }

}