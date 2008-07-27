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
       //add default parameters so that the builder can useString them if not specified
       def df = [:]
       df.put(TAG, Table.TAG)
       Table table = this.internBuild(new Table(), map, df)

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
