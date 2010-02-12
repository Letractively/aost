package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Div

/**
 * Created by IntelliJ IDEA.
 * User: vivmon1
 * Date: Jul 25, 2008
 * Time: 5:18:33 PM
 * To change this template use File | Settings | File Templates.
 */
class DivBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, Div.TAG)
        Div div = this.internBuild( new Div(), map, df)

        return div
    }
}