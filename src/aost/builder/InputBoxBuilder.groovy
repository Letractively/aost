package aost.builder

import aost.locator.BaseLocator
import aost.object.InputBox

class InputBoxBuilder extends UiObjectBuilder{

    def build(Map map){
       InputBox box = new InputBox()
       baseClosure(box, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       box.setLocator(locator)

       return box
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new InputBox()

        return build(map);
    }
}