package aost.locator
/**
 *   Automcatically generate relate xpath based on composite elements in the
 *   UI object
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class AutoLocateStrategy {

    def static boolean canHandle(locator){
       if(locator instanceof CompositeLocator)
        return true
       else
        return false
    }

    def static String locate(CompositeLocator locator){
        
        String xpath = XPathBuilder.internBuildXPath(locator.tag, locator.text, locator.position, locator.attributes)
        if(locator.leading != null && (!locator.leading.trim().isEmpty())){
            return locator.leading + xpath
        }

        return xpath
    }
    
}