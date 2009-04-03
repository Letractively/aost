package org.tellurium.builder

import org.tellurium.object.ClickableUi

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class ClickableUiBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can useString them if not specified
        def df = [:]
        ClickableUi clickable = this.internBuild(new ClickableUi(), map, df)

        return clickable
    }

}