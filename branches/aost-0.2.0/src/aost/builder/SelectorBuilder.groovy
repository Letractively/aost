package aost.builder

import aost.object.Selector
import aost.locator.BaseLocator

class SelectorBuilder extends UiObjectBuilder{

   def build(Map map){
       Selector selector = new Selector()
       baseClosure(selector, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       selector.setLocator(locator)

       return selector
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new Selector()

        return build(map);  
    }
}