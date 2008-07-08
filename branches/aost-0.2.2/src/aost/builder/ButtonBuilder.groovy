package aost.builder

import aost.object.Button
import aost.locator.BaseLocator

class ButtonBuilder extends UiObjectBuilder{

   def build(Map map){
       Button button = new Button()
       baseClosure(button, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       button.setLocator(locator)

       return button
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new Button()

        return build(map)
    }
}