package aost.builder

import aost.object.UiObject
import aost.locator.BaseLocator
import aost.locator.CompositeLocator

/**
 *  Basic UI object builder
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
abstract class UiObjectBuilder{
    public static final String ID = "id"
    public static final String NAMESPACE = "namespace"
    public static final String LOCATOR = "locator"
    public static final String COMPOSITE = "clocator"
    public static final String LEAD = "lead"
    public static final String TRAIL = "trail"
    public static final String TAG = "tag"
    public static final String TEXT = "text"
    public static final String POSITION = "position"
    public static final String TYPE = "type"

    def abstract build(Map map, Closure c);

    boolean validate(Map map){
        boolean valid = true
        if(map == null || map.isEmpty()){
            println("Error: Must specified ID and other attributes for the UI object")
            return false
        }

        if(map.get(ID) == null){
            println("Error: ID must be specified")
            return false
        }
        
        if(map.get(LOCATOR) != null && map.get(COMPOSITE) != null){
            println("Error: cannot use both locator and composite to define UI")
            return false
        }

        return valid
    }

    Map makeCaseInsensitive(Map map){
        def newmap = [:]
        map.each{ String key, value ->
            //make all lower cases
            newmap.put(key.toLowerCase(), value)
        }

        return newmap
    }

    def internBuild(UiObject obj, Map map, Map df){
       if(!validate(map))
         throw new RuntimeException("UI Object definition error")

        //make all lower cases
        map = makeCaseInsensitive(map)

        obj.id = map.get(ID)
        obj.namespace = map.get(NAMESPACE)

        if(map.get(LOCATOR) != null){
            //use base locator
            obj.locator = buildBaseLocator(map.get(LOCATOR))
        }else if (map.get(COMPOSITE) != null){
            //use composite locator, it must be a map
            obj.locator = buildCompositeLocator(map.get(COMPOSITE), df)
        }else{
            //use default base Locator
            obj.locator = new BaseLocator()
        }

        return obj
    }

    def buildBaseLocator(String loc){
        BaseLocator locator = new BaseLocator(loc:loc)
        return locator
    }

    def buildCompositeLocator(Map map, Map df){
        CompositeLocator locator = new CompositeLocator()
        Map<String, String> attributes = [:]
        locator.lead = map.get(LEAD)
        locator.trail = map.get(TRAIL)

        locator.tag = map.get(TAG)
        //use default value  if TAG not specified
        if(locator.tag == null && df != null)
            locator.tag = df.get(TAG)

        locator.text = map.get(TEXT)
        locator.position = map.get(POSITION)

        map.each { String key, value ->
            if(!LEAD.equals(key) && !TAG.equals(key) && !TEXT.equals(key) && !POSITION.equals(key))
                attributes.put(key, value)
        }
        if(df != null && (!df.isEmpty())){
            df.each { String key, value ->
                //only apply default value when the attribute is not specified
                if(!TAG.equals(key) && attributes.get(key) == null){
                    attributes.put(key, value)
                }
            }
        }
        locator.attributes = attributes
        
        return locator
    }
}