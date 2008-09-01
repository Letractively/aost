package aost.builder

import aost.object.Icon
import aost.locator.BaseLocator

class IconBuilder extends UiObjectBuilder{

   def build(Map map){
       Icon icon = new Icon()
       baseClosure(icon, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       icon.setLocator(locator)

       return icon
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new Icon()

        return build(map);
    }
}