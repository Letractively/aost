package org.tellurium.builder

import org.tellurium.locator.BaseLocator
import org.tellurium.locator.CompositeLocator
import org.tellurium.locator.JQLocator
import org.tellurium.object.Container
import org.tellurium.object.UiObject
import org.tellurium.Const
import org.tellurium.object.Table
import org.tellurium.i18n.InternationalizationManager;
import org.tellurium.i18n.InternationalizationManagerImpl;



/**
 *  Basic UI object builder
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
abstract class UiObjectBuilder extends Const {

    protected InternationalizationManager i18nManager = new InternationalizationManagerImpl();

    def abstract build(Map map, Closure c);

    boolean validate(UiObject obj, Map map){
        boolean valid = true
        if(map == null || map.isEmpty()){
        	
            println i18nManager.translate("UIObjectBuilder.EmptyMap")
            return false
        }

        if(map.get(UID) == null){
            println i18nManager.translate("UIObjectBuilder.UIDRequired")
            return false
        }
        
        if(map.get(LOCATOR) != null && map.get(CLOCATOR) != null){
            println i18nManager.translate("UIObjectBuilder.LocatorRequired")
            return false
        }

        if(map.get(USE_GROUP_INFO) != null && (!Container.class.isAssignableFrom(obj.getClass())) ){
           println i18nManager.translate("UIObjectBuilder.GroupInfoRequired")
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
         throw new RuntimeException(i18nManager.translate("UIObjectBuilder.ObjectDefinitionError"))

        //make all lower cases
        map = makeCaseInsensitive(map)

        obj.uid = map.get(UID)
        String ns = map.get(NAMESPACE)
        if(ns != null && ns.trim().length() > 0){
          obj.namespace = ns.trim()
        }
      
        String useGroup = map.get(USE_GROUP_INFO)
        if(useGroup != null && TRUE.equals(useGroup.toUpperCase())){
            ((Container)obj).useGroupInfo = true
        }
        String cache = map.get(CACHEABLE)
        if(cache != null && TRUE.equals(cache.toUpperCase())){
          obj.cacheable = true
        }

        String nocachechildren = map.get(NO_CACHE_FOR_CHILDREN)
        if(nocachechildren != null && nocachechildren.trim().length() > 0){
          if(obj instanceof Container){
            if(TRUE.equalsIgnoreCase(nocachechildren)){
              obj.noCacheForChildren = true
            }
          }
        }
        
/*
        else{
           if(obj instanceof org.tellurium.object.Table || obj instanceof org.tellurium.object.List){
              //not to use cache for children for all tables and Lists by default
              obj.noCacheForChildren = true
           }
        }
*/

        if(map.get(LOCATOR) != null){
            //use base locator
            obj.locator = buildBaseLocator(map.get(LOCATOR))
        }else if (map.get(CLOCATOR) != null){
            //use composite locator, it must be a map
            obj.locator = buildCompositeLocator(map.get(CLOCATOR), df)
            if(obj.namespace != null && obj.namespace.trim().length() > 0){
              obj.locator.namespace = obj.namespace
            }
        }else if (map.get(JQLOCATOR) != null){
            //useString jquery locator
            obj.locator = buildJQueryLocator(map.get(JQLOCATOR))
        }else{
            //use default base Locator
            obj.locator = new BaseLocator()
        }

        //add respond to events
        if(map.get(RESPOND_TO_EVENTS) != null){
            obj.respondToEvents = map.get(RESPOND_TO_EVENTS)
            //add dynamic method "click" if the Click event is included and is not on the original object
            //this will be implemented by the methodMissing method
        }

        return obj
    }

    def buildBaseLocator(String loc){
        BaseLocator locator = new BaseLocator(loc:loc)
        return locator
    }

    def buildJQueryLocator(String loc){
        JQLocator locator = new JQLocator(loc:loc)

        return locator
    }

    def buildCompositeLocator(Map map, Map df){
        CompositeLocator locator = new CompositeLocator()
        Map<String, String> attributes = [:]
        locator.header = map.get(HEADER)
        locator.trailer = map.get(TRAILER)
        
        if(map.get(DIRECT) != null && TRUE.equalsIgnoreCase(map.get(DIRECT)))
            locator.direct = true

        if(map.get(INSIDE) != null && TRUE.equalsIgnoreCase(map.get(INSIDE)))
            locator.inside = true

        locator.tag = map.get(TAG)
        //use default value  if TAG not specified
        if(locator.tag == null && df != null)
            locator.tag = df.get(TAG)

        locator.text = map.get(TEXT)
        locator.position = map.get(POSITION)

        map.each { String key, value ->
            if(!HEADER.equals(key) && !TRAILER.equals(key) && !TAG.equals(key) && !TEXT.equals(key) && !POSITION.equals(key) && !DIRECT.equals(key))
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