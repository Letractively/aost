package org.tellurium.widget.extjs

import org.tellurium.widget.WidgetBootstrap
import org.tellurium.builder.UiObjectBuilderRegistry
import org.tellurium.widget.extjs.builder.DummyBuilder

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
          uiObjectBuilderRegistry.registerBuilder(getFullName("Dummy"), new DummyBuilder())
        }
    }

    protected String getFullName(String name){
        return ExtJSWidget.NAMESPACE + ExtJSWidget.NAMESPACE_SUFFIX + name
    }
}