package aost.builder

import aost.object.UiObject
import aost.locator.BaseLocator
import aost.locator.CompositeLocator
import aost.locator.GroupLocator
import aost.object.Container

/**
 *  Basic UI object builder
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
abstract class UiObjectBuilder{
    public static final String UID = "uid"
    public static final String NAMESPACE = "namespace"
    public static final String LOCATOR = "locator"
    public static final String CLOCATOR = "clocator"
//    public static final String GLOCATOR = "glocator"
    public static final String HEADER = "header"
    public static final String TRAILER = "trailer"
    public static final String TAG = "tag"
    public static final String TEXT = "text"
    public static final String POSITION = "position"
    public static final String TYPE = "type"
    public static final String USE_GROUP_INFO = "group"
    public static final String TRUE = "TRUE"

    def abstract build(Map map, Closure c);

    boolean validate(UiObject obj, Map map){
        boolean valid = true
        if(map == null || map.isEmpty()){
            println("Error: Must specified ID and other attributes for the UI object")
            return false
        }

        if(map.get(UID) == null){
            println("Error: UID must be specified")
            return false
        }
        
        if(map.get(LOCATOR) != null && map.get(CLOCATOR) != null){
            println("Error: cannot use both locator and composite to define UI")
            return false
        }

        if(map.get(USE_GROUP_INFO) != null && (!Container.class.isAssignableFrom(obj.getClass())) ){
           println("Error: only Container or its child classes can use Group information to infer its locator")
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
       if(!validate(obj, map))
         throw new RuntimeException("UI Object definition error")

        //make all lower cases
        map = makeCaseInsensitive(map)

        obj.uid = map.get(UID)
        obj.namespace = map.get(NAMESPACE)
        String useGroup = map.get(USE_GROUP_INFO)
        if(useGroup != null && TRUE.equals(useGroup.toUpperCase())){
            ((Container)obj).useGroupInfo = true
        }

        if(map.get(LOCATOR) != null){
            //use base locator
            obj.locator = buildBaseLocator(map.get(LOCATOR))
        }else if (map.get(CLOCATOR) != null){
            //use composite locator, it must be a map
            obj.locator = buildCompositeLocator(map.get(CLOCATOR), df)
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
        locator.header = map.get(HEADER)
        locator.trailer = map.get(TRAILER)

        locator.tag = map.get(TAG)
        //use default value  if TAG not specified
        if(locator.tag == null && df != null)
            locator.tag = df.get(TAG)

        locator.text = map.get(TEXT)
        locator.position = map.get(POSITION)

        map.each { String key, value ->
            if(!HEADER.equals(key) && !TRAILER.equals(key) && !TAG.equals(key) && !TEXT.equals(key) && !POSITION.equals(key))
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