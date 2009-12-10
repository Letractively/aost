package org.telluriumsource.widget

import org.telluriumsource.builder.UiObjectBuilderRegistry
import org.telluriumsource.config.Configurable

import org.telluriumsource.i18n.InternationalizationManager;
import org.telluriumsource.i18n.InternationalizationManagerImpl;



/**
 * Configure widgets
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 2, 2008
 * 
 */
class WidgetConfigurator implements Configurable{

    protected InternationalizationManager i18nManager = new InternationalizationManagerImpl()

    protected final static String PACKAGE_DELIMITER = "."
    protected final static String WIDGET_MODULE_SEPARATOR = ","

    public void configWidgetModule(String widgetModules){
        //first check if the string contains any widget module
        if(widgetModules != null && (widgetModules.trim().length() > 0)){
            String[] modules = widgetModules.trim().split(WIDGET_MODULE_SEPARATOR)

            UiObjectBuilderRegistry registry = new UiObjectBuilderRegistry()
            for(String module : modules){
                String fullname = getWidgetBootstrapClassFullName(module)
                if(fullname != null){
                    WidgetBootstrap bootstrap = (WidgetBootstrap) Class.forName(fullname).newInstance()
                    bootstrap.loadWidget(registry)
                }else{
                    println i18nManager.translate("WidgetConfigurator.ModuleNotempty" , module )
                }
            }
        }
    }

    protected String getWidgetBootstrapClassFullName(String name){
        if(name == null || name.trim().length() == 0)
            return null

        String trimed = name.trim()
        //package name provided, assume it is the full class name
        if(trimed.contains(PACKAGE_DELIMITER)){
            return trimed
        }else{
            //use the name conversion org.telluriumsource.widget.MODULE.Init
            //where Bootstrap should implement the interface WidgetBootstrap
            return "org.telluriumsource.widget.${trimed}.Init"
        }        
    }
}