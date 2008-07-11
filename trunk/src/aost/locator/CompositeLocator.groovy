package aost.locator

/**
 *  The composite locator which is used to automatically generate the relative
 *  xpath by given attributes and parameters.
 *
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class CompositeLocator {
    String lead
    String tag
    String text
    String trail
    def position
    Map<String, String> attributes = [:]
}