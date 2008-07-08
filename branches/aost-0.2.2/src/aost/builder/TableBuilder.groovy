package aost.builder

import aost.builder.UiObjectBuilder
import aost.locator.BaseLocator
import aost.object.Table
import aost.object.UiObject

/**
 * Table builder
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */

class TableBuilder extends UiObjectBuilder{

   def build(Map map, Closure closure){
       Table table = new Table()

       if(map == null)
          return table

       baseClosure(table, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       table.setLocator(locator)
       if(closure)
          closure(table)

       return table
   }

   def build(Table table, UiObject[] objects){

      if(table == null || objects == null || objects.length < 1)
        return table

      objects.each {obj -> table.add(obj)}

      return table
   }

   def build(Table table, UiObject object){

      if(table == null || object == null)
        return table

      table.add(object)

      return table
   }
}
