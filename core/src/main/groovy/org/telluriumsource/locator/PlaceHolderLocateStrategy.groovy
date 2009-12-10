package org.telluriumsource.locator

import org.telluriumsource.i18n.InternationalizationManager;
import org.telluriumsource.i18n.InternationalizationManagerImpl;


class PlaceHolderLocateStrategy {

    public static final String PLACE_HOLDER = "\\?";
    protected static InternationalizationManager i18nManager = new InternationalizationManagerImpl()


    def static boolean canHandle(locator){
       if(locator instanceof PlaceHolderLocator)
        return true
       else
        return false
    }

    def static String locate(PlaceHolderLocator locator){
        if(locator == null)
            throw new RuntimeException(i18nManager.translate("PlaceHolderLocatorStrategy.InvalidNullLocator"))
        def template = locator.template
        def attributes = locator.attributes

        if(template == null || attributes == null)
			return template;

		String loc = new String(template);
		for(String attribute : attributes){

			if(attribute != null){
				loc = loc.replaceFirst(PLACE_HOLDER, attribute);
			}else{
	            throw new RuntimeException(i18nManager.translate("PlaceHolderLocatorStrategy.InvalidNullAttribute"))
			}
		}

		return loc;
    }
}