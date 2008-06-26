package aost.builder

import aost.object.Container
import aost.locator.BaseLocator
import aost.object.UiObject

class ContainerBuilder extends UiObjectBuilder{

    def build(Map map, Closure closure){
       Container container = new Container()

       if(map == null)
          return container
        
       baseClosure(container, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       container.setLocator(locator)
       if(closure)
          closure(container)
        
       return container
   }

   def build(Container container, UiObject[] objects){

      if(container == null || objects == null || objects.length < 1)
        return container

      objects.each {obj -> container.add(obj)}

      return container
   }

   def build(Container container, UiObject object){

      if(container == null || object == null)
        return container

      container.add(object)

      return container
   }
}