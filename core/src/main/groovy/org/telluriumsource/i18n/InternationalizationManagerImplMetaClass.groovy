package org.telluriumsource.i18n

/**
 * InternationalizationManagerMetaClass
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */
class InternationalizationManagerImplMetaClass extends MetaClassImpl{

    private final static INSTANCE = new InternationalizationManagerImpl()

    InternationalizationManagerImplMetaClass() { super(InternationalizationManagerImpl) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}