package org.telluriumsource.widget.dojo

import org.telluriumsource.widget.dojo.builder.DatePickerBuilder
import org.telluriumsource.widget.dojo.builder.JtvTabContainerBuilder
import org.telluriumsource.widget.WidgetBootstrap
import org.telluriumsource.builder.UiObjectBuilderRegistry

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
           uiObjectBuilderRegistry.registerBuilder(getFullName("JtvTabContainer"), new JtvTabContainerBuilder())
        }
    }

    protected String getFullName(String name){
        return DojoWidget.NAMESPACE + DojoWidget.NAMESPACE_SUFFIX + name
    }
}