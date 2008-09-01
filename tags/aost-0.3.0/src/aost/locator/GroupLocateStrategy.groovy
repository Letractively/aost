package aost.locator

import aost.object.Container
import aost.object.UiObject

/**
 *   Use group information, i.e., use its direct descedants' information to form its own locator
 *   In the future, we may consider deeper information such as grandchild descendants'. Note, only
 *   Container and its extended classes can use GroupLocateStrategy
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
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
                String gattr = XPathBuilder.buildDescendantXPath(cloc.tag, cloc.text, cloc.position, cloc.attributes)
                groupAttributes.add(gattr)
            }
        }

        CompositeLocator locator = obj.locator
        String xpath = XPathBuilder.buildGroupXPath(locator.tag, locator.text, locator.position, locator.attributes) {List<String> list ->
            if (!groupAttributes.isEmpty()) {
                list.addAll(groupAttributes)
            }
        }
        if (locator.header != null && (!locator.header.trim().isEmpty())) {
            xpath = locator.header + xpath
        }

        if (locator.trailer != null && (!locator.trailer.trim().isEmpty())) {
            xpath = xpath + locator.trailer
        }

        return xpath
    }

}