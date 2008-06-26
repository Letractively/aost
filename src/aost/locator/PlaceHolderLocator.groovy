package aost.locator
/**
 * This one is depreciated since Groovy has GString that you can use directly.
 * Please use GString like:
 *
 *     "label=${target}"
 *
 *    @author Jian Fang (Jian.Fang@jtv.com)
 */
class PlaceHolderLocator {
    String template
    String[] attributes
}