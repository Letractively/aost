package org.tellurium.builder

import org.tellurium.object.Icon
import org.tellurium.locator.BaseLocator

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