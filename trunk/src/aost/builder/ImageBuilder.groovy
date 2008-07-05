package aost.builder

import aost.object.Image
import aost.locator.BaseLocator

/**
 *  Image builder
 * 
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class ImageBuilder extends UiObjectBuilder{

   def build(Map map){
       Image url = new Image()
       baseClosure(url, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       url.setLocator(locator)

       return url
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new Image()

        return build(map);
    }
}