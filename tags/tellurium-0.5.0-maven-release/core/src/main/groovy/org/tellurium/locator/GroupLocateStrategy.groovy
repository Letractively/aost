package org.tellurium.locator

import org.tellurium.object.Container
import org.tellurium.object.UiObject

/**
 *   Use group information, i.e., useString its direct descedants' information to form its own locator
 *   In the future, we may consider deeper information such as grandchild descendants'. Note, only
 *   Container and its extended classes can useString GroupLocateStrategy
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class GroupLocateStrategy {

    //group locate strategy is kind of special since it requires the object and its children which it holds
    //must be a container or its child classes to call this
    def static String locate(Container obj){
        if(obj.locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(obj.locator)

        List<String> groupAttributes = new ArrayList<String>()

        obj.components.each{ String key, UiObject child ->
            //can only useString the child's information in the CompositeLocator
            //cannot useString other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
                String gattr
                if(cloc.direct)
                    gattr= XPathBuilder.buildChildXPath(cloc.tag, cloc.text, cloc.position, cloc.attributes)
                else
                    gattr= XPathBuilder.buildDescendantXPath(cloc.tag, cloc.text, cloc.position, cloc.attributes)
                groupAttributes.add(gattr)
            }
        }

        CompositeLocator locator = obj.locator
        String xpath = XPathBuilder.buildGroupXPath(locator.tag, locator.text, locator.position, locator.direct, locator.attributes) {List<String> list ->
            if (!groupAttributes.isEmpty()) {
                list.addAll(groupAttributes)
            }
        }
        if (locator.header != null && (locator.header.trim().length() > 0)) {
            xpath = locator.header + xpath
        }

        if (locator.trailer != null && (locator.trailer.trim().length() > 0)) {
            xpath = xpath + locator.trailer
        }

        return xpath
    }

}