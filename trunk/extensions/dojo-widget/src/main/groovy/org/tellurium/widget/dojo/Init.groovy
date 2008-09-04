package org.tellurium.widget.dojo

import org.tellurium.widget.WidgetBootstrap
import org.tellurium.builder.UiObjectBuilderRegistry
import org.tellurium.widget.dojo.builder.DatePickerBuilder

/**
 * The bootstrap class for the Dojo widget module
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 2, 2008
 * 
 */
class Init implements WidgetBootstrap{

    public void loadWidget(UiObjectBuilderRegistry uiObjectBuilderRegistry) {
        if(uiObjectBuilderRegistry != null){
           uiObjectBuilderRegistry.registerBuilder(getFullName("DatePicker"), new DatePickerBuilder())      
        }
    }

    protected String getFullName(String name){
        return DojoWidget.NAMESPACE + DojoWidget.NAMESPACE_SUFFIX + name
    }
}