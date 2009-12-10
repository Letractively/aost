package org.telluriumsource.builder

import org.telluriumsource.object.ClickableRadioButton

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 3, 2008
 * 
 */
class ClickableRadioButtonBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        //add default parameters so that the builder can useString them if not specified
        def df = [:]
        df.put(TAG, ClickableRadioButton.TAG)
        df.put(TYPE, ClickableRadioButton.TAG)
        ClickableRadioButton radiobutton = this.internBuild(new ClickableRadioButton(), map, df)

        return radiobutton
    }

}