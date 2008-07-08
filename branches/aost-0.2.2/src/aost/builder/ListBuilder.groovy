package aost.builder

import aost.object.UiObject
import aost.object.List
import aost.locator.BaseLocator

/**
 * List builder
 *
 *
* @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class ListBuilder extends UiObjectBuilder{
   static final String SEPARATOR = "separator"

   def build(Map map, Closure closure){
       List list = new List()

       if(map == null)
          return list

       baseClosure(list, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       if(locator.loc == null)
           locator.loc = ""
       list.setLocator(locator)
       if(map.get(SEPARATOR) != null){
           list.separator = map.get(SEPARATOR)
       }
       
       if(closure)
          closure(list)

       return list
   }

   def build(List list, UiObject[] objects){

      if(list == null || objects == null || objects.length < 1)
        return list

      objects.each {obj -> list.add(obj)}

      return list
   }

   def build(List list, UiObject object){

      if(list == null || object == null)
        return list

      list.add(object)

      return list
   }

}