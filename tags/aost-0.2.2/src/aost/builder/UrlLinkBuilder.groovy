package aost.builder

import aost.object.UrlLink
import aost.locator.BaseLocator

class UrlLinkBuilder extends UiObjectBuilder{

   def build(Map map){
       UrlLink url = new UrlLink()
       baseClosure(url, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       url.setLocator(locator)

       return url
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new UrlLink()

        return build(map);
    }
}