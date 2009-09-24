package org.tellurium.i8n

/**
 * InternationalizationManagerMetaClass
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */
class InternationalizationManagerMetaClass extends MetaClassImpl{

    private final static INSTANCE = new InternationalizationManager()

    InternationalizationManagerMetaClass() { super(InternationalizationManager) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}