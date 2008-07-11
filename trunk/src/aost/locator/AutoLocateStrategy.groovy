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
        if(locator.lead != null && (!locator.lead.trim().isEmpty())){
            xpath = locator.lead + xpath
        }

        if(locator.trail != null && (!locator.trail.trim().isEmpty())){
            xpath = xpath + locator.trail    
        }

        return xpath
    }
    
}