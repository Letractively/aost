package org.aost.dsl

import org.aost.datadriven.object.mapping.FieldSetParser

/**
 * The default DdDslContext concrete class
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 25, 2008
 *
 */
class DefaultDdDslContext extends DdDslContext{

    public FieldSetParser getFieldSetParser(){
        return this.fs
    }

    public UiDslParser getUiDslParser(){
        return this.ui
    }
}