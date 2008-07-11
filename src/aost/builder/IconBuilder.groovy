package aost.builder

import aost.object.Icon
import aost.locator.BaseLocator

/**
 *    Icon builder
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class IconBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        def df = [:]
        Icon icon = this.internBuild( new Icon(), map, df)

        return icon
    }
}