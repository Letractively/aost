package aost.builder

import aost.object.TextBox
import aost.locator.BaseLocator

class TextBoxBuilder extends UiObjectBuilder{

   def build(Map map){
       TextBox textbox = new TextBox()
       baseClosure(textbox, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       textbox.setLocator(locator)

       return textbox
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new TextBox()

        return build(map);  
    }
}