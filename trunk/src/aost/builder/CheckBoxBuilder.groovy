package aost.builder

import aost.object.CheckBox
import aost.locator.BaseLocator

class CheckBoxBuilder extends UiObjectBuilder{

   def build(Map map){
       CheckBox checkbox = new CheckBox()
       baseClosure(checkbox, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       CheckBox.setLocator(locator)

       return checkbox
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new CheckBox()

        return build(map);
    }
}