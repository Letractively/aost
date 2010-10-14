package org.telluriumsource.ui.widget.jqueryui

import org.telluriumsource.ui.widget.WidgetBootstrap
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry
import org.telluriumsource.ui.widget.jqueryui.builder.DatePickerBuilder

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
        return JQueryUiWidget.NAMESPACE + JQueryUiWidget.NAMESPACE_SUFFIX + name
    }
}