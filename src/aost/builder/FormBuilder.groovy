package aost.builder

import aost.object.UiObject
import aost.object.Form
import aost.locator.BaseLocator

/**
 *    Form builder
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class FormBuilder extends UiObjectBuilder{

    def build(Map map, Closure closure){
       Form form = new Form()

       if(map == null)
          return form

       baseClosure(form, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       form.setLocator(locator)
       if(closure)
          closure(form)

       return form
   }

   def build(Form form, UiObject[] objects){

      if(form == null || objects == null || objects.length < 1)
        return form

      objects.each {obj -> form.add(obj)}

      return form
   }

   def build(Form form, UiObject object){

      if(form == null || object == null)
        return form

      form.add(object)

      return form
   }
}