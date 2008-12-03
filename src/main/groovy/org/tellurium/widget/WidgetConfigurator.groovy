package org.tellurium.widget

import org.tellurium.config.Configurable
import org.tellurium.builder.UiObjectBuilderRegistry

/**
 * Configure widgets
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 2, 2008
 * 
 */
class WidgetConfigurator implements Configurable{

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
                    println "Warning, Widget module ${module} cannot be empty"
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
            //use the name conversion org.tellurium.widget.MODULE.Init
            //where Bootstrape should implement the interface WidgetBootstrap
            return "org.tellurium.widget.${trimed}.Init"
        }        
    }
}