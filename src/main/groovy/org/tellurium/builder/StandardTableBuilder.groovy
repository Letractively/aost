package org.tellurium.builder

import org.tellurium.object.StandardTable
import org.tellurium.object.UiObject

/**
 * Build Standard Table
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class StandardTableBuilder extends UiObjectBuilder{

   def build(Map map, Closure closure){
       //add default parameters so that the builder can use them if not specified
       def df = [:]
       df.put(TAG, StandardTable.TAG)
       StandardTable table = this.internBuild(new StandardTable(), map, df)

       if(closure)
          closure(table)

       return table
   }

   def build(StandardTable table, UiObject[] objects){

      if(table == null || objects == null || objects.length < 1)
        return table

      objects.each {UiObject obj -> table.add(obj)}

      return table
   }

   def build(StandardTable table, UiObject object){

      if(table == null || object == null)
        return table

      table.add(object)

      return table
   }

}