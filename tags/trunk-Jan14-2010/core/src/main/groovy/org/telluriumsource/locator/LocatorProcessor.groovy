package org.telluriumsource.locator

import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.exception.InvalidLocatorException
import org.telluriumsource.i18n.IResourceBundle;
import org.telluriumsource.framework.Environment;



/**
 * convert different locator data structures to actual locators or partial locators
 * delegate to different locate strategies, like a handler chain.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class LocatorProcessor{
    public static final String CANNOT_HANDLE_LOCATOR= "Cannot handle locator"
  	protected IResourceBundle i18nBundle

  	public LocatorProcessor(){
    	  i18nBundle = Environment.instance.myResourceBundle()

    }

    def String locate(WorkflowContext context, locator){
        if(locator == null)
            return ""

        if(locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(locator)

        if(locator instanceof CompositeLocator){
            if(context.isUseCssSelector())
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

        throw new InvalidLocatorException(i18nBundle.getMessage("LocatorProcessor.CannnotHandleLocator" , locator.getClass()))
    }
}