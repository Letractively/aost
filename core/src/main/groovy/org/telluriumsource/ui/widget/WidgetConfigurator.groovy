package org.telluriumsource.ui.widget

import org.telluriumsource.ui.builder.UiObjectBuilderRegistry
import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.ui.widget.WidgetBootstrap
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.annotation.Inject
import org.telluriumsource.annotation.Provider;



/**
 * Configure widgets
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 2, 2008
 *
 */
@Provider
class WidgetConfigurator implements Configurable{

    @Inject(name="i18nBundle", lazy=true)
	private IResourceBundle i18nBundle

    @Inject
    private UiObjectBuilderRegistry registry

    protected final static String PACKAGE_DELIMITER = "."
    protected final static String WIDGET_MODULE_SEPARATOR = ","
  
    public void configWidgetModule(String widgetModules){
        //first check if the string contains any widget module
        if(widgetModules != null && (widgetModules.trim().length() > 0)){
            String[] modules = widgetModules.trim().split(WIDGET_MODULE_SEPARATOR)

            for(String module : modules){
                String fullname = getWidgetBootstrapClassFullName(module)
                if(fullname != null){
                    WidgetBootstrap bootstrap = (WidgetBootstrap) Class.forName(fullname).newInstance()
                    bootstrap.loadWidget(registry)
                }else{
                    println i18nBundle.getMessage("WidgetConfigurator.ModuleNotempty" , module )
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
            //use the name conversion org.telluriumsource.ui.widget.MODULE.Init
            //where Bootstrap should implement the interface WidgetBootstrap
            return "org.telluriumsource.ui.widget.${trimed}.Init"
        }
    }
}