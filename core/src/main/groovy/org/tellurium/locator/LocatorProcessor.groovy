package org.tellurium.locator

import org.tellurium.dsl.WorkflowContext
import org.tellurium.exception.InvalidLocatorException
import org.tellurium.i18n.InternationalizationManager;
import org.tellurium.i18n.InternationalizationManagerImpl;


/**
 * convert different locator data structures to actual locators or partial locators
 * delegate to different locate strategies, like a handler chain.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class LocatorProcessor{
    public static final String CANNOT_HANDLE_LOCATOR= "Cannot handle locator"
    protected InternationalizationManager i18nManager = new InternationalizationManagerImpl()

  
    def String locate(WorkflowContext context, locator){
        if(locator == null)
            return ""

        if(locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(locator)

        if(locator instanceof CompositeLocator){
            if(context.isUseJQuerySelector())
              return CompositeLocateStrategy.select(locator)

            return CompositeLocateStrategy.locate(locator)

        }

/*		if(locator instanceof JQLocator){
			  return JQueryLocateStrategy.locate(locator);
		}*/
      
        //should not process here, let the walkTo() method to handle that since it can handle
        //the relationship along its path and it has more information about objects and its children
//        if(locator instanceof GroupLocator)
//            return GroupLocateStrategy.locate(locator)
		
        throw new InvalidLocatorException(i18nManager.translate("LocatorProcessor.CannnotHandleLocator" , locator.getClass()))
    }
}