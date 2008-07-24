package aost.datadriven.object.mapping
/**
 * Shared code for builder
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
abstract class BaseBuilder {
    protected static final String ID = "id"
    protected static final String DESCRIPTION = "description"
    
    Map makeCaseInsensitive(Map map){
        def newmap = [:]
        map.each{ String key, value ->
            //make all lower cases
            newmap.put(key.toLowerCase(), value)
        }

        return newmap
    }

    def abstract build(Map map)
}