package org.tellurium.builder

import org.tellurium.object.UiObject
import org.tellurium.object.List
import org.tellurium.locator.BaseLocator

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
       def df = [:]
       List list = this.internBuild(new List(), map, df)

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