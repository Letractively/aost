package org.tellurium.test.ddt

import org.tellurium.ddt.object.mapping.FieldSetParser
import org.tellurium.dsl.UiDslParser
import org.tellurium.ddt.DataProvider
import org.tellurium.ddt.object.mapping.type.TypeHandlerRegistry
import org.tellurium.ddt.object.mapping.FieldSetRegistry
import org.tellurium.ddt.ActionRegistry

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 31, 2008
 *
 */
class DefaultTelluriumDataDrivenModule extends TelluriumDataDrivenModule{
    
    public DefaultTelluriumDataDrivenModule(){

    }

    public DefaultTelluriumDataDrivenModule(TypeHandlerRegistry thr, FieldSetRegistry fsr,
        FieldSetParser fs, ActionRegistry ar, DataProvider dataProvider){
//        this.ui = ui
        this.thr = thr
        this.fsr = fsr
        this.fs = fs
        this.ar = ar
        this.dataProvider = dataProvider
    }

    public FieldSetParser getFieldSetParser(){
        return this.fs
    }

    public void setFieldSetParser(FieldSetParser fs){
        this.fs = fs
    }

    public UiDslParser getUiDslParser(){
        return this.ui
    }

    public void setUiDslParser(UiDslParser ui){
        this.ui = ui
    }

    public DataProvider getDataProvider(){
        return this.dataProvider
    }

/*  XXX: seems this will cause endless loop in groovy and lead to stack overflow 
    public void setDataProvider(DataProvider dataProvider){
        this.dataProvider = dataProvider
    }
*/

    public TypeHandlerRegistry getTypeHandlerRegistry(){
        return this.thr
    }

    public void setTypeHandlerRegistry(TypeHandlerRegistry thr){
        this.thr = thr
    }

    public FieldSetRegistry getFieldSetRegistry(){
        return this.fsr
    }

    public void setFieldSetRegistry(FieldSetRegistry fsr){
       this.fsr = fsr
    }

    public void setActionRegistry(ActionRegistry ar){
        this.ar = ar
    }

    public ActionRegistry getActionRegistry(){
        return this.ar
    }

    public void defineModule() {

    }
}