import aost.builder.UiObjectBuilder
import aost.locator.BaseLocator

/**
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */

class TableBuilder extends UiObjectBuilder{

    def static build(Map map){
       Table table = new Table();
       baseClosure(table, map)

       BaseLocator locator = new BaseLocator(loc:map.get(UiObjectBuilder.LOCATOR))
       table.setLocator(locator)

       return table
   }

    public build(Map map, Closure c) {
        if(map == null)
            return new Table()

        return build(map)
    }
}
